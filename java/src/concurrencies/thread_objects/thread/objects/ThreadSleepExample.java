package thread.objects;

/**
 * Pausing execution of a Thread using sleep()<br/>
 * The sleep() method provided by Thread class allows you to pause the execution
 * of the currently executing thread for the specified number of milliseconds.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ThreadSleepExample {

	public static void main(String[] args) {
		System.out.println("Inside : " + Thread.currentThread().getName());

		String[] messages = { "If I can stop one heart from breaking,", "I shall not live in vain.",
				"If I can ease one life the aching,", "Or cool one pain,", "Or help one fainting robin",
				"Unto his nest again,", "I shall not live in vain" };

		Runnable runnable = () -> {
			System.out.println("Inside : " + Thread.currentThread().getName());

			for (String message : messages) {
				System.out.println(message);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					throw new IllegalStateException(e);
				}
			}
		};

		Thread thread = new Thread(runnable);

		thread.start();
	}
}