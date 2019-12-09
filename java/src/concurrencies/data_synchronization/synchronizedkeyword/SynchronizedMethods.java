package synchronizedkeyword;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class SynchronizedMethods {

	private int sum = 0;

	public void calculate() {
		setSum(getSum() + 1);
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	@Test
	public void givenMultiThread_whenNonSyncMethod() {
		ExecutorService service = Executors.newFixedThreadPool(3);
		SynchronizedMethods summation = new SynchronizedMethods();

		IntStream.range(0, 1000).forEach(
				count -> service.submit(summation::calculate));
		try {
			service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(1000, summation.getSum());
	}

	public synchronized void synchronisedCalculate() {
		setSum(getSum() + 1);
	}

	@Test
	public void givenMultiThread_whenMethodSync() {
		ExecutorService service = Executors.newFixedThreadPool(3);
		SynchronizedMethods method = new SynchronizedMethods();

		IntStream.range(0, 1000).forEach(
				count -> service.submit(method::synchronisedCalculate));
		try {
			service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(1000, method.getSum());
	}

	private static int staticSum = 0;

	public static synchronized void syncStaticCalculate() {
		staticSum = staticSum + 1;
	}

	@Test
	public void givenMultiThread_whenStaticSyncMethod() {
		ExecutorService service = Executors.newCachedThreadPool();

		IntStream.range(0, 1000).forEach(
				count -> service
						.submit(SynchronizedMethods::syncStaticCalculate));
		try {
			service.awaitTermination(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(1000, SynchronizedMethods.staticSum);
	}

	public void performSynchrinisedTask() {
		synchronized (this) {
			setCount(getCount() + 1);
		}
	}

	private static void setCount(int i) {

	}

	private static int getCount() {

		return 0;
	}

	@Test
	public void givenMultiThread_whenBlockSync() {
		ExecutorService service = Executors.newFixedThreadPool(3);
		SynchronizedMethods synchronizedBlocks = new SynchronizedMethods();

		IntStream.range(0, 1000).forEach(
				count -> service
						.submit(synchronizedBlocks::performSynchrinisedTask));
		try {
			service.awaitTermination(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(1000, SynchronizedMethods.getCount());
	}

	public static void performSynchrinisedTaskStatic() {
		synchronized (SynchronizedMethods.class) {
			setCount(getCount() + 1);
		}
	}

	@Test
	public void givenMultiThread_whenStaticSyncBlock() {

		ExecutorService service = Executors.newCachedThreadPool();

		IntStream.range(0, 1000)

		.forEach(count ->

		service.submit(SynchronizedMethods::performSynchrinisedTaskStatic));
		try {
			service.awaitTermination(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(1000,

		SynchronizedMethods.getStaticCount());

	}

	private static Object getStaticCount() {
		// TODO Auto-generated method stub
		return null;
	}
}
