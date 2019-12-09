package executors.schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Design Patterns
 * 
 * @see <a
 *      href="https://github.com/coder-chi/study/tree/b43d66db9c6959fb723e4a444ae2ee8d6c6190ba/diarystudy/src/main/java">link git</a>
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class ScheduledThreadPoolExecutorDemo {
	static class TimerTask implements Runnable {
		private String id;

		public TimerTask(String id) {
			this.id = id;
		}

		@Override
		public void run() {
			System.out.println("任务为： " + id + " 现在时间为："
					+ System.currentTimeMillis());
		}
	}

	public static void main(String[] args) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);
		ScheduledFuture sfa = ses.scheduleAtFixedRate(new TimerTask("a"), 200,
				1000, TimeUnit.MILLISECONDS);
		ScheduledFuture sfb = ses.scheduleAtFixedRate(new TimerTask("b"), 400,
				1000, TimeUnit.MILLISECONDS);
		ScheduledFuture sfc = ses.scheduleAtFixedRate(new TimerTask("c"), 600,
				1000, TimeUnit.MILLISECONDS);
		ScheduledFuture sfd = ses.scheduleAtFixedRate(new TimerTask("d"), 800,
				1000, TimeUnit.MILLISECONDS);
		try {
			Thread.sleep(5000);
			sfa.cancel(true);
			Thread.sleep(5000);
			ses.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}