package learn.threads.vn;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class RunnableTest implements Runnable {
	String name;

	public RunnableTest(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		try {
			// Bắt đầu xử lý
			System.out.println(Thread.currentThread().getName()
					+ " Starting process " + name);
			Thread.sleep(500);
			System.out.println(Thread.currentThread().getName()
					+ " Finished process " + name);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		// Theo cách truyền thống thì để thực thi RunnableTest ta phải tạo mới 1
		// Thread sau đó truyền RunnableTest vào.
		new Thread(new RunnableTest("T1")).start();
	}
}