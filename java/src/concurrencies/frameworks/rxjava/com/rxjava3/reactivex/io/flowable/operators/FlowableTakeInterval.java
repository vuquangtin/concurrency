package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableTakeInterval {

    private static final Logger log = LoggerFactory.getLogger(FlowableTakeInterval.class);

    public static void main(String[] args) {

        Flowable<Long> flowable =
                Flowable.interval(500, TimeUnit.MILLISECONDS)
                    .take(2, TimeUnit.SECONDS);

        flowable.subscribe(l -> log.info("{}", l));

        Utils.hold(6000);

    }

}
