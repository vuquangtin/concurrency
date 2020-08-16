package concurrency.java.optimize.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import concurrency.java.bounded.blocking.BoundedBufferQueue;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BoundedBufferQueueTest {

    public static void main(String[] args) {

        BoundedBufferQueue<Integer> queue = new BoundedBufferQueue<>(1200);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(new Producer(queue));
        executorService.execute(new Consumer(queue));

    }

    static class Producer implements Runnable {

        private BoundedBufferQueue queue;

        public Producer(BoundedBufferQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                int num = (int) (Math.random() * 100);
                try {
                    queue.put(num);
                    System.out.println(Thread.currentThread().getName() + " producer " + num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {

        private BoundedBufferQueue queue;

        public Consumer(BoundedBufferQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Integer num = (Integer) queue.take();
                    System.out.println(Thread.currentThread().getName() + " consume " + num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}