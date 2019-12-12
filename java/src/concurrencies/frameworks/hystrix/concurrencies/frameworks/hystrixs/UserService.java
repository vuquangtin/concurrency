package concurrencies.frameworks.hystrixs;

import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

/**
 * 用@HystrixCommand的方式来实现
 */
@Service
public class UserService {
	/**
	 * 同步的方式。 fallbackMethod定义降级
	 */
	@HystrixCommand(fallbackMethod = "helloFallback")
	public String getUserId(String name) {
		int i = 1 / 0; // 此处抛异常，测试服务降级
		return "你好:" + name;
	}

	public String helloFallback(String name) {
		return "error";
	}

	// 异步的执行
	@HystrixCommand(fallbackMethod = "getUserNameError")
	public Future<String> getUserName(final Long id) {
		return new AsyncResult<String>() {
			@Override
			public String invoke() {
				int i = 1 / 0;// 此处抛异常,测试服务降级
				return "小明:" + id;
			}
		};
	}

	public String getUserNameError(Long id) {
		return "faile";
	}
}
