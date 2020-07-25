package consumer;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class Client {
	public static void main(String[] args) {
		EventStorage storage = new EventStorage();

		Producer producer = new Producer(storage);
		Thread thread1 = new Thread(producer);

		Consumer consumer = new Consumer(storage);
		Thread thread2 = new Thread(consumer);

		thread2.start();
		thread1.start();
	}
}