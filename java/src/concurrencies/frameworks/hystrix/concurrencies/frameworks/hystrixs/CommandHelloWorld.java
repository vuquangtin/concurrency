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