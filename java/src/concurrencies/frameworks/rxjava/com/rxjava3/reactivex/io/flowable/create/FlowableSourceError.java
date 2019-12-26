package com.rxjava3.reactivex.io.flowable.create;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableSourceError {

    private static final Logger log = LoggerFactory.getLogger(FlowableSourceError.class);

    public static void main(String[] args) {

        Flowable<String> flowable = Flowable.create(source -> {
            try {
                source.onNext("Alpha");
                source.onNext("Beta");
                source.onNext("Gamma");
                source.onNext("Delta");
                source.onNext("Epsilon");
                source.onComplete();
            } catch (Throwable e) {
                source.onError(e);
            }
        }, BackpressureStrategy.BUFFER);

        flowable.subscribe(log::info, Throwable::printStackTrace);

    }

}
