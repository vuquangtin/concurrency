package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableZipSynchronous {

    private static final Logger log = LoggerFactory.getLogger(FlowableZipSynchronous.class);

    public static void main(String[] args) {

        Flowable<Long> intervalA =
                Flowable.interval(1, TimeUnit.SECONDS);

        Flowable<Long> intervalB =
                Flowable.interval(1, TimeUnit.SECONDS);

        Flowable<Long> zip = Flowable.zip(intervalA.timestamp(), intervalB.timestamp(),
                (t, w) -> t.time() - w.time());

        // Events in both flows are synchronized
        zip.subscribe(tt -> log.info("Time between {}", tt));

        Utils.hold(6000);

    }

}
