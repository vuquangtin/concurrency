package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableDelay {

    private static final Logger log = LoggerFactory.getLogger(FlowableDelay.class);

    public static void main(String[] args) {

        log.info("Delay 1 second");
        Flowable<Integer> flowable = Flowable.range(1, 10)
                .delay(1, TimeUnit.SECONDS);

        log.info("Starts sending events");
        flowable.subscribe(
                l -> log.info("Index {}", l),
                ex -> log.error("Error {}", ex.getMessage())
        );

        Utils.hold(2000);

    }

}
