package com.rxjava3.reactivex.io.flowable.subscribers;

import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableSubscriberSimple {

    private static final Logger log = LoggerFactory.getLogger(FlowableSubscriberSimple.class);

    public static void main(String[] args) {

        Flowable<String> flowable = Flowable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        flowable.map(String::length)
                .filter(i -> i >= 5)
                .subscribe(
                        length -> log.info("{}", length),
                        Throwable::printStackTrace,
                        () -> log.info("Done!")
                );

    }

}
