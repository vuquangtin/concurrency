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
public class Consumer2 extends Thread {
	Producer producer;
	String name;

	Consumer2(String name, Producer producer) {
		this.producer = producer;
		this.name = name;
	}

	public void run() {
		try {
			while (true) {
				String message = producer.getMessage();
				System.out.println(name + " got message: " + message);
				sleep(2000);
			}
		} catch (InterruptedException e) {
		}
	}

	public static void main(String args[]) {
		Producer producer = new Producer();
		producer.start();

		// Start two this time
		new Consumer2("One", producer).start();
		new Consumer2("Two", producer).start();
	}
}
