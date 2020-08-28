package concurrency.java.wrappers.ext;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExecutorServiceWrapperTest {
	private static final ExecutorServiceUtils.ExecutorServiceWrapper wrapper = ExecutorServiceUtils
			.newFixedThreadPool("ExecutorServiceWrapperTest");
	public static Logger logger = Logger
			.getLogger(ExecutorServiceWrapperTest.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		Future<Boolean> result = wrapper.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				// TODO Auto-generated method stub
				return true;
			}
		});
		try {
			logger.debug("result:" + result.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
