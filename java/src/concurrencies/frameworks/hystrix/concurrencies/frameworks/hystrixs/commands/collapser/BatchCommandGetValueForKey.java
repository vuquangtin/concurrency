package concurrencies.frameworks.hystrixs.commands.collapser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.netflix.hystrix.HystrixCollapser.CollapsedRequest;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class BatchCommandGetValueForKey extends HystrixCommand<List<String>> {

	private final Collection<CollapsedRequest<String, Integer>> requests;

	protected BatchCommandGetValueForKey(Collection<CollapsedRequest<String, Integer>> requests) {

		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWroldGroup"))
				.andCommandKey(HystrixCommandKey.Factory.asKey(BatchCommandGetValueForKey.class.getName()))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CommandCollapserGetValueForKeyPool")));
		this.requests = requests;
	}

	@Override
	protected List<String> run() throws Exception {
		List<String> response = new ArrayList<String>();
		for(CollapsedRequest<String, Integer> request: requests){
			response.add("ValueForKey : "+request.getArgument());
		}
		return response;
	}

}
