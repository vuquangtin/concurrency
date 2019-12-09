package basic.containers;

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
public class VectorExample2 {

    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {

        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread thread1 = new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                        vector.get(i);
                    }
                }
            };

            Thread thread2 = new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.get(i);
                    }
                }
            };
            /*在上述的写法当中如果，当remove之前线程2获得了vector的下标，但是get()方法执行的时候remove方法已经执行，就会出现越界的问题
            但是将get()方法放置在线程1当中的话还是会出现线程安全的问题*/
            thread1.start();
            thread2.start();
        }
    }

}
