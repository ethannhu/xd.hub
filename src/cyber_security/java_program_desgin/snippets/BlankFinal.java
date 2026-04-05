/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

/**
 *
 * @author 20692
 */
public class BlankFinal {
    private final int i = 0;
    private final int j;
    private final Poppet p;
    public BlankFinal() {
        this.j= 1;
        this.p = new Poppet(1);
    }
    public BlankFinal(int x) {
        this.j = x;
        this.p = new Poppet(x);
    }
    public static void main(String args[]) {
        new BlankFinal();
        new BlankFinal(42);
    }
}

class Poppet {
    private int i;
    Poppet(int ii) {
        this.i = ii;
    }
}
