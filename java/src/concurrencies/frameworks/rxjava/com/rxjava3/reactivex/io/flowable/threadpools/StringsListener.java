package com.rxjava3.reactivex.io.flowable.threadpools;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class StringsListener {

    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private final int ID;

    public StringsListener() {
        ID = COUNTER.getAndIncrement();
    }

    protected abstract void onString(String w);
    protected abstract void onError(Throwable thr);

    @Override
    public String toString() {
        return String.format("Listener ID:%d:%s", ID, super.toString());
    }

}
