/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package walletWatch;

/**
 *
 * @author 20692
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;






public class CalendarView extends JFrame {
    private JLabel dateTimeLabel;
    private JTable calendarTable;
    private JComboBox<Integer> yearComboBox;
    private JComboBox<String> monthComboBox;
    private DefaultTableModel calendarModel;
    private RecordService recordService;
    private JTextArea recordTextArea;
    private JButton addRecordButton;
    private JPanel controlYMPanel;
    private JButton queryButton;
    private JButton summaryButton;
    private JButton countDaysButton;

    public CalendarView() {
        setTitle("日历显示器");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        recordService = new RecordService();
        // 日期时间标签
        initDateTimeLabel();
        // 添加记账记录按钮
        initAddRecordButton();
        // 日历表格
        initCalendarTable();
        // 控制面板
        initControlYMPanel();
        // 记账信息
        initRecordTextArea();
        // 查询按钮
        initQueryButton();
        // 汇总按钮
        initSummaryButton();
        // 统计天数按钮
        initCountDaysButton();

        // 添加组件到窗口
        setupComponents();

        // 启动时更新
        updateDateTime();
        updateCalendar();

        // 每秒更新时间
        Timer timer = new Timer(1000, e -> {
            updateDateTime();
            updateSelection();
            updateSelectionRecord();
        });
        timer.start();
    }

    private void setupComponents() {
        // 使用主面板和子面板优化布局
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // 左侧：添加按钮 + 控制年月，垂直排列
        leftPanel.add(addRecordButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(queryButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(summaryButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(countDaysButton);
        // 右侧：记账信息 + 查询按钮
        rightPanel.add(new JScrollPane(recordTextArea), BorderLayout.CENTER);

        // 主面板中间：日历表格
        mainPanel.add(new JScrollPane(calendarTable), BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(controlYMPanel, BorderLayout.SOUTH);
        // 顶部：时间标签
        add(dateTimeLabel, BorderLayout.NORTH);
        // 中间：主面板
        add(mainPanel, BorderLayout.CENTER);
    }

    private void initQueryButton() {
        queryButton = new JButton("查询记录");
        queryButton.addActionListener(e -> queryRecordCallback());
    }

    private void initSummaryButton() {
        summaryButton = new JButton("汇总");
        summaryButton.addActionListener(e -> summaryCallback());
    }

    private void initControlYMPanel() {
        controlYMPanel = new JPanel();
        monthComboBox = new JComboBox<>(MonthNames());
        yearComboBox = new JComboBox<>();
        for (int i = 1970; i <= 2100; i++) {
            yearComboBox.addItem(i);
        }
        controlYMPanel.add(new JLabel("选择月份："));
        controlYMPanel.add(monthComboBox);
        controlYMPanel.add(new JLabel("选择年份："));
        controlYMPanel.add(yearComboBox);

        monthComboBox.addActionListener(e -> updateCalendar());
        yearComboBox.addActionListener(e -> updateCalendar());
        // 启动时设置当前年月
        YearMonth now = YearMonth.now();
        monthComboBox.setSelectedIndex(now.getMonthValue() - 1);
        yearComboBox.setSelectedItem(now.getYear());
    }

    private void initRecordTextArea() {
        // 记账信息
        recordTextArea = new JTextArea();
        recordTextArea.setColumns(10);
        recordTextArea.setEditable(false);
        recordTextArea.setLineWrap(true);
        recordTextArea.setWrapStyleWord(true);
    }

    private void initCalendarTable() {
        String[] columns = { "日", "一", "二", "三", "四", "五", "六" };
        calendarModel = new DefaultTableModel(null, columns);
        calendarTable = new JTable(calendarModel);
        calendarTable.setEnabled(true);
        calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        calendarTable.setRowHeight(40);

    }

    private void initAddRecordButton() {
        addRecordButton = new JButton("添加记账记录");
        addRecordButton.addActionListener(e -> addRecordCallback());

    }

    private void initDateTimeLabel() {
        dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        dateTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);

    }

    private void initCountDaysButton() {
        countDaysButton = new JButton("统计消费天数");
        countDaysButton.addActionListener(e -> countDaysCallback());
    }

    private void addRecordCallback() {
        RecordPanel panel = new RecordPanel();
        int result = JOptionPane.showConfirmDialog(this, panel, "添加记账记录", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int id = panel.getId();
            String type = panel.getType();
            double amount = panel.getAmount();
            String keywords[] = panel.getKeywords();
            SingleRecord record = new SingleRecord(this.getSelectedDate(), id, type, amount, keywords);
            this.recordService.addRecord(record);
        }
    }

    private void queryRecordCallback() {
        QueryPanel queryPanel = new QueryPanel();
        int result = JOptionPane.showConfirmDialog(this, queryPanel, "查询记录", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            ArrayList<SingleRecord> records = new ArrayList<>();
            String dateText = queryPanel.getDate();
            String keywordText = queryPanel.getKeyword();

            if (!dateText.isEmpty()) {
                try {
                    LocalDate date = LocalDate.parse(dateText);
                    records.addAll(recordService.queryByDate(date));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "日期格式错误，应为yyyy-MM-dd", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (!keywordText.isEmpty()) {
                ;
                records.addAll(recordService.queryByKeyword(keywordText));
            }
            // 去重
            ArrayList<SingleRecord> uniqueRecords = new ArrayList<>();
            for (SingleRecord r : records) {
                if (!uniqueRecords.contains(r))
                    uniqueRecords.add(r);
            }
            if (uniqueRecords.isEmpty()) {
                JOptionPane.showMessageDialog(this, "没有找到记录", "查询结果", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder();
                for (SingleRecord record : uniqueRecords) {
                    sb.append("ID: ").append(record.id).append(", ");
                    sb.append("类型: ").append(record.type).append(", ");
                    sb.append("费用: ").append(record.amount).append(", ");
                    sb.append("关键词: ").append(String.join(", ", record.keywords)).append(", ");
                    sb.append("日期: ").append(record.date).append("\n");
                }
                JTextArea resultArea = new JTextArea(sb.toString());
                resultArea.setEditable(false);
                resultArea.setLineWrap(true);
                resultArea.setWrapStyleWord(true);
                JScrollPane scrollPane = new JScrollPane(resultArea);
                scrollPane.setPreferredSize(new Dimension(350, 200));
                JOptionPane.showMessageDialog(this, scrollPane, "查询结果", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void summaryCallback() {
        SummaryPanel summaryPanel = new SummaryPanel();
        int result = JOptionPane.showConfirmDialog(this, summaryPanel, "汇总", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String keywordText = summaryPanel.getKeyword();
            String monthText = summaryPanel.getMonth();
            double sum = 0.0;
            if (!keywordText.isEmpty()) {
                sum = recordService.sumByKeyword(keywordText);
            } else if (!monthText.isEmpty()) {
                try {
                    LocalDate date = LocalDate.parse(monthText + "-01");
                    sum = recordService.sumByMonth(date.getYear(), date.getMonthValue());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "日期格式错误，应为yyyy-MM", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "汇总结果: " + sum, "汇总结果", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateSelectionRecord() {
        LocalDate selectedDate = getSelectedDate();
        ArrayList<SingleRecord> records = recordService.queryByDate(selectedDate);
        if (records.isEmpty()) {
            recordTextArea.setText("没有记录");
        } else {
            StringBuilder sb = new StringBuilder();
            for (SingleRecord record : records) {
                sb.append("ID: ").append(record.id).append(", ");
                sb.append("类型: ").append(record.type).append(", ");
                sb.append("费用: ").append(record.amount).append(", ");
                sb.append("关键词: ").append(String.join(", ", record.keywords)).append("\n");
            }
            recordTextArea.setText(sb.toString());
        }
    }

    private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        dateTimeLabel.setText("当前时间: " + now.format(formatter));
    }

    private LocalDate getSelectedDate() {
        int selectedRow = calendarTable.getSelectedRow();
        int selectedColumn = calendarTable.getSelectedColumn();
        if (selectedRow != -1 && selectedColumn != -1) {
            Object value = calendarTable.getValueAt(selectedRow, selectedColumn);
            if (value != null) {
                int day = Integer.parseInt(value.toString());
                return LocalDate.of((int) yearComboBox.getSelectedItem(),
                        monthComboBox.getSelectedIndex() + 1, day);
            }
        }
        return null;
    }

    private void updateSelection() {
        int selectedRow = calendarTable.getSelectedRow();
        int selectedColumn = calendarTable.getSelectedColumn();
        if (selectedRow != -1 && selectedColumn != -1) {
            Object value = calendarTable.getValueAt(selectedRow, selectedColumn);
            if (value != null) {
                int day = Integer.parseInt(value.toString());
                LocalDate selectedDate = LocalDate.of((int) yearComboBox.getSelectedItem(),
                        monthComboBox.getSelectedIndex() + 1, day);
                dateTimeLabel.setText("选中日期: " + selectedDate);
            }
        }
    }

    private void updateCalendar() {
        int year = (int) yearComboBox.getSelectedItem();
        int month = monthComboBox.getSelectedIndex() + 1;
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDay = yearMonth.atDay(1);
        int startDay = firstDay.getDayOfWeek().getValue() % 7; // 周日为0

        calendarModel.setRowCount(0);
        Object[] week = new Object[7];
        int dayCounter = 1;
        int totalDays = yearMonth.lengthOfMonth();

        // 填充第一周
        for (int i = 0; i < 7; i++) {
            if (i < startDay) {
                week[i] = "";
            } else {
                week[i] = dayCounter++;
            }
        }
        calendarModel.addRow(week);

        // 填充剩余的日期
        while (dayCounter <= totalDays) {
            week = new Object[7];
            for (int i = 0; i < 7; i++) {
                if (dayCounter <= totalDays) {
                    week[i] = dayCounter++;
                } else {
                    week[i] = "";
                }
            }
            calendarModel.addRow(week);
        }
    }

    private String[] MonthNames() {
        return new String[] { "一月", "二月", "三月", "四月", "五月", "六月",
                "七月", "八月", "九月", "十月", "十一月", "十二月" };
    }

    private void countDaysCallback() {
        int year = (int) yearComboBox.getSelectedItem();
        int month = monthComboBox.getSelectedIndex() + 1;
        
        // 使用更准确的方式统计天数
        Set<LocalDate> daysWithExpenses = new HashSet<>();
        // 获取所有记录
        ArrayList<SingleRecord> allRecords = recordService.getAllRecords();
        // 遍历所有记录，找出指定年月的记录
        for (SingleRecord record : allRecords) {
            if (record.date.getYear() == year && record.date.getMonthValue() == month) {
                daysWithExpenses.add(record.date);
            }
        }
        
        JOptionPane.showMessageDialog(this, 
            String.format("%d年%d月共有%d天有消费记录", year, month, daysWithExpenses.size()),
            "消费天数统计",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalendarView().setVisible(true);
        });
    }
}

class RecordPanel extends JPanel {
    private JTextField idField;
    private JComboBox<String> typeBox;
    private JTextField amountField;
    private JTextField keywordField;

    public RecordPanel() {
        setLayout(new GridLayout(0, 2));
        idField = new JTextField();
        String[] types = { "餐饮", "交通", "购物", "娱乐", "其他" };
        typeBox = new JComboBox<>(types);
        amountField = new JTextField();
        keywordField = new JTextField();

        add(new JLabel("记账ID:"));
        add(idField);
        add(new JLabel("类型:"));
        add(typeBox);
        add(new JLabel("费用:"));
        add(amountField);
        add(new JLabel("关键词:"));
        add(keywordField);
    }

    public int getId() {
        return Integer.parseInt(idField.getText().trim());
    }

    public String getType() {
        return (String) typeBox.getSelectedItem();
    }

    public double getAmount() {
        return Double.parseDouble(amountField.getText().trim());
    }

    public String[] getKeywords() {
        return keywordField.getText().trim().split(";");
    }
}

class QueryPanel extends JPanel {
    private JTextField dateField;
    private JTextField keywordField;

    public QueryPanel() {
        setLayout(new GridLayout(0, 2));
        dateField = new JTextField();
        keywordField = new JTextField();

        add(new JLabel("日期(yyyy-MM-dd):"));
        add(dateField);
        add(new JLabel("关键词:"));
        add(keywordField);
    }

    public String getDate() {
        return dateField.getText().trim();
    }

    public String getKeyword() {
        return keywordField.getText().trim();
    }
}

class SummaryPanel extends JPanel {
    private JTextField keywordField;
    private JTextField monthField;

    public SummaryPanel() {
        setLayout(new GridLayout(0, 2));
        keywordField = new JTextField();
        monthField = new JTextField();

        add(new JLabel("关键词:"));
        add(keywordField);
        add(new JLabel("日期(yyyy-MM):"));
        add(monthField);
    }

    public String getKeyword() {
        return keywordField.getText().trim();
    }

    public String getMonth() {
        return monthField.getText().trim();
    }
}