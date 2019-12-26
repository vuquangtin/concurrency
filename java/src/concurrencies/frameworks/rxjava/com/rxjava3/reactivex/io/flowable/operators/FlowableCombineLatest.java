package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableCombineLatest {

    private static final Logger log = LoggerFactory.getLogger(FlowableCombineLatest.class);

    public static void main(String[] args) {

        Flowable<String> slow =
                Flowable.interval(18, TimeUnit.MILLISECONDS).map(i -> "Slow".concat(i.toString()));

        Flowable<String> fast =
                Flowable.interval(10, TimeUnit.MILLISECONDS).map(i -> "Fast".concat(i.toString()));

        Flowable<String> combine = Flowable.combineLatest(slow, fast,
                (s, f) -> s.concat(" : ").concat(f));

        // If an event occurs, it will combine with latest from the other flow
        combine.subscribe(sf -> log.info("{}", sf),
                ex -> log.error("Error : {}", ex.getMessage()));

        Utils.hold(2000);

    }

}
