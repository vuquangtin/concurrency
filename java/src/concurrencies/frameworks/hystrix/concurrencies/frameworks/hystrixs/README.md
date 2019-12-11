# How To Use
## Hello World!

The following is a basic “Hello World” implementation of a HystrixCommand:

```java
package concurrencies.frameworks.hystrixs;

import org.apache.log4j.Logger;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import concurrencies.utilities.Log4jUtils;

public class CommandHelloWorld extends HystrixCommand<String> {
	static Logger logger = Logger.getLogger(CommandHelloWorld.class.getName());
	private final String name;

	public CommandHelloWorld(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}

	@Override
	protected String run() {
		// a real example would do work like a network call here
		return "Hello " + name + "!";
	}

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		String s = new CommandHelloWorld("World").execute();
		logger.debug("s:" + s);

	}
}
```

## HystrixObservableCommand Equivalent

An equivalent Hello World solution that uses a HystrixObservableCommand instead of a HystrixCommand would involve overriding the construct method as follows:

```java
package concurrencies.frameworks.hystrixs;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

public class HystrixObservableCommandHelloWorld extends
		HystrixObservableCommand<String> {

	private final String name;

	public HystrixObservableCommandHelloWorld(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}

	@Override
	protected Observable<String> construct() {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						// a real example would do work like a network call here
						observer.onNext("Hello");
						observer.onNext(name + "!");
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		}).subscribeOn(Schedulers.io());
	}
}
```

