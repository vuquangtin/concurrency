package com.rxjava3.reactivex.io.flowable.subscribers;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableSubscriberConnectable {

    private static final Logger log = LoggerFactory.getLogger(FlowableSubscriberConnectable.class);

    public static void main(String[] args) {

        ConnectableFlowable<String> flowable = Flowable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon").publish();

        flowable.subscribe(s -> log.info("First {}", s));

        flowable.map(String::length).subscribe(l -> log.info("Second {}", l));

        flowable.connect();

    }

}
