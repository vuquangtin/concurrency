package synchronizers.exchanger.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.stream.IntStream;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExchangerProducer implements Runnable {

	private Exchanger<List<Integer>> exchanger;
	private int bufferLimit;
	private static Random RANDOM = new Random();
	private int currentValue;
	private List<Integer> buffer = new ArrayList<>();

	public ExchangerProducer(Exchanger<List<Integer>> exchanger, int bufferLimit) {
		this.exchanger = exchanger;
		this.bufferLimit = bufferLimit;
	}

	@Override
	public void run() {
		while (true) {
			try {
				int sleepTime = RANDOM.nextInt(10) + 1;
				Thread.sleep(sleepTime * 1000);

				this.fillBufferWithNumbers();
				System.out.println(String.format("Producer has produced: %s",
						buffer));

				System.out.println("Exchange produced data");
				buffer = exchanger.exchange(buffer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void fillBufferWithNumbers() {
		IntStream.rangeClosed(1, bufferLimit).iterator()
				.forEachRemaining((Integer integer) -> {
					buffer.add(++currentValue);
				});
	}
}