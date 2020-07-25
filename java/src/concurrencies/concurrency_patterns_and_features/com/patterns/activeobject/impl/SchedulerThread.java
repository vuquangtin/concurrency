package com.patterns.activeobject.impl;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SchedulerThread extends Thread {
    private final ActivationQueue queue;
    public SchedulerThread(ActivationQueue queue) {
        this.queue = queue;
    }
    public void invoke(MethodRequest request) {
        queue.putRequest(request);
    }
    public void run() {
        while (true) {
            MethodRequest request = queue.takeRequest();
            request.execute();
        }
    }
}