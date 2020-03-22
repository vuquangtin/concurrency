package learn.threads.vn;

import java.util.concurrent.Semaphore;

/**
 * Mutual Exclusive. Có thể hiểu là "Loại trừ lẫn nhau". Cách này hệ thống sẽ
 * giúp ưu tiên một Thread và giúp ngăn chặn các Thread khác, khỏi nguy cơ xung
 * đột với nhau. Do đặc tính này của cơ chế làm chúng ta liên tưởng tới một sự
 * can thiệp mạnh tay, quyết liệt của hệ thống, nên mới có cái tên “loại trừ”.
 * Cách này sẽ được mình gói gọn trong bài học kế tiếp. <br/>
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MutualExclusive {
	public static void main(String args[]) {
		final BinarySemaphore test = new BinarySemaphore();
		new Thread() {
			@Override
			public void run() {
				test.mutualExclusion();
			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				test.mutualExclusion();
			}
		}.start();

	}
}

/**
 * Counting Semaphore Example in Java (Binary Semaphore) <br/>
 * a Counting semaphore with one permit is known as binary semaphore because it
 * has only two state permit available or permit unavailable. Binary semaphore
 * can be used to implement mutual exclusion or critical section where only one
 * thread is allowed to execute. Thread will wait on acquire() until Thread
 * inside critical section release permit by calling release() on semaphore.
 * 
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
class BinarySemaphore {

	Semaphore binary = new Semaphore(1);

	public void mutualExclusion() {
		try {
			binary.acquire();

			// mutual exclusive region
			System.out.println(Thread.currentThread().getName() + " inside mutual exclusive region");
			Thread.sleep(1000);

		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} finally {
			binary.release();
			System.out.println(Thread.currentThread().getName() + " outside of mutual exclusive region");
		}
	}

}
/***
 * OuptPut : Thread-0 inside mutual exclusive region Thread-0 outside of mutual
 * exclusive region Thread-1 inside mutual exclusive region Thread-1 outside of
 * mutual exclusive region
 * 
 */