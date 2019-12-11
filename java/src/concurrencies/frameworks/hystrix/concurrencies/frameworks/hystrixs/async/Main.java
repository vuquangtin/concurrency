package concurrencies.frameworks.hystrixs.async;

import org.apache.log4j.Logger;

import concurrencies.frameworks.hystrixs.core.SyncCommand;
import concurrencies.utilities.Log4jUtils;

public class Main {
	static Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		Boolean result = new SyncCommand<>("Main", "add-posts",
				() -> runBoolean()).execute();
		if (result == Boolean.TRUE) {
			logger.debug("TRUE");
		}
		Product resultProduct = new SyncCommand<>("Main", "add-posts",
				() -> runProduct()).execute();
		if (resultProduct != null) {
			logger.debug(resultProduct);
		}
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
