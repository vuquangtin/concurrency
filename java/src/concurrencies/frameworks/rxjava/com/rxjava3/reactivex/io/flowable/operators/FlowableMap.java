package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableMap {

    private static final Logger log = LoggerFactory.getLogger(FlowableMap.class);

    public static void main(String[] args) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");

        Flowable<LocalDate> flowable =
                Flowable.just("1/3/2019", "5/9/2019", "10/12/2019", "50/1/2019")
                        .map(s -> LocalDate.parse(s, dtf));

        flowable.subscribe(
                l -> log.info("{}", l),
                ex -> log.error("Error {}", ex.getMessage())
        );

    }

}
