/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets.pkg2;

/**
 *
 * @author 20692
 */
import snippets.pkg1.C;
public class ProtectedVsPackageAndPublic{
	public static void main(String[] args){
		C obj=new C();
		//! obj.func();	//不是C的子类，且与C非同一个包
	}
}
class CSub extends C{		//C的子类，可以访问C的func()方法
	void mtd(C parent, CSub sub){
		func();
		//! parent.func();  //应通过子类引用而非父类引用访问func()
		sub.func();
	}
}