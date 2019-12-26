package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableTimerFlatMapAlt {

    private static final Logger log = LoggerFactory.getLogger(FlowableTimerFlatMapAlt.class);

    public static void main(String[] args) {

         Flowable<String> flowable =
                Flowable.just("Alpha", "Beta", "Gamma", "Delta","Epsilon")
                        .flatMap(word -> Flowable.timer(word.length(), TimeUnit.SECONDS).map(x -> word));

        flowable.subscribe(
                l -> log.info("Timer for word {}", l),
                ex -> log.error("Error {}", ex.getMessage())
        );

        Utils.hold(10000);

    }

}
