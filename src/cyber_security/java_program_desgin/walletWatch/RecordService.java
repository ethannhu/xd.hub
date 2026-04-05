package walletWatch;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.Serializable;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class SingleRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    public LocalDate date;
    public int id;
    public String type;
    public double amount;
    public String[] keywords;

    public SingleRecord(LocalDate date, int id, String type, double amount, String[] keywords) {
        this.date = date;
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.keywords = keywords;
    }
}

public class RecordService implements Serializable {
    private static int numberOfServices = 0;
    private ArrayList<SingleRecord> records;
    private static final String RECORDS_PATH = "src/main/java/walletWatch/records.txt";

    public RecordService() {
        if (numberOfServices > 1) {
            throw new IllegalStateException("Only one instance of RecordService is allowed.");
        }
        numberOfServices++;

        Path path = Paths.get(RECORDS_PATH);
        boolean recordFileExists = Files.exists(path);

        this.records = new ArrayList<SingleRecord>(100);
        if (!recordFileExists) {
            try {
                Files.createFile(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toString()))) {
                Object obj = ois.readObject();
                @SuppressWarnings("unchecked")
                ArrayList<SingleRecord> loadedRecords = (ArrayList<SingleRecord>) obj;
                this.records = loadedRecords;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    private void saveRecords() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RECORDS_PATH))) {
            oos.writeObject(this.records);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRecord(SingleRecord record) {
        this.records.add(record);
        this.saveRecords();
    }

    public ArrayList<SingleRecord> queryByDate(LocalDate date) {
        ArrayList<SingleRecord> result = new ArrayList<>();
        for (SingleRecord record : records) {
            if (record.date.equals(date)) {
                result.add(record);
            }
        }
        return result;
    }

    public ArrayList<SingleRecord> queryByKeyword(String keyword) {
        ArrayList<SingleRecord> result = new ArrayList<>();
        for (SingleRecord record : records) {
            if (record.keywords != null) {
                for (String k : record.keywords) {
                    if (k != null && k.equalsIgnoreCase(keyword)) {
                        result.add(record);
                        break;
                    }
                }
            }
        }
        return result;
    }

    public double sumByMonth(int year, int month) {
        double sum = 0.0;
        for (SingleRecord record : records) {
            if (record.date.getYear() == year && record.date.getMonthValue() == month) {
                sum += record.amount;
            }
        }
        return sum;
    }

    public double sumByKeyword(String keyword) {
        double sum = 0.0;
        for (SingleRecord record : records) {
            if (record.keywords != null) {
                for (String k : record.keywords) {
                    if (k != null && k.equalsIgnoreCase(keyword)) {
                        sum += record.amount;
                        break;
                    }
                }
            }
        }
        return sum;
    }

    /**
     * 获取所有记录
     * @return 所有记账记录的列表
     */
    public ArrayList<SingleRecord> getAllRecords() {
        return new ArrayList<>(records);
    }

    /**
     * 统计指定月份有消费的天数
     * @param year 年份
     * @param month 月份
     * @return 有消费记录的天数
     */
    public int countDaysWithExpenses(int year, int month) {
        Set<LocalDate> daysWithExpenses = new HashSet<>();
        for (SingleRecord record : records) {
            if (record.date.getYear() == year && record.date.getMonthValue() == month) {
                daysWithExpenses.add(record.date);
            }
        }
        return daysWithExpenses.size();
    }
}