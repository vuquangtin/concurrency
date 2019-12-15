package running.threads.in.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadCall extends Thread {

    private BlockingQueue<Integer> bq = new ArrayBlockingQueue<Integer>(10);
    private ThreadCall next;

    public void setNext(ThreadCall t) {
        this.next = t;
    }

    public void addElBQ(int a) {
        this.bq.add(a);
    }

    public ThreadCall(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        int x = 0;
        while(true) {
            try {
                x = 0;
                x = bq.take();
                if (x!=0) {
                    System.out.println(Thread.currentThread().getName() + " =>" + x);
                    if (x >= 100) System.exit(0); // Need to stop all running threads
                    next.addElBQ(x+1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int THREAD_COUNT = 10;
        List<ThreadCall> listThread = new ArrayList<>();

        for (int i=1; i<=THREAD_COUNT; i++) {
            listThread.add(new ThreadCall("Thread " + i));
        }

        for (int i = 0; i < listThread.size(); i++) {
            if (i == listThread.size()-1) {
                listThread.get(i).setNext(listThread.get(0));
            }
            else listThread.get(i).setNext(listThread.get(i+1));
        }

        listThread.get(0).addElBQ(1);

        for (int i = 0; i < listThread.size(); i++) {
            listThread.get(i).start();
        }
    }
}