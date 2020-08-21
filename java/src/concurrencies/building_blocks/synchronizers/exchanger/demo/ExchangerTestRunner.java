package synchronizers.exchanger.demo;

import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExchangerTestRunner {

	public static void main(String[] args) {

		try {
			Exchanger<List<Integer>> exchanger = new Exchanger<>();

			// The producer will create 10 integers on andy request
			ExchangerProducer producer = new ExchangerProducer(exchanger, 5);
			ExchangerConsumer consumer = new ExchangerConsumer(exchanger);

			Executor executor = Executors.newCachedThreadPool();

			executor.execute(producer);
			executor.execute(consumer);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
