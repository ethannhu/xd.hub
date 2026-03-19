/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

/**
 *
 * @author 20692
 */
class Useful {
	public void f() { }
	public void g() { }
}
class MoreUseful extends Useful {
	public void f() { }
	public void g() { }
	public void u() { }
	public void v() { }
	public void w() { }
}
public class RTTI {
	public static void main(String[] args) {
		Useful[] x = { new Useful(), new MoreUseful() };
		x[0].f();
		x[1].g();
		// Compile time: method not found in Useful:
		// ！ x[1].u();
		((MoreUseful) x[1]).u(); // Downcast/RTTI
		((MoreUseful) x[0]).u(); // 抛出异常
	}
}

