package fileStatistics;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class FileStatistics {
    private File file;

    public FileStatistics(File file) {
        this.file = file;
    }

    // 判断是文件还是目录
    public void checkPathType() {
        if (!file.exists()) {
            System.out.println("路径不存在！");
        } else if (file.isDirectory()) {
            System.out.println("该路径是一个目录。");
        } else if (file.isFile()) {
            System.out.println("该路径是一个文件。");
        }
    }

    // 判断是否为 .txt 文件
    public boolean isTextFile() {
        return file.isFile() && file.getName().toLowerCase().endsWith(".txt");
    }

    // 统计字符数和字符串（单词）数
    public String performStatistics() throws IOException {
        String content = Files.readString(file.toPath());
        int charCount = 0;
        int wordCount = 0;

        for (char c : content.toCharArray()) {
            if (Character.isLetter(c)) {
                charCount++;
            }
        }

        String[] words = content.split("\\b+");
        for (String word : words) {
            if (word.matches("[A-Za-z]+")) {
                wordCount++;
            }
        }

        return String.format("统计结果：\n英文字符数：%d\n英文单词数：%d\n", charCount, wordCount);
    }

    // 输出结果到屏幕
    public void printToConsole(String result) {
        System.out.println(result);
    }

    // 输出结果到文件
    public void writeToFile(String result, String outputPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write(result);
        }
    }
}
