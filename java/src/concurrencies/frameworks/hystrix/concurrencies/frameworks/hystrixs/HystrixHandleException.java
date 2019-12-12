package concurrencies.frameworks.hystrixs;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

@Service
public class HystrixHandleException {
	/**
	 * 当设置ignoreExceptions参数时， 抛出对应的异常就不会触发降级(也就是不会调用failMethod()方法).
	 */
	@HystrixCommand(ignoreExceptions = { NullPointerException.class,
			ArithmeticException.class }, fallbackMethod = "failMethod")
	public String getUserName(Long id) {
		Long re = id / 0; // 会抛ArithmeticException
		String param = null;
		param.trim(); // 此处会抛NullPointException
		return "张三";
	}

	private String failMethod(Long id, Throwable e) {
		return e.getMessage();
	}

}