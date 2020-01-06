package thread.objects;

/**
 * Note that, instead of creating a class which implements Runnable and then
 * instantiating that class to get the runnable object, you can create an
 * anonymous runnable by using Java’s anonymous class syntax. <br/>
 * Anonymous classes enable you to make your code more concise. They enable you
 * to declare and instantiate a class at the same time. - From Java doc.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class RunnableExampleAnonymousClass {

	public static void main(String[] args) {
		System.out.println("Inside : " + Thread.currentThread().getName());

		System.out.println("Creating Runnable...");
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Inside : " + Thread.currentThread().getName());
			}
		};

		System.out.println("Creating Thread...");
		Thread thread = new Thread(runnable);

		System.out.println("Starting Thread...");
		thread.start();
	}
}
