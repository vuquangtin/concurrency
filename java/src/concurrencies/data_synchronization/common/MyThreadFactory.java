package common;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MyThreadFactory implements ThreadFactory {
    private int counter;
    private String name;
    private List<String> stats;

    public MyThreadFactory(String name) {
        counter = 0;
        this.name = name;
        stats = new ArrayList<>();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, name + "-Thread_" + counter);
        counter++;
        stats.add("Created thread " + t.getId() + " with name " + t.getName() + " on " + new Date());
        return t;
    }

    public String getStatus() {
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = stats.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MyThreadFactory factoryTest = new MyThreadFactory("MyThreadFactory");
        Thread thread;

        for (int i = 0; i < 5; i++) {
            thread = factoryTest.newThread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        System.out.println("Factory stats:" + factoryTest.getStatus());
    }
}