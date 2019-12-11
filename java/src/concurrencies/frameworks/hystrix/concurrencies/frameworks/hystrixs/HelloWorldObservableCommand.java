package concurrencies.frameworks.hystrixs;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

import concurrencies.utilities.Log4jUtils;
import rx.Observable;
import rx.Subscription;

public class HelloWorldObservableCommand extends
		HystrixObservableCommand<String> {
	static Logger logger = Logger.getLogger(CommandHelloWorld.class.getName());
	private String name;

	public HelloWorldObservableCommand(String name) {

		super(HystrixCommandGroupKey.Factory.asKey("default"));

		this.name = name;

	}

	@Override
	protected Observable<String> resumeWithFallback() {

		return Observable.just("Returning a Fallback");

	}

	@Override
	protected Observable<String> construct() {

		return Observable.just("Hello" + this.name);

	}

	public static void main(String[] args) throws InterruptedException {
		logger = Log4jUtils.initLog4j();
		HelloWorldObservableCommand helloWorldCommand = new HelloWorldObservableCommand(
				"World");

		logger.info("Completed executing HelloWorld Command");

		Observable<String> obs = helloWorldCommand.observe();
		logger.info("obs:" + obs);
		CountDownLatch l = new CountDownLatch(1);

		Subscription sub = obs.subscribe(System.out::println,
				t -> l.countDown(), () -> l.countDown());

		l.await();
		
	
	}
}