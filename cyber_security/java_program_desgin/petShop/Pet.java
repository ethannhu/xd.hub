/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package petShop;

/**
 *
 * @author 20692
 */
public abstract class Pet {
    public int index;
    public String name;
    public boolean isAdopted;

    public abstract void feed();

    @Override
    public String toString() {
        return "id=" + index + ", name=" + name + ", isAdopted=" + isAdopted;
    }

    public static void getType(Pet p){
        if (p instanceof Dog) {
            System.out.println("Dog");
        } else if (p instanceof Cat) {
            System.out.println("Cat");
        } else if (p instanceof Bird) {
            System.out.println("Bird");
        } else {
            System.out.println("Unknown type");
        }
    }
}
