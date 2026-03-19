/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author 20692
 */
public class BufferedIO{
	public static void main(String[] args) throws IOException{
		BufferedReader in=new BufferedReader(
				new FileReader("BufferedIO.java"));
		PrintWriter out=new PrintWriter( new BufferedWriter(
				new FileWriter("BufferedIO.txt")));
		String s;
		int linecnt=1;
		StringBuilder sb=new StringBuilder();
		while((s=in.readLine())!=null){
			sb.append(linecnt+":"+s+"\n");
			out.println(linecnt+":"+s);
			linecnt++;
		}
		in.close();
		out.close();
		System.out.print(sb.toString());
	}
}

