package concurrencies.frameworks.hystrixs;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class HystrixObservableCommandHelloWord extends HystrixObservableCommand<String> {
    private String  name;

    protected HystrixObservableCommandHelloWord(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"));
        this.name = name;
    }

    @Override
    protected Observable<String> construct() {
        //return "Hello "+ name + " !";
        return null;
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        return null;
    }

    public static void main(String[] args){
        //new HystrixObservableCommandHelloWord("李四").observe();
        new HystrixObservableCommandHelloWord("李四").toObservable();
    }

}