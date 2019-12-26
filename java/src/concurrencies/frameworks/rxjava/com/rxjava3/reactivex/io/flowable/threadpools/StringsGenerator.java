package com.rxjava3.reactivex.io.flowable.threadpools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class StringsGenerator {

    private static final Logger log = LoggerFactory.getLogger(StringsGenerator.class);

    private AtomicInteger threadCounter = new AtomicInteger(1);

    private ExecutorService service = Executors.newCachedThreadPool(r -> {
        Thread thread = new Thread(r);
        thread.setName("Generator " + threadCounter.getAndIncrement());
        return thread;
    });

    private transient boolean running = true;

    private List<StringsListener> listeners = new LinkedList<>();
    private int threadCount;
    private CyclicBarrier barrier;

    private final Random RANDOM = new Random(0);

    private String[] strings;
    private boolean barriered;

    public StringsGenerator(final String[] words) {
        this(words, false);
    }

    public StringsGenerator(final String[] words, boolean barriered) {
        this.strings = words;
        this.barriered = barriered;
        init();
    }

    private void init() {
        log.info("Init");
        this.threadCount = strings.length;
        if (barriered) {
            barrier = new CyclicBarrier(threadCount, System.out::println);
        }
        launchGenerators();
    }

    private void launchGenerators() {
        for (int i = 0; i < threadCount; i++) {
            launchThread(strings[i]);
        }
    }

    private void launchThread(String str) {
        service.execute(() -> {
            final Object MUTEX = new Object();

            while (running) {
                try {
                    if (barriered) {
                        barrier.await();
                    }

                    listeners.forEach(subscriber -> {
                        subscriber.onString(str);
                    });

                    synchronized (MUTEX) {
                        MUTEX.wait(RANDOM.nextInt(200) + 1000);
                    }
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void register(StringsListener listener) {
        listeners.add(listener);
    }

    public void terminate() {
        log.info("Terminate");
        running = false;
    }

}
