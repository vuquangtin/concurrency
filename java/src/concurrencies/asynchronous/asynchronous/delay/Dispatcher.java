package asynchronous.delay;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * decription:发起一个请求，但我不期望立即执行，可能延后多少秒再执行，如果你来做这个服务端，你该如何设计？
 * </p>
 * <p>
 * date:2015年8月20日 下午4:48:09
 * </p>
 * 
 * @author gsu·napoleon
 */
public class Dispatcher {

	private DelayQueue<RequestDelayed> delayQueue = new DelayQueue<RequestDelayed>();

	public void run(DelayQueue<RequestDelayed> lower,
			DelayQueue<RequestDelayed> higher) {
		RequestDelayed requestDelayed1 = new RequestDelayed(10);
		RequestDelayed requestDelayed2 = new RequestDelayed(5);
		delayQueue.add(requestDelayed1);
		delayQueue.add(requestDelayed2);
		try {
			RequestDelayed runRequestDelayed = delayQueue.take();
			runRequestDelayed.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Dispatcher dispatcher = new Dispatcher();
		dispatcher.run(null, null);
	}

}

class RequestDelayed implements Delayed, Runnable {

	private int seconds;

	public RequestDelayed(int seconds) {
		this.seconds = seconds;
	}

	public void run() {
		System.err.println("****************** seconds : " + seconds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Delayed#getDelay(java.util.concurrent.TimeUnit)
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.toSeconds(seconds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		return 0;
	}

}