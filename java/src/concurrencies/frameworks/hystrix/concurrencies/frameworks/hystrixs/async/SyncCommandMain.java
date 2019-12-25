package concurrencies.frameworks.hystrixs.async;

import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import concurrencies.frameworks.hystrixs.core.AsyncCommand;
import concurrencies.frameworks.hystrixs.core.SyncCommand;
import concurrencies.utilities.Log4jUtils;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class SyncCommandMain {
	static Logger logger = Logger.getLogger(Main.class.getName());

	@HystrixProperty(name = "hystrix.command.default.execution.timeout.enabled", value = "false")
	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		Boolean result = new SyncCommand<>("Main", "add-posts", () -> runBoolean()).execute();
		if (result == Boolean.TRUE) {
			logger.debug("TRUE");
		}
		Product resultProduct;
		try {
			resultProduct = new AsyncCommand<>("Main", "add-posts", () -> runProduct()).observe().toBlocking()
					.toFuture().get();
			if (resultProduct != null) {
				logger.debug(resultProduct.getName());
				logger.debug(resultProduct.getDecs());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Subscription resultProduct2 = new AsyncCommand<>("Main", "add-posts",
		// () -> runProduct()).observe().subscribe();

		//TimeUtils.sleepInMinutes(1, "");
	}

	public static boolean runBoolean() {
		return true;
	}

	public static Product runProduct() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Product("AAAAAAAA", "BBBBBbbbbbbbbBBBB");
	}
}