package blockingqueues.examples;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class LinkedBlockingQueueExample {
    private static BlockingQueue<Integer> blockingQueue= new LinkedBlockingQueue<>(1);

    public static void main(String[] args) {
        Thread producer= new Thread(LinkedBlockingQueueExample::runProducer);
        Thread consumer= new Thread(LinkedBlockingQueueExample::runConsumer);

        producer.start();
        consumer.start();
    }

    private static void runProducer(){
        for(int i=1; i<=10;i++){
            try {
                blockingQueue.put(i);
                System.out.println("Producing: "+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void runConsumer(){
        for (int i=0;i<=10; i++){
            try {
                Thread.sleep(1000);
                System.out.println("Consuming: "+blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}