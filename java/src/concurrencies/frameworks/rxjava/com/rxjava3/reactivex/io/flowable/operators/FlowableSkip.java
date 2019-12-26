package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableSkip {

    private static final Logger log = LoggerFactory.getLogger(FlowableSkip.class);

    public static void main(String[] args) {

        Flowable<Integer> flowable =
                Flowable.range(1, 100)
                        .skip(90);

        flowable.subscribe(i -> log.info("{}", i));

    }

}
