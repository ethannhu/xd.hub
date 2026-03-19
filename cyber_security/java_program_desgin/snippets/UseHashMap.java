/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snippets;

import java.util.HashMap;

/**
 *
 * @author 20692
 */
public class UseHashMap {
        public static void main(String args[]) {
	HashMap<String, String> hScore = new HashMap<String, String>();
	hScore.put("张一", "86");
	hScore.put("李二", "98");
	hScore.put("海飞", "99");
	System.out.println("按字符串输出：" + hScore.toString());
	hScore.put("李二", "77");
	hScore.remove("张一");
	System.out.println("修改并删除之后");
	System.out.println("按字符串输出：" + hScore.toString());
        }
}	//又例：Statistics.java

