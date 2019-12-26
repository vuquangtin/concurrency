package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableTake {

    private static final Logger log = LoggerFactory.getLogger(FlowableTake.class);

    public static void main(String[] args) {

        Flowable<String> flowable =
                Flowable.just("Alpha", "Beta", "Gamma", "Delta",
                        "Epsilon")
                .take(3);

        flowable.subscribe(log::info);

    }

}
