package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableWithLatestFrom {

    private static final Logger log = LoggerFactory.getLogger(FlowableWithLatestFrom.class);

    public static void main(String[] args) {

        Flowable<String> slow =
                Flowable.interval(18, TimeUnit.MILLISECONDS).map(i -> "Slow".concat(i.toString()));

        Flowable<String> fast =
                Flowable.interval(10, TimeUnit.MILLISECONDS).map(i -> "Fast".concat(i.toString()));

        Flowable<String> combine = slow.withLatestFrom(fast, (s, f) -> s.concat(" : ").concat(f));

        // All slow flowable events will be conserve while
        // fastest events that won't have a correspondence will be dropped
        combine.subscribe(ab -> log.info("{}", ab),
                ex -> log.error("Error : {}", ex.getMessage()));

        Utils.hold(2000);

    }

}
