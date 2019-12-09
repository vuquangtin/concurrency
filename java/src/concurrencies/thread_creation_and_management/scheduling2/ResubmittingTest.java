package scheduling2;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ResubmittingTest {
	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService service2 = new ResubmittingScheduledThreadPoolExecutor(
				5, new ScheduledExceptionHandler() {
					private AtomicInteger problems = new AtomicInteger();

					@Override
					public boolean exceptionOccurred(Throwable e) {
						e.printStackTrace();
						if (problems.incrementAndGet() >= 5) {
							System.err.println("We give up!");
							System.exit(0);
							return false;

						}
						System.err.println("Resubmitting task to scheduler");
						return true;
					}
				}); // new MyScheduledExceptionHandler());
		service2.scheduleAtFixedRate(new MyRunnable(), 2, 1, TimeUnit.SECONDS);
	}

	private static class MyRunnable implements Runnable {
		public void run() {
			if (Math.random() < 0.3) {
				System.out.println("I have a problem");
				throw new IllegalArgumentException("I have a problem");
			}
			System.out.println("I'm happy");
		}
	}

	/** As an example, we give up after 5 failures. */
	protected static class MyScheduledExceptionHandler implements
			ScheduledExceptionHandler {
		private AtomicInteger problems = new AtomicInteger();

		public boolean exceptionOccurred(Throwable e) {
			e.printStackTrace();
			if (problems.incrementAndGet() >= 5) {
				System.err.println("We give up!");

				return false;

			}
			System.err.println("Resubmitting task to scheduler");
			return true;
		}
	}
}
