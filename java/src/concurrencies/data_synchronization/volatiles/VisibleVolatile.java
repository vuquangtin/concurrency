package volatiles;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class VisibleVolatile extends Thread {

	private volatile boolean flag = true;

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		System.out.println("开始循环>>>>>>>>>>>>>>");
		while (flag) {
			System.out.println("is while");
		}
		System.out.println("flag值改变，线程结束！！！");
	}

	public static void main(String[] args) throws InterruptedException {
		VisibleVolatile volatileThread = new VisibleVolatile();
		volatileThread.start();
		Thread.sleep(1000);
		volatileThread.setFlag(false);
	}
}