package blockingqueues.examples;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class TransferQueueExample {

	TransferQueue<String> queue = new LinkedTransferQueue<String>();

	class Producer implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < 2; i++) {
				try {
					System.out.println("Producer waiting to transfer: " + i);
					queue.transfer("" + i);
					System.out.println("Producer transfered: " + i);
					CountDownLatch latch = new CountDownLatch(1);
					CyclicBarrier barrier = new CyclicBarrier(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	class Consumer implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < 2; i++) {
				try {
					Thread.sleep(2000);
					System.out.println("Consumer waiting to comsume: " + i);
					queue.take();
					System.out.println("Consumer consumes: " + i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String args[]) {
		TransferQueueExample example = new TransferQueueExample();
		new Thread(example.new Producer()).start();
		new Thread(example.new Consumer()).start();
	}

}