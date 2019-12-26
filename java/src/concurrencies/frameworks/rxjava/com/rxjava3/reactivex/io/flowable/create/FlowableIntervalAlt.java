package com.rxjava3.reactivex.io.flowable.create;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableIntervalAlt {

    private static final Logger log = LoggerFactory.getLogger(FlowableIntervalAlt.class);

    public static void main(String[] args) {

        Flowable<Long> seconds =
                Flowable.interval(1, TimeUnit.SECONDS)
                        .doOnNext(n -> log.info("Received {}", n))
                        .doOnSubscribe(s -> log.info("Subscribed {}", s));

        seconds.subscribe(t ->
                log.info("One {}", t));

        Utils.hold(5000);

        seconds.subscribe(t ->
                log.info("Two {}", t));

        Utils.hold(6000);

    }

}
