package com.rxjava3.reactivex.io.flowable.create;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableSource {

    private static final Logger log = LoggerFactory.getLogger(FlowableSource.class);

    public static void main(String[] args) {

        Flowable<String> flowable = Flowable.create(source -> {
            source.onNext("Alpha");
            source.onNext("Beta");
            source.onNext("Gamma");
            source.onNext("Delta");
            source.onNext("Epsilon");
            source.onComplete();
        }, BackpressureStrategy.BUFFER);

        flowable.subscribe(log::info);

    }

}
