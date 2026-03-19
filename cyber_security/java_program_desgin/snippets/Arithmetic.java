/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

/**
 *
 * @author 20692
 */
public class Arithmetic {

    public static void main(String args[]) {
        int a = 1;
        double d = 1.0;
        double[] result = {
            46 / 9,
            46 % 9 + 4 * 4 - 2,
            45 + 43 % 5 * (23 * 3 % 2),
            4 + d * d + 4,
            1.5 * 3 + (++a),
            1.5 * 3 + a++,};
        for (double r : result) {
            System.out.println(r);
        }

    }
}
