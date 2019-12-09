package com.java.concurrency.Introduction;

import com.java.concurrency.annotations.GuardedBy;
import com.java.concurrency.annotations.ThreadSafe;


/**
 * Sequence
 *
 * @author Brian Goetz and Tim Peierls
 */

@ThreadSafe
public class Sequence {
    @GuardedBy("this") private int nextValue;

    public synchronized int getNext() {
        return nextValue++;
    }
}
