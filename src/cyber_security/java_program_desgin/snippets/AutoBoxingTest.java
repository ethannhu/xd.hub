/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

/**
 *
 * @author 20692
 */
public class AutoBoxingTest {
	public static void main(String args[]) {
		Integer x, y;
		int c;
		x = 22;// autoboxing
		y = 15;// autoboxing
		if ((c = x.compareTo(y)) == 0)
			System.out.println("x is equal to y");
		else if (c < 0)
			System.out.println("x is less than y");
		else
			System.out.println("x is greater than y");
		System.out.println("x + y = " + (x + y));// autounboxing
	}
}

