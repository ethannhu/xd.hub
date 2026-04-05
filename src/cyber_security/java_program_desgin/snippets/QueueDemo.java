/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author 20692
 */
public class QueueDemo {
	public static void printQ(Queue queue) {
		while (queue.peek() != null)
			System.out.print(queue.remove() + " ");
		System.out.println();
	}
	public static void main(String[] args) {
		Queue<Integer> queue = new LinkedList<Integer>();
		Random rand = new Random(47);
		for (int i = 0; i < 10; i++)
			queue.offer(rand.nextInt(i + 10));
		printQ(queue);

		Queue<Character> qc = new LinkedList<Character>();
		for (char c : "Brontosaurus".toCharArray())
			qc.offer(c);
		printQ(qc);
	}
}		//又例：Counter.java

