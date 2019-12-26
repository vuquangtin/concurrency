package com.rxjava3.reactivex.io.flowable.threadpools;

import io.reactivex.rxjava3.disposables.Disposable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        String[] strings = {"IBM", "NMR", "BAC", "AAA", "MFT"};
        StringsGenerator generator = new StringsGenerator(strings);

        StringsHandler handler = new StringsHandler(generator);

        // Subscriber One
        Disposable one = handler.processor().subscribe(str ->
                log.info("Subscriber One - string {}", str)
        );

        // Subscriber Two
        Disposable two = handler.processor().take(40).subscribe(str ->
                log.info("Subscriber Two - string {}", str)
        );

        // Start generator
        handler.start();

        Utils.hold(20000);

        // Stop generator
        generator.terminate();

        Utils.hold(1000);

        System.exit(0);

    }

}
