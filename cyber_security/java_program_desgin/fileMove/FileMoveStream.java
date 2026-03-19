/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fileMove;

/**
 *
 * @author Eric
 * @date 2025年5月16日
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;

public class FileMoveStream {

    public static void main(String[] args) {
        String sourceFile = "source.txt";
        String targetFile = "dest.txt";
        File source = new File(sourceFile);
        try (
                FileInputStream inputStream = new FileInputStream(source); FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("File copy done.");
        } catch (IOException e) {
            System.out.println("Cannot copy file: " + e.getMessage());
        }
        try {
            Files.deleteIfExists(source.toPath());
            System.out.println("Source delete done.");
        } catch (IOException e) {
            System.out.println("Cannot delete source file: " + e.getMessage());
        }
    }
}
