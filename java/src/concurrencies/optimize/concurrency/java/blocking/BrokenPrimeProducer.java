package concurrency.java.blocking;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
@Slf4j
public class BrokenPrimeProducer extends Thread {
	private final BlockingQueue<BigInteger> queue;
	private volatile boolean cancelled = false;
	private volatile boolean needMorePrimes = true;
	static Logger logger = Logger
			.getLogger(BrokenPrimeProducer.class.getName());

	BrokenPrimeProducer(BlockingQueue queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			BigInteger p = BigInteger.ONE;
			while (!cancelled) {
				queue.put(p = p.nextProbablePrime());
			}
		} catch (InterruptedException e) {
			logger.info("异常", e);
		}
	}

	public void cancel() {
		cancelled = true;
	}

	public void setNeedMorePrimes(boolean flag) {
		logger.info("修改needMorePrimes成：{}" + flag);
		needMorePrimes = flag;
	}

	public void consumePrime(BrokenPrimeProducer brokenPrimeProducer) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.submit(brokenPrimeProducer);
		brokenPrimeProducer.start();
		executorService.submit(new Runnable() {
			@Override
			@SneakyThrows
			public void run() {
				try {
					SECONDS.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				brokenPrimeProducer.setNeedMorePrimes(false);
			}
		});
		executorService.shutdown();
		try {
			while (needMorePrimes()) {
				consume(brokenPrimeProducer.queue.take());
			}
		} catch (Exception e) {
			logger.error("客户端异常", e);
		} finally {
			brokenPrimeProducer.cancel();
		}
	}

	public boolean needMorePrimes() {
		/*
		 * Random random = new Random(); int i = random.nextInt(10000); return i
		 * % 2 == 0;
		 */
		return needMorePrimes;
	}

	public static void consume(BigInteger data) {
		logger.info("拿取数据：" + data);
	}

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<BigInteger> queue = new LinkedBlockingQueue<BigInteger>();
		BrokenPrimeProducer brokenPrimeProducer = new BrokenPrimeProducer(queue);
		brokenPrimeProducer.consumePrime(brokenPrimeProducer);
	}
}
