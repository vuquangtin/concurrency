package concurrencies.frameworks.hystrixs.async;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class GreetingCommand extends HystrixCommand<String> {

    private final String name;

    public GreetingCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("GreetingGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        if (this.name == null){
            throw new IllegalArgumentException("name sould not be 'null'.");
        }
        return "Hello " + this.name + "!";
    }

    @Override
    protected String getFallback() {
        return "Hello Guest!";
    }
}