/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

/**
 *
 * @author 20692
 */
public class NotOverriding extends Base{
	private int i=2;
	public static void main(String[] args){
		NotOverriding no=new NotOverriding();
		no.increase();
		System.out.println(no.i);
		System.out.println(no.getI());
	}
}
class Base{
	private int i=100;
	public void increase(){
		this.i++;
	}
	public int getI(){
		return this.i;
	}
}

