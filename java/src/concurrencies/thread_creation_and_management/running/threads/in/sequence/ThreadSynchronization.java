package running.threads.in.sequence;

import java.util.ArrayList;
import java.util.List;

public class ThreadSynchronization {

    public static int i = 1;
    public static final int maxThreads = 10;

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < maxThreads; i++) {
            list.add(new Object());
        }
        Object currObject = list.get(maxThreads - 1);
        for (int i = 0; i < maxThreads; i++) {
            Object nextObject = list.get(i);
            RunnableClass a = new RunnableClass(currObject, nextObject, i == 0 ? true : false);
            Thread th = new Thread(a);
            th.setName("Thread - " + (i + 1));
            th.start();
            currObject = list.get(i);
        }
    }

}

class RunnableClass implements Runnable {

    private Object currObject;
    private Object nextObject;
    private boolean firstThread;

    public RunnableClass(Object currObject, Object nextObject, boolean first) {
        this.currObject = currObject;
        this.nextObject = nextObject;
        this.firstThread = first;
    }

    @Override
    public void run() {
        int i = 0;
        try {
            if (firstThread) {
                Thread.sleep(5000);
                firstThread = false;
                System.out.println(Thread.currentThread().getName() + " - " + ThreadSynchronization.i++);
                synchronized (nextObject) {
                    nextObject.notify();
                }
            }
            while (i++ < Integer.MAX_VALUE) {
                synchronized (currObject) {
                    currObject.wait();
                }
                System.out.println(Thread.currentThread().getName() + " - " + ThreadSynchronization.i++);
                Thread.sleep(1000);
                synchronized (nextObject) {
                    nextObject.notify();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}