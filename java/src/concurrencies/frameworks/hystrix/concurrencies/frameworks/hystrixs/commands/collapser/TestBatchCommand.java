package concurrencies.frameworks.hystrixs.commands.collapser;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

public class TestBatchCommand {

	@SuppressWarnings("deprecation")
	public static void main(String... strings) {

		HystrixRequestContext context = HystrixRequestContext
				.initializeContext();
		try {
			CommandCollapserGetValueForKey collapse1 = new CommandCollapserGetValueForKey(
					1);
			String value = collapse1.execute();
			System.out.println(value);

			HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest()
					.getExecutedCommands().toArray(new HystrixCommand<?>[1])[0];
			System.out.println(" CommandKey Name : "
					+ command.getCommandKey().name());
			System.out.println(""
					+ command.getExecutionEvents().contains(
							HystrixEventType.COLLAPSED));
			System.out.println(""
					+ command.getExecutionEvents().contains(
							HystrixEventType.SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.shutdown();
		}

	}
}
