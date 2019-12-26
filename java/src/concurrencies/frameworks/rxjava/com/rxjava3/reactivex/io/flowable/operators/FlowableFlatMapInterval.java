package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableFlatMapInterval {

    private static final Logger log = LoggerFactory.getLogger(FlowableFlatMapInterval.class);

    public static void main(String[] args) {

        Flowable<Long> flowable = Flowable.range(1, 5)
                .filter(i -> i % 2 == 0)
                .flatMap(i -> Flowable.interval(i, TimeUnit.SECONDS));

        flowable.subscribe(
                l -> log.info("Even time intervals {}", l),
                ex -> log.error("Error {}", ex.getMessage())
        );

        Utils.hold(30000);

    }

}
