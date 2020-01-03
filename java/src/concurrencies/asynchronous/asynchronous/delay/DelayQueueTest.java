package asynchronous.delay;

import java.io.IOException;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 注入其中的元素必须实现 java.util.concurrent.Delayed 接口 deplay 设置的不是expire 时间，
 * 设置的是延迟多久才可以用的时间 传递给 getDelay 方法的 getDelay 实例是一个枚举类型，它表明了将要延迟的时间段。TimeUnit 枚举
 */
public class DelayQueueTest {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		DelayQueue<DelayedDemo> delayQueue = new DelayQueue<DelayedDemo>();
		DelayedDemo delayedDemo = new DelayedDemo(10000, 1); // deplay
																// 设置的不是expire
																// 时间，
																// 设置的是延迟多久才可以执行的时间
		delayQueue.put(delayedDemo);
		DelayedDemo delayedDemo2 = new DelayedDemo(15000, 2); // deplay
																// 设置的不是expire
																// 时间，
																// 设置的是延迟多久才可以执行的时间
		delayQueue.put(delayedDemo2);
		DelayedDemo delayedDemo3 = new DelayedDemo(5000, 3); // deplay
																// 设置的不是expire
																// 时间，
																// 设置的是延迟多久才可以执行的时间
		delayQueue.put(delayedDemo3);
		DelayedDemo delayedDemo4 = new DelayedDemo(7000, 4); // deplay
																// 设置的不是expire
																// 时间，
																// 设置的是延迟多久才可以执行的时间
		delayQueue.put(delayedDemo4);
		DelayedDemo delayedDemo5 = new DelayedDemo(6000, 5); // deplay
																// 设置的不是expire
																// 时间，
																// 设置的是延迟多久才可以执行的时间
		delayQueue.put(delayedDemo5);
		while (true) {
			System.out.println(delayQueue.take().getType()); // 五秒后才可以使用
		}
	}

}

class DelayedDemo implements Delayed {
	private long expireTime;
	private long type;

	public DelayedDemo(long delay, long type) {
		this.expireTime = delay + System.currentTimeMillis();
		this.type = type;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(expireTime - System.currentTimeMillis(),
				TimeUnit.MILLISECONDS);
	}

	@Override
	public int compareTo(Delayed o) {
		DelayedDemo that = (DelayedDemo) o;
		if (this.expireTime > that.expireTime) {// 过期时刻越靠后，越排在队尾.
			return 1;
		} else if (this.expireTime == that.expireTime) {
			return 0;
		} else {
			return -1;
		}
	}

	public long getType() {
		return type;
	}
}