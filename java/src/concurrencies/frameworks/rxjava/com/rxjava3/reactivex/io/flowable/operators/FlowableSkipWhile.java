package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableSkipWhile {

    private static final Logger log = LoggerFactory.getLogger(FlowableSkipWhile.class);

    public static void main(String[] args) {

        Flowable<Integer> flowable =
                Flowable.range(1, 100)
                        .skipWhile(i -> i <= 95);

        flowable.subscribe(i -> log.info("{}", i));

    }

}
