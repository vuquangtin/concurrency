package asynchronous.delay;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue的使用
 * 
 * @author brucexiajun DelayedTask的构造函数的参数表示的就是等待时间
 *         主函数中，先将等待时间小于5000的各任务加入到队列中，最后加入等待时间等于5000的队列
 *         执行任务的时候，按照等待时间从短到长开始执行，先执行id为0到19的任务
 *         执行id为20的任务的时候，将会按照创建的先后打印出所有的任务（包括20） 最后20号任务结束线程 ./Concurrency/New
 *         Library Components
 */
class DelayedTask implements Runnable, Delayed {
	private static int counter = 0;
	private final int id = counter++;
	private final int delta;
	private final long trigger;
	protected static List<DelayedTask> sequence = new ArrayList<DelayedTask>();

	public DelayedTask(int delay) {
		delta = delay;
		trigger = System.nanoTime() + NANOSECONDS.convert(delta, MILLISECONDS);

		sequence.add(this);// 按照task被创立的顺序将其加入到表中
	}

	public long getDelay(TimeUnit unit) {
		// System.nanoTime()返回值表示从某一固定但任意的时间算起的毫微秒数（或许从以后算起，所以该值可能为负）
		return unit.convert(trigger - System.nanoTime(), NANOSECONDS);

	}

	public int compareTo(Delayed delay) {
		DelayedTask that = (DelayedTask) delay;
		if (trigger < that.trigger) {
			return -1;
		} else {
			return trigger > that.trigger ? 1 : 0;
		}
	}

	public void run() {
		System.out.println(this + " ");
	}

	public String toString() {
		return String.format("[%1$-4d]", delta) + "Task" + id;
	}

	public String summary() {
		return "(" + id + ":" + delta + ")";
	}

	public static class EndSentinel extends DelayedTask {
		private ExecutorService exec;

		public EndSentinel(int delay, ExecutorService e) {
			super(delay);
			exec = e;
		}

		public void run() {
			for (DelayedTask pt : sequence) {
				System.out.println(pt.summary() + " ");
			}
			System.out.println(this + " Calling shutdownNow()");
			exec.shutdownNow();
		}
	}

}

class DelayedTaskConsumer implements Runnable {
	private DelayQueue<DelayedTask> q;

	public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
		this.q = q;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				q.take().run();// 取队列(queue)尾部元素执行，即按等待时间从短到长取元素执行，会调用DelayedTask的run方法和EndSentinel的run方法
			}
		} catch (InterruptedException e) {
		}
		System.out.println("Finished DelayedTaskConsumer");
	}

}

public class DelayQueueDemo {
	public static void main(String args[]) {
		Random random = new Random(47);
		ExecutorService exec = Executors.newCachedThreadPool();
		DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();
		for (int i = 0; i < 20; i++) {
			queue.put(new DelayedTask(random.nextInt(5000)));// 按照task等待时间的长短将其插入到队列中
		}
		queue.put(new DelayedTask.EndSentinel(5000, exec));// 将id为20,延迟为5000的任务加入到队列中，这个任务会最后执行
		exec.execute(new DelayedTaskConsumer(queue));
	}
}