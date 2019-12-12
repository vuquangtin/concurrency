package concurrencies.frameworks.hystrixs.commands;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

public class TestCommandHelloWorldCache {

	public static void main(String[] args) {

		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		try {
			CommandHelloWorldCache cache1 = new CommandHelloWorldCache("Harish");
			System.out.println(" Name: " + cache1.execute());
			System.out.println(" From Cache " + cache1.isResponseFromCache());

			CommandHelloWorldCache cache2 = new CommandHelloWorldCache("Harish");
			System.out.println(" Name: " + cache2.execute());
			System.out.println(" From Cache " + cache2.isResponseFromCache());
			
			CommandHelloWorldCache cache3 = new CommandHelloWorldCache("Shree");
			System.out.println(" Name: " + cache3.execute());
			System.out.println(" From Cache " + cache3.isResponseFromCache());
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			context.shutdown();
		}
	}

}
