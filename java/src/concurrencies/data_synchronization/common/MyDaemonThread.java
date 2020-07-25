package common;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyDaemonThread extends Thread {

	private int i = 0;

	@Override
	public void run() {
		while (true) {
			try {
				i++;
				System.out.println("守护线程正在工作-i=" + (i));
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		MyDaemonThread daemonThread = new MyDaemonThread();
		daemonThread.setDaemon(true);
		daemonThread.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("用户线程main结束了，守护线程daemonThread不再工作");
	}
}