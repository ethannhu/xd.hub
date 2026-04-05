/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

/**
 *
 * @author 20692
 */
public class UnaryConversion {
    public static void main(String[] args) {
        byte b= 2;
        char c = '\u1000';
        int x= 8, y=3;
        int i=~b;
        System.out.println("b:"+b+" c:"+c);
        System.out.println(Integer.toHexString(i));
        System.out.println(x/y);
        System.out.println(x/(float)y);
    }
}
