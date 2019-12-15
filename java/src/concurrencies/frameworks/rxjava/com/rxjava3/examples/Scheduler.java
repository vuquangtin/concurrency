package com.rxjava3.examples;

import java.util.concurrent.Executor;

/**
 * @Description
 * @Date 2019/11/20 15:44
 * @Author mxz
 */
public class Scheduler {
    // 创建线程池
    final Executor executor;
    public Scheduler(Executor executor) {
        this.executor = executor;
    }

    public Worker createWorker() {
        return new Worker(executor);
    }

    //具体任务的执行者
    public static class Worker {
        final Executor executor;
        public Worker(Executor executor) {
            this.executor = executor;
        }

        public void schedule(Runnable runnable) {
            executor.execute(runnable);
        }
    }
}