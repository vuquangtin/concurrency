package basic.containers;

import java.util.Iterator;
import java.util.Vector;

import basic.annotations.NotThreadSafe;

/**
 * @author panghu
 * @Title: VectorExample2
 * @ProjectName cuncurrency_demo
 * @Description: TODO
 * @date 19-2-21 上午10:00
 */
@NotThreadSafe
public class VectorExample3 {

    /**
     * java.util.ConcurrentModificationException
     **/
    private static void test1(Vector<Integer> v1){
        for (Integer i:v1
             ) {
            if (i.equals(3)){
                v1.remove(i);
            }
        }
    }

    /**
     * java.util.ConcurrentModificationException
     * @param v1
     */
    private static void test2(Vector<Integer> v1){
        Iterator<Integer> integerIterator = v1.iterator();
        while (integerIterator.hasNext()){
            Integer i = integerIterator.next();
            if (i.equals(3)){
                v1.remove(i);
            }
        }
    }

    /**
     *没有抛出异常
     * @param v1
     */
    private static void test3(Vector<Integer> v1){
        for (int i = 0; i < v1.size(); i++) {
            if (v1.get(i).equals(3)){
                v1.remove(i);
            }
        }
    }

    public static void main(String[] args) {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        test3(vector);
    }
}

