/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

/**
 *
 * @author 20692
 */
public class PrivOverride {
	private void f() { System.out.println("private f()"); }
	public static void main(String[] args) {
		PrivOverride po = new Derived();
		po.f();
	}
}
class Derived extends PrivOverride {
	public int a;
	public void f() { System.out.println("public f()"); }
}

