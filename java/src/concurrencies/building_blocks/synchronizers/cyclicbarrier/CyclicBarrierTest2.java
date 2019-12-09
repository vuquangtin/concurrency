package synchronizers.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class CyclicBarrierTest2 {
	/**
	 * 学生总数
	 */
	private final int STUDENT_COUNT = 12;

	final CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
		@Override
		public void run() {
			System.out.println("有三个同学到齐了，放行");
		}
	});

	public void goHome() throws InterruptedException, BrokenBarrierException {
		System.out.println(Thread.currentThread().getName() + "已刷卡，等待开门回家~");
		barrier.await();
	}

	public void run() {
		for (int i = 0; i < STUDENT_COUNT; i++) {
			new Thread("学生" + i + "  ") {
				@Override
				public void run() {
					try {
						goHome();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	
	}

	public static void main(String arg[]) {
		new CyclicBarrierTest2().run();
	}
}
