package concurrencies.frameworks.hystrixs.async;

import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class UserHystrixObservableCommandMain {
	static Logger logger = Logger.getLogger(UserHystrixObservableCommandMain.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		//try {
			//User user = new UserHystrixObservableCommand().observe().toBlocking().toFuture().get();
			//logger.debug(user.getUsername());
			new UserHystrixObservableCommand().observe().subscribe((u) -> {
				System.out.println(u.getUsername());
			});
			//logger.debug(user.getUsername());
		//} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}

	}

}
