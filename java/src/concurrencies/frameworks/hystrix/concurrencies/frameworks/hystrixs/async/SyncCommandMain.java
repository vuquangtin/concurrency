package concurrencies.frameworks.hystrixs.async;

import org.apache.log4j.Logger;

import rx.Observable;
import rx.Subscription;
import concurrencies.frameworks.hystrixs.core.AsyncCommand;
import concurrencies.frameworks.hystrixs.core.SyncCommand;
import concurrencies.utilities.Log4jUtils;
import concurrencies.utilities.TimeUtils;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SyncCommandMain {
	static Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
//		Boolean result = new SyncCommand<>("Main", "add-posts",
//				() -> runBoolean()).execute();
//		if (result == Boolean.TRUE) {
//			logger.debug("TRUE");
//		}
		Observable<Product> resultProduct =new AsyncCommand<>("Main", "add-posts",
				() -> runProduct()).observe();
		Subscription resultProduct2 = new AsyncCommand<>("Main", "add-posts",
				() -> runProduct()).observe().subscribe();
		if (resultProduct != null) {
			resultProduct.subscribe(products -> {
				logger.debug(products);
//		        for (Product p : products) {
//		        	logger.debug(p.getName());
//		        }
		    });
			//logger.debug(resultProduct.);
		}
		TimeUtils.sleepInMinutes(1, "");
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