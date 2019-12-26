package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableDistinctUntilChange {

    private static final Logger log = LoggerFactory.getLogger(FlowableDistinctUntilChange.class);

    public static void main(String[] args) {

        Flowable<Integer> flowable =
                Flowable.just(1, 1, 1, 2, 2, 3, 3, 2, 1, 1)
                        .distinctUntilChanged();

        flowable.subscribe(l -> log.info("{}", l));

    }

}
