/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

/**
 *
 * @author 20692
 */
public class InheritStaticInit {
    public static void main(String args[]){
        new T2();
    }
}

class T1{
    static int s1 = 1;
    static {
        System.out.println("Static code of T1. T2.s2: " + T2.s2 + " T1.s1: "+T1.s1);
        
    }
    T1() {
        System.out.println("T1's constuctor. T1.s1: " +s1);
    }
}


class T2 extends T1 {
    static int s2 = 2;
    static {
        System.out.println("Static code of T2. T2.s2:" +T2.s2);
    }
    T2() {
        System.out.println("T2's constuctor. T2.s2:" +s2);
    }
}