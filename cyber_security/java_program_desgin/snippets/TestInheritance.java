/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

/**
 *
 * @author 20692
 */
public class TestInheritance{
	public static void main(String[] args){
		Rectangle rect=new Rectangle();
		rect.newDraw();
	}
}
class Shape{
	public void draw(){ System.out.println("Draw shape"); }
}
class Rectangle extends Shape{
	public void draw(){ System.out.println("Draw Rectangle"); }
	public void newDraw(){
		draw();
		super.draw();
	}
}

