/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objectSerialize;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author 20692
 */
public class ObjectSerializer {

    public static void main(String[] args) {
        Fighter fighterObj = new Fighter(1, 2, 3, 4);
        try (ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream("fighter.ser"))) {
            s.writeObject(fighterObj);

            System.out.println(
                    "Object saved.");
        } catch (NotSerializableException e) {
            System.out.println("Error serializing object: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO exception caught: " + e.getMessage());
        }
    }
}

class Fighter implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int strong;
    private int attack;
    private int vit;

    public Fighter(int id, int strong, int attack, int vit) {
        this.id = id;
        this.strong = strong;
        this.attack = attack;
        this.vit = vit;
    }
}
