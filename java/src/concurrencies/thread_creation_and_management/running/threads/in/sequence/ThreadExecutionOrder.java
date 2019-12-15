package running.threads.in.sequence;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/***
 * @see https://stackoverflow.com/questions/15997483/how-to-ensure-java-threads-run-in-a-particular-order
 * @author admin
 *
 */
public class ThreadExecutionOrder {

	private CountDownLatch countDownLatch = new CountDownLatch(2);
	private CountDownLatch countDownLatch1 = new CountDownLatch(1);
	private CyclicBarrier barrier;
	private final Object monitor = new Object();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ThreadExecutionOrder().test();
	}

	public void test() {
		Runnable t1 = new Runnable() {

			@Override
			public void run() {
				System.out.print("A");
				countDownLatch1.countDown();
				countDownLatch.countDown();
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}

		};
		Runnable t2 = new Runnable() {

			@Override
			public void run() {
				try {
					countDownLatch1.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print("B");
				countDownLatch.countDown();
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}

		};
		Runnable t3 = new Runnable() {

			@Override
			public void run() {
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print("C");
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}

		};
		for (int i = 0; i < 3; i++) {
			barrier = new CyclicBarrier(3, new Runnable() {
				@Override
				public void run() {
					synchronized (monitor) {
						countDownLatch = new CountDownLatch(2);
						countDownLatch1 = new CountDownLatch(1);
						monitor.notify();
					}
				}

			});
			new Thread(t1).start();
			new Thread(t2).start();
			new Thread(t3).start();
			synchronized (monitor) {
				try {
					monitor.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}