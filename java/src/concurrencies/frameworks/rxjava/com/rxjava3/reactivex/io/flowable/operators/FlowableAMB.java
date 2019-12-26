package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableAMB {

    private static final Logger log = LoggerFactory.getLogger(FlowableAMB.class);

    public static void main(String[] args) {

        // First flowable that emmit an event remains other one won't be consumed
        Flowable.ambArray(
                flowable(100, 18, "Slow"),
                flowable(200, 10, "Fast")
        ).subscribe(sf -> log.info("{}", sf),
                ex -> log.error("Error : {}", ex.getMessage()));
        Utils.hold(2000);

    }

    private static Flowable<String> flowable(int initDelay, int interval, String name) {
        return Observable.interval(initDelay, interval, TimeUnit.MILLISECONDS)
                .map(x -> name.concat(String.valueOf(x)))
                .doOnSubscribe(s -> log.info("Subscribe to {}", name))
                .doOnDispose(() -> log.info("Dispose {}", name))
                .toFlowable(BackpressureStrategy.BUFFER);

    }

}
