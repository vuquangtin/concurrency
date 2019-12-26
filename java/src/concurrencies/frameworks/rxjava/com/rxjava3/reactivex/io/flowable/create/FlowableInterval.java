package com.rxjava3.reactivex.io.flowable.create;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableInterval {

    private static final Logger log = LoggerFactory.getLogger(FlowableInterval.class);

    public static void main(String[] args) {

        Flowable<Long> seconds =
                Flowable.interval(1, TimeUnit.SECONDS);

        seconds.subscribe(t ->
                log.info("{}", t));

        Utils.hold(20000);
    }

}
