package com.rxjava3.reactivex.io.operators.all;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

import com.rxjava3.utils.SubscriberFactory;
/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ErrorHanding {
    public static void main(String[] args) throws InterruptedException {
//        onErrorReturn();
//        onErrorResumeNext();
//        onExceptionResumeNext();
//        retry();
        retryWhen();

        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * onErrorReturn操作符,是在Observable发生错误或异常的时候（即将回调oError方法时），
     * 拦截错误并执行指定的逻辑，返回一个跟源Observable相同类型的结果，最后回调订阅者的onComplete方法
     */
    private static void onErrorReturn() {
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                for (int i = 0; i < 10; i++) {
                    if (i == 5) {
                        subscriber.onError(new Throwable("发生错误：5"));
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });

        observable.onErrorReturn(new Func1<Throwable, Integer>() {
            @Override
            public Integer call(Throwable throwable) {
                return 500000;
            }
        }).subscribe(SubscriberFactory.getIntegerSubscriber("onErrorReturn()"));
    }

    /**
     * onErrorResumeNext操作符,和onErrorReturn类似
     * 不过onErrorReturn只能在错误或异常发生时只返回一个和源Observable相同类型的结果，
     * 而onErrorResumeNext操作符是在 错误或异常 发生时返回一个Observable，也就是说可以返回多个和源Observable相同类型的结果
     */
    private static void onErrorResumeNext() {
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                for (int i = 0; i < 10; i++) {
                    if (i == 5) {
                        subscriber.onError(new Error("error"));
                        //                        subscriber.onError(new Exception("exception"));
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });

        observable.onErrorResumeNext(new Func1<Throwable, Observable<? extends Integer>>() {
            @Override
            public Observable<? extends Integer> call(Throwable throwable) {
                if (throwable.getMessage().equals("exception")) {
                    return Observable.just(501, 502, 503);
                } else if (throwable.getMessage().equals("error")) {
                    return Observable.just(601, 602, 603);
                } else {
                    return Observable.just(101, 102, 103);
                }
            }
        }).subscribe(SubscriberFactory.getIntegerSubscriber("onErrorResumeNext()"));
    }

    /**
     * OnExceptionResumeNext-类似于OnErrorResumeNext,
     * 不同之处在于其会对onError抛出的数据类型做判断，
     * 如果是Exception，也会使用另外一个Observable代替原Observable继续发射数据，
     * 否则会将错误分发给Subscriber
     */
    private static void onExceptionResumeNext() {
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                for (int i = 0; i < 10; i++) {
                    if (i == 5) {
                        //                        subscriber.onError(new Error("error"));
                        subscriber.onError(new Exception("exception"));
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });

        observable.onExceptionResumeNext(Observable.just(101, 202, 303))
                .subscribe(SubscriberFactory.getIntegerSubscriber("onExceptionResumeNext()"));
    }

    /**
     * Retry操作符,发生错误的时候会重新进行订阅,而且可以重复多次，所以发射的数据可能会产生重复。
     * 如果重复指定次数还有错误的话就会将错误返回给观察者
     */
    private static void retry() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                for (int i = 0; i < 10; i++) {
                    if (i == 5) {
                        subscriber.onError(new Throwable("发生 error:5"));
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).retry(2).subscribe(SubscriberFactory.getIntegerSubscriber("retry()"));
    }

    /**
     * retryWhen操作符,是在源Observable出现错误或者异常时，通过回调第二个Observable来判断是否重新尝试执行源Observable的逻辑，
     * 如果第二个Observable没有错误或者异常出现，则就会重新尝试执行源Observable的逻辑，否则就会直接回调执行订阅者的onError方法
     */
    // TODO: 16/12/29  没搞懂示例
    private static void retryWhen() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                for (int i = 0; i < 10; i++) {
                    if (i == 5) {
                        subscriber.onError(new Throwable("发生 error:5"));
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.zipWith(Observable.range(1, 3), new Func2<Throwable, Integer, Integer>() {
                    @Override
                    public Integer call(Throwable throwable, Integer integer) {
                        return integer;
                    }
                }).flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        System.out.println("每" + integer + "s，执行一次");
                        return Observable.timer(integer, TimeUnit.SECONDS);
                    }
                });
            }
        }).subscribe(SubscriberFactory.getIntegerSubscriber("retryWhen()"));
    }


}