package learn.threads.vn.docs2;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ShareMemoryTest {

	public static void main(String[] args) {

		ShareMemory sm = new ShareMemory();
		WorkingThreadShareMemory thread1 = new WorkingThreadShareMemory(sm,
				"Thread1");
		WorkingThreadShareMemory thread2 = new WorkingThreadShareMemory(sm,
				"Thread2");
		WorkingThreadShareMemory thread3 = new WorkingThreadShareMemory(sm,
				"Thread3");

		thread1.start();
		thread2.start();
		thread3.start();
	}

}
