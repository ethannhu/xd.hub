package fileStatistics;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入文件或目录路径：");
        String path = scanner.nextLine();
        File file = new File(path);

        FileStatistics stats = new FileStatistics(file);
        stats.checkPathType();

        if (!stats.isTextFile()) {
            System.out.println("不是一个 .txt 文本文件，程序结束。");
            return;
        }

        try {
            String result = stats.performStatistics();

            System.out.println("请选择输出方式：1 - 屏幕，2 - 写入文件");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除缓冲

            if (choice == 1) {
                stats.printToConsole(result);
            } else if (choice == 2) {
                System.out.println("请输入输出文件路径：");
                String outputPath = scanner.nextLine();
                stats.writeToFile(result, outputPath);
                System.out.println("统计结果已写入文件。");
            } else {
                System.out.println("无效选择。");
            }
        } catch (Exception e) {
            System.out.println("发生错误：" + e.getMessage());
        }
    }
}
