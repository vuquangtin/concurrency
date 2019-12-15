package com.rxjava3.examples;


public class Observable<T> {
    final OnSubscribe<T> onSubscribe;
    public Observable(OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }
    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<T>(onSubscribe);
    }

    public Subscriber subscribe(Subscriber<T> subscriber) {
        // 是 Subscriber 增加的方法 事件还未发送之前执行，用于数据清零或重置
        // 如果对线程有要求，要求在主线程上就只能用doOnSubscribe
        subscriber.onStart();
        // 按照设置的执行计划开始执行
        onSubscribe.call(subscriber);
        return subscriber;
    }

    // T -> R
    public <R> Observable<R> map(Transformer<? super T, ? extends R> transformer) {
        OnSubscribe<R> onSubscribe = new OnSubscribe<R>() {
            @Override
            public void call(Subscriber<? super R> subscriber) {

                Subscriber<T> subscriber1 = new Subscriber<T>() {

                    @Override
                    public void onNext(T t) {
                        subscriber.onNext(transformer.call(t));
                    }

                    @Override
                    public void onStart() {
                        System.out.println("第二个Sub启动");
                    }
                };
                Observable.this.subscribe(subscriber1);
            }
        };
        return Observable.create(onSubscribe);
    }


    // T -> R
    public <R> Observable<R> map2(Transformer<? super T, ? extends R> transformer) {
        return create(new MapOnSubscribe<T, R>(this, transformer));
    }


    public <R> Observable<R> lift(Operator<? super T, ? extends R> operator) {
        return Observable.create(new OnSubscribe<R>() {
            @Override
            public void call(Subscriber<? super R> subscriber) {
                Subscriber newSubscriber = operator.call(subscriber);
                newSubscriber.onStart();
                // 最原始的Onsubscribe 闭包原理
                onSubscribe.call(newSubscriber);
            }
        });
    }

    public Observable<T> subscribeOn(Scheduler scheduler) {
        return Observable.create(new OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onStart();
                // 将事件的生产切换到新的线程。
                scheduler.createWorker().schedule(new Runnable() {
                    @Override
                    public void run() {
                        Observable.this.onSubscribe.call(subscriber);
                    }
                });
            }
        });
    }

    public Observable<T> observeOn(Scheduler scheduler) {
        return Observable.create(new OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onStart();
                Scheduler.Worker worker = scheduler.createWorker();
                Observable.this.onSubscribe.call(new Subscriber<T>() {
                    @Override
                    public void onNext(T t) {
                        worker.schedule(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onNext(t);
                            }
                        });
                    }
                });
            }
        });
    }

    public interface OnSubscribe<T> {

        void call(Subscriber<? super T> subscriber);
    }
}