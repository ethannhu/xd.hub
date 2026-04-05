/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets.pkg1;

/**
 *
 * @author 20692
 */
public class A{
	public A(){ System.out.println("A's constructor"); }
	void func(){ System.out.println("A‘s method"); } //func()为包权限
}
class B{//B为包权限
	public B(){ System.out.println("B's constructor"); }
}

