package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableZipAsyncError {

    private static final Logger log = LoggerFactory.getLogger(FlowableZipAsyncError.class);

    public static void main(String[] args) {

        Flowable<String> slow =
                Flowable.interval(18, TimeUnit.MILLISECONDS).map(i -> "Slow".concat(i.toString()));

        Flowable<String> fast =
                Flowable.interval(10, TimeUnit.MILLISECONDS).map(i -> "Fast".concat(i.toString()));

        Flowable<String> zip = Flowable.zip(slow, fast,
                (s, f) -> s.concat(" : ").concat(f));

        // A lack of requests will occurs because slow stream
        zip.subscribe(sf -> log.info("{}", sf),
                ex -> log.error("Error : {}", ex.getMessage()));

        Utils.hold(2000);

    }

}
