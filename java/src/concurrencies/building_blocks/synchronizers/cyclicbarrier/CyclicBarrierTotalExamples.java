package synchronizers.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import synchronizers.Data;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class CyclicBarrierTotalExamples {
	final static int MAX = 100;
	public static final Data dataShare = new Data();

	public static void main(String[] args) {

		CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {

			@Override
			public void run() {
				System.out.println("total a+b=" + dataShare.getTotal());
				
			}
		});

		Thread threadA = new Thread(new RandomRunnable(0, barrier));
		Thread threadB = new Thread(new RandomRunnable(1, barrier));
		threadA.start();
		threadB.start();
		try {
			threadA.join();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	private static class RandomRunnable implements Runnable {
		private int index;
		private CyclicBarrier barrier;

		public RandomRunnable(int index, CyclicBarrier barrier) {
			this.index = index;
			this.barrier = barrier;
		}

		@Override
		public void run() {
			for (int i = 0; i < MAX; i++) {
				int r = new Random().nextInt(MAX);
				if (index == 1) {
					System.out.println("B="+r);
					dataShare.setB(r);
				} else {
					System.out.println("A="+r);
					dataShare.setA(r);
				}
				try {
					barrier.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

}
