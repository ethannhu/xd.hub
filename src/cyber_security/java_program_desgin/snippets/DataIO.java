/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author 20692
 */
public class DataIO {

    public static void main(String[] args) throws IOException {
        DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream("data.txt")));
        out.writeBoolean(false);
        out.writeChar('c');
        out.writeByte(1);
        out.writeShort(2);
        out.writeInt(3);
        out.writeLong(4L);
        out.writeFloat(5.0f);
        out.writeDouble(6.0);
        out.writeUTF("hello world!");
        out.close();

        DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream("data.txt")));
        System.out.println(in.readBoolean() + ";" + in.readChar() + ";");
        System.out.println(in.readByte() + ";" + in.readShort() + ";");
        System.out.println(+in.readInt() + ";" + in.readLong());
        System.out.println(in.readFloat() + ";" + in.readDouble() + ";");
        System.out.println(in.readUTF());
        in.close();
    }
}
