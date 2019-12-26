package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableTakeWhile {

    private static final Logger log = LoggerFactory.getLogger(FlowableTakeWhile.class);

    public static void main(String[] args) {

        Flowable<Integer> flowable =
                Flowable.range(1, 100)
                        .takeWhile(i -> i < 5);

        flowable.subscribe(i -> log.info("{}", i));

    }

}
