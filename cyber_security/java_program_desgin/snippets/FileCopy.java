/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author 20692
 */
public class FileCopy{
        public static void main(String[] args) throws IOException{
                FileInputStream in=new FileInputStream("FileCopy.java");
                FileOutputStream out=new FileOutputStream("FileCopy.txt");
                int c;
                while( (c=in.read())!=-1)
                        out.write(c);
                in.close();
                out.close();
        }
}	//又例：CopyBytes.java
