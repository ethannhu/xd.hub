/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

/**
 *
 * @author 20692
 */
public class Expression {

    public static void main(String args[]) {
        boolean[] results1 = {
            0.0 == -0.0,
            0.0 > -0.0,
            1.0 < Double.NaN,
            1.0 > Double.NaN,
            1.0 == Double.NaN,
            1.0 != Double.NaN,
            Double.NaN == Double.NaN,};
        double[] results2 = {
            0.0 / 0.0,
            1.0 / 0.0,
            1.0 / -0.0,};
        for (boolean b : results1) {
            System.out.println(b);
        }
        for (double d : results2) {
            System.out.println(d);
        }
    }

}
