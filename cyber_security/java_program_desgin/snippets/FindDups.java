/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author 20692
 */
public class FindDups {
	public static void main(String args[]) {
		// 创建一个HashSet对象，缺省的初始容量是16
		Set<String> s = new HashSet<String>();
		// 将命令行中的每个字符串加入到集合s中，
		//其中重复的字符串将不能加入，并被打印输出
		for (int i = 0; i < args.length; i++) {
		        if (!s.add(args[i]))
			System.out.println("Duplicate detected: " + args[i]);
		}
		// 输出集合s的元素个数以及集合中的所有元素：
		System.out.println(s.size() + " distinct words detected: " + s);
	}
}

