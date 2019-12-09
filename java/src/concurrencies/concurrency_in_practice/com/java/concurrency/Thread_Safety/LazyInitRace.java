package com.java.concurrency.Thread_Safety;

import com.java.concurrency.annotations.NotThreadSafe;


/**
 * LazyInitRace
 *
 * Race condition in lazy initialization
 * Negative
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null)
            instance = new ExpensiveObject();
        return instance;
    }
}

class ExpensiveObject { }

