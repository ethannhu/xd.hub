/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

import java.io.File;
import java.util.Date;

/**
 *
 * @author 20692
 */
public class RenameFile {
        private static void fileData(File f) {
                System.out.println("Absolute path: " + f.getAbsolutePath()
                      + "\n Can read: " + f.canRead() + "\n Can write: " + f.canWrite()
                      + "\n getName: " + f.getName() + "\n getParent: " + f.getParent() 
                      + "\n getPath: " + f.getPath() + "\n length: " + f.length()
                      + "\n lastModified: " + new Date(f.lastModified()));
                if (f.isFile())  System.out.println("It's a file");
                else    System.out.println("It's a directory");
        }
        public static void main(String[] args) {
                File old = new File(args[0]);
                File rname = new File(args[1]);
                System.out.println("The original file's information: ");
                fileData(old);
                old.renameTo(rname);
                System.out.println("\n The file information after rename: ");
                fileData(rname);
                if (!old.exists()) 
                      System.out.println("\n The original file never exists");
        }
}

