/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

/**
 *
 * @author 20692
 */
public class TestInit {
    int x;
    public static void main(String args[]) {
        TestInit init = new TestInit();
        int x = (int)(Math.random()*100);
        int y=0;
        int z;
        if(x>50){
            y=9;
        }
        z=x+y+init.x;
        System.out.println("x:"+x+" y:"+y+" z:"+z+" init.x:"+init.x);
    }
}
