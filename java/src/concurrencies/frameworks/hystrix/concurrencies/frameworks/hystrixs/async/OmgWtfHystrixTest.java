package concurrencies.frameworks.hystrixs.async;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;


import com.google.common.collect.ImmutableList;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

import lombok.Data;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class OmgWtfHystrixTest {

    @Test
    public void testVanillaExecute() {

        new VanillaHystrixCommand("One", 500).execute();
        new VanillaHystrixCommand("Two", 50).execute();
        new VanillaHystrixCommand("Three", 250).execute();
    }

    @Test
    public void testVanillaQueue() throws ExecutionException, InterruptedException {
        Future<List<String>> f1 = new VanillaHystrixCommand("One", 500).queue();
        Future<List<String>> f2 = new VanillaHystrixCommand("Two", 50).queue();
        Future<List<String>> f3 = new VanillaHystrixCommand("Three", 250).queue();

        f1.get(); f2.get(); f3.get();
    }

    @Test
    public void testVanillaToObservable() throws ExecutionException, InterruptedException {
        Observable<List<String>> o1 = new VanillaHystrixCommand("One", 500).toObservable();
        Observable<List<String>> o2 = new VanillaHystrixCommand("Two", 50).toObservable();
        Observable<List<String>> o3 = new VanillaHystrixCommand("Three", 250).toObservable();

        TestSubscriber<String> ts = new TestSubscriber<>();
        Observable.merge(o1, o2, o3).flatMap(Observable::from).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertReceivedOnNext(ImmutableList.of("Two", "Three", "One"));
    }

    @Test
    public void testFancyToObservable() throws ExecutionException, InterruptedException {
        /* note the creation of a lazy Observable, followed by explicit scheduler */
        Observable<String> o1 = new FancyHystrixCommand("One", 500).toObservable().subscribeOn(Schedulers.io());
        Observable<String> o2 = new FancyHystrixCommand("Two", 50).toObservable().subscribeOn(Schedulers.io());
        Observable<String> o3 = new FancyHystrixCommand("Three", 250).toObservable().subscribeOn(Schedulers.io());

        TestSubscriber<String> ts = new TestSubscriber<>();
        Observable.merge(o1, o2, o3).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertReceivedOnNext(ImmutableList.of("Two", "Three", "One"));
    }

    @Test
    public void testVanillaObserve() throws ExecutionException, InterruptedException {
        Observable<List<String>> o1 = new VanillaHystrixCommand("One", 500).observe();
        Observable<List<String>> o2 = new VanillaHystrixCommand("Two", 50).observe();
        Observable<List<String>> o3 = new VanillaHystrixCommand("Three", 250).observe();

        TestSubscriber<String> ts = new TestSubscriber<>();
        Observable.merge(o1, o2, o3).flatMap(Observable::from).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertReceivedOnNext(ImmutableList.of("Two", "Three", "One"));
    }

    @Data
    private static final class VanillaHystrixCommand extends HystrixCommand<List<String>> {

        private final String name;
        private final int delayMillis;

        public VanillaHystrixCommand(String name, int delayMillis) {
            super(HystrixCommandGroupKey.Factory.asKey(VanillaHystrixCommand.class.getSimpleName()));
            this.name = name;
            this.delayMillis = delayMillis;
        }

        @Override
        protected List<String> run() throws Exception {
            System.out.println("Begin " + name + " on thread " + Thread.currentThread().getName());
            TimeUnit.MILLISECONDS.sleep(delayMillis);
            System.out.println("End " + name);
            return ImmutableList.of(name);
        }
    }

    private static final class FancyHystrixCommand extends HystrixObservableCommand<String> {

        private final String name;
        private final int delayMillis;

        public FancyHystrixCommand(String name, int delayMillis) {
            super(HystrixCommandGroupKey.Factory.asKey(FancyHystrixCommand.class.getSimpleName()));
            this.name = name;
            this.delayMillis = delayMillis;
        }

        @Override
        protected Observable<String> construct() {
            return Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> observer) {
                    if (observer.isUnsubscribed()) {
                        return;
                    }
                    System.out.println("Begin " + name + " on thread " + Thread.currentThread().getName());
                    try {
                        TimeUnit.MILLISECONDS.sleep(delayMillis);
                    } catch (InterruptedException e) {
                        observer.onError(e);
                    }
                    System.out.println("End " + name);
                    observer.onNext(name);
                    observer.onCompleted();
                }
            });
        }
    }

}


