package threadpools.custom;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class Business implements Runnable {

	private int i;

	public Business(int i) {
		this.i = i;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("sleep:" + 4000);
			Thread.sleep(4000);
			System.out.println(Thread.currentThread().getName());
			System.out.println(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}