package com.rxjava3.reactivex.io.flowable.threadpools;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;

public class StringsHandler {

    private ConnectableFlowable<String> processor;

    public StringsHandler(final StringsGenerator generator) {
        Flowable<String> flowable = Flowable.create(emitter ->
        {
            StringsListener listener = new StringsListener() {
                @Override
                public void onString(String event) {
                    emitter.onNext(event);
                }

                @Override
                public void onError(Throwable e) {
                    emitter.onError(e);
                }
            };

            generator.register(listener);

        }, BackpressureStrategy.BUFFER);
        processor = flowable.publish();
    }

    public ConnectableFlowable<String> processor() {
        return processor;
    }

    public void start() {
        processor.connect();
    }

}
