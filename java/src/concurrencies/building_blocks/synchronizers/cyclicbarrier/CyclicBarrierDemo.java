package synchronizers.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class CyclicBarrierDemo {
	public static void main(String[] args) {
		Runnable action = new Runnable() {
			@Override
			public void run() {
				String name = Thread.currentThread().getName();
				System.out.printf("Thread %s " + "executing barrier action.%n",
						name);
			}
		};
		final CyclicBarrier barrier = new CyclicBarrier(3, action);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				String name = Thread.currentThread().getName();
				System.out.printf("%s about to join game...%n", name);
				try {
					barrier.await();
				} catch (BrokenBarrierException bbe) {
					System.out.println("barrier is broken");
					return;
				} catch (InterruptedException ie) {
					System.out.println("thread interrupted");
					return;
				}
				System.out.printf("%s has joined game%n", name);
			}
		};
		ExecutorService[] executors = new ExecutorService[] {
				Executors.newSingleThreadExecutor(),
				Executors.newSingleThreadExecutor(),
				Executors.newSingleThreadExecutor() };
		for (ExecutorService executor : executors) {
			executor.execute(task);
			executor.shutdown();
		}
	}
}