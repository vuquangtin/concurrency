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
public class MyThread4 {
	Countx count = new Countx();

	public MyThread4() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 500; i++) {
					count.in();

					try {
						Thread.sleep(1); // Tạm dừng thực hiện chương trình 1
											// mili giây
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 500; i++) {
					count.out();
				}

			}
		}).start();

	}

	public static void main(String[] args) {

		new MyThread4();
	}

}

class Countx {
	public int i = 0, j = 0;

	public void in() {
		i++;
		j++;
	}

	public void out() {
		System.out.println("down i: " + i);
		System.out.println("down j: " + j);
	}
}