package com.rxjava3.reactivex.io.flowable.create;

import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableMapSimple {

    private static final Logger log = LoggerFactory.getLogger(FlowableMapSimple.class);

    public static void main(String[] args) {

        Flowable<String> strings =
                Flowable.just("Alpha", "Beta", "Gamma", "Delta",
                        "Epsilon");

        strings.map(s -> s.length()).subscribe(l -> log.info("{}", l));

    }

}
