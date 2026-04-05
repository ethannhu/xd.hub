/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package countIntersection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author 20692
 */
public class Counter {

    public static String count(float[] a, float[] b) {
        HashMap<Integer, Integer> intersection = new HashMap<>();
        float[] union = new float[a.length + b.length];
        System.arraycopy(a, 0, union, 0, a.length);
        System.arraycopy(b, 0, union, a.length, b.length);
        for (float floatNum : union) {
            int numKey = Math.round(floatNum);
            if (intersection.containsKey(numKey)) {
                int prev_cnt = intersection.get(numKey);
                intersection.put(numKey, prev_cnt + 1);
            } else {
                intersection.put(numKey, 1);
            }
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = intersection.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            if (entry.getValue() == 1) {
                iterator.remove();
            }
        }
        return intersection.toString();
    }
    
    public static void main(String args[]) {
        float[] f1 = {3.1f, 4.2f, 5.5f, 6.5f};
        float[] f2 = {3.2f, 7.1f, 3.5f, 6.6f};
        String result = Counter.count(f1, f2);
        System.out.println(result);
    }
}
