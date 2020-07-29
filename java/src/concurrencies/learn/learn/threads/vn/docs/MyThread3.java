package learn.threads.vn.docs;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MyThread3 {
	Count count = new Count();

	public MyThread3() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				count.up();
				System.out.println("up: " + count.c);
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				count.down();
				System.out.println("dowwn: " + count.c);

			}
		}).start();
	}

	public static void main(String[] args) {
		new MyThread3();

	}

}

class Count {
	int c = 0;

	synchronized public void up() {
		for (int i = 0; i < 500; i++) {
			System.out.println("up: " + c++);
		}
	}

	synchronized public void down() {
		for (int i = 0; i < 500; i++) {
			System.out.println("down: " + c--);
		}
	}

}
