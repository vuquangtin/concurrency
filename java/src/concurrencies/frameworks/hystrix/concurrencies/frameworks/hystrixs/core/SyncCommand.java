package concurrencies.frameworks.hystrixs.core;

import java.util.concurrent.Callable;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.exception.HystrixBadRequestException;

/**
 * @author: kumin on 05/04/2019
 **/

public class SyncCommand<T> extends HystrixCommand<T> {

    private Callable<T> callable;
    private T fallback;

    /**
     * I am lazy mam, so I don't want to pass properties to here
     **/
    protected SyncCommand(String group, String name, Callable<T> callable, T fallback) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(group))
                .andCommandKey(HystrixCommandKey.Factory.asKey(name))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(5000)
                        .withCircuitBreakerEnabled(true)
                        .withCircuitBreakerRequestVolumeThreshold(10)
                        .withCircuitBreakerSleepWindowInMilliseconds(10000)
                ));
        this.callable = callable;
        this.fallback = fallback;
    }

    public SyncCommand(String group, String name, Callable<T> callable) {
        this(group, name, callable, null);
    }

    @Override
    protected T run() throws Exception {
        try {
            return callable.call();
        } catch (Exception e) {
            if (e instanceof ClientException) {
                throw new HystrixBadRequestException(e.getMessage());
            } else throw e;
        }
    }
}
