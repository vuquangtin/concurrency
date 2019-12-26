package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableDistinct {

    private static final Logger log = LoggerFactory.getLogger(FlowableDistinct.class);

    public static void main(String[] args) {

        Flowable<Integer> flowable =
                Flowable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                        .map(String::length)
                        .distinct();

        flowable.subscribe(l -> log.info("{}", l));

    }

}
