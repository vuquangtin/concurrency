package com.rxjava.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by achaub001c on 7/13/2016.
 * utility methods common to all
 */
public class GenericUtil {
    public static ExecutorService SERVICE = Executors.newFixedThreadPool(10);

    public static long getMilliElapsed(long start) {
        return System.currentTimeMillis() - start;
    }
}