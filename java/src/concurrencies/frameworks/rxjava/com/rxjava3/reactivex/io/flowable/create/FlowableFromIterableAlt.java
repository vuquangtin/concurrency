package com.rxjava3.reactivex.io.flowable.create;

import io.reactivex.rxjava3.core.Flowable;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableFromIterableAlt {

    private static final Logger log = LoggerFactory.getLogger(FlowableFromIterableAlt.class);

    public static void main(String[] args) {

        Flowable<Integer> flowable = Flowable.fromIterable(new EvenNumberIterable());

        flowable.subscribe(n -> log.info("{}", n));

    }

    static class EvenNumberIterable implements Iterable<Integer> {

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                private Integer currentNumber = Integer.valueOf(0);

                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public Integer next() {
                    Utils.hold(500);
                    return currentNumber += 2;
                }
            };
        }

    }

}
