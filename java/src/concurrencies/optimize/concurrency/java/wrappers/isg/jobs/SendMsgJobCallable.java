package concurrency.java.wrappers.isg.jobs;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SendMsgJobCallable<V> implements Callable<V> {

	public static AtomicLong jobsProcessed = new AtomicLong(0);
	private static MessageSender msgSender;

	private static AtomicLong msgNumber = new AtomicLong(0);

	public SendMsgJobCallable() {
		if (msgSender == null)
			msgSender = new MessageSender();

	}

	@Override
	public V call() throws Exception {

		msgNumber.incrementAndGet();
		try {

			System.out.println("sending msg....msgNumber : " + msgNumber);

			msgSender.sendTextToQueue();
			jobsProcessed.incrementAndGet();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return (V) jobsProcessed;
	}
}