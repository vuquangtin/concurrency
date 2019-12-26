package com.rxjava3.reactivex.io.flowable.subscribers;

import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableTwoSubscribers {

    private static final Logger log = LoggerFactory.getLogger(FlowableTwoSubscribers.class);

    public static void main(String[] args) {

        Flowable<String> flowable = Flowable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        // First subscriber
        flowable.subscribe(
                        length -> log.info("First {}", length),
                        Throwable::printStackTrace,
                        () -> log.info("First done!")
                );

        // Second subscriber
        flowable.subscribe(
                length -> log.info("Second {}", length),
                Throwable::printStackTrace,
                () -> log.info("Second done!")
        );

    }

}
