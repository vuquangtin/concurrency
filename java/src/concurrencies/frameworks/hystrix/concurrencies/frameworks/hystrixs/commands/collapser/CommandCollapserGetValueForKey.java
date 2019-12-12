package concurrencies.frameworks.hystrixs.commands.collapser;

import java.util.Collection;
import java.util.List;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;

public class CommandCollapserGetValueForKey extends HystrixCollapser<List<String>, String, Integer> {

	private Integer key;

	public CommandCollapserGetValueForKey(Integer key) {
		super();
		this.key = key;
	}

	@Override
	protected HystrixCommand<List<String>> createCommand(
			Collection<com.netflix.hystrix.HystrixCollapser.CollapsedRequest<String, Integer>> request) {
		return new BatchCommandGetValueForKey(request);
	}

	@Override
	public Integer getRequestArgument() {
		return key;
	}

	@Override
	protected void mapResponseToRequests(List<String> batchResponse,
			Collection<com.netflix.hystrix.HystrixCollapser.CollapsedRequest<String, Integer>> requests) {
		int count = 0;
		for (CollapsedRequest<String, Integer> request : requests) {
			request.setResponse(batchResponse.get(count++));
		}
	}

}
