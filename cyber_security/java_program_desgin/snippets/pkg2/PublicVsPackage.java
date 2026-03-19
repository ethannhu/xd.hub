/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets.pkg2;

/**
 *
 * @author 20692
 */
import snippets.pkg1.*;
public class PublicVsPackage{
	public static void main(String[] args){
		A obj=new A();
		//B obj2=new B(); 	//在pkg1之外不能创建B的对象
		//obj.func(); 		//在pkg1之外不能访问func()方法
	}
}