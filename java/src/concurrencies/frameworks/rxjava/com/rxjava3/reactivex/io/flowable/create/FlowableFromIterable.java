package com.rxjava3.reactivex.io.flowable.create;

import io.reactivex.rxjava3.core.Flowable;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableFromIterable {

    private static final Logger log = LoggerFactory.getLogger(FlowableFromIterable.class);

    public static void main(String[] args) {

        List<String> items =
                Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        Flowable<String> flowable = Flowable.fromIterable(items);

        flowable.subscribe(log::info);

    }

}
