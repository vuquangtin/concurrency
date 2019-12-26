package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

public class FlowableFlatMapIterableCollect {

    private static final Logger log = LoggerFactory.getLogger(FlowableFlatMapIterableCollect.class);

    public static void main(String[] args) {

        List<String> words = ImmutableList.of("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        List<Integer> wordsLength = new ArrayList<>();

        Flowable. fromArray(words)
                .flatMapIterable(x -> x)
                .collect(() -> wordsLength, (l, p) -> l.add(p.length()))
                .subscribe(
                        l -> log.info("{}", l)
                );

    }

}
