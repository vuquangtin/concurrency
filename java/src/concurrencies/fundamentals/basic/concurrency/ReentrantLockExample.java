package basic.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Created by xfan on 16/1/28.
 *
 * Knowledge:
 *
 * 在前面 `synchronized` 的例子中说过, 一个线程不能获取到已经被其他线程获取到的锁,
 * 但是一个线程可以再次获取它已经获取到的锁. 允许一个线程多次获得同一个锁就可以允许
 * 同步重入(reentrant synchronized). 当在一段同步的代码中直接或间接地调用了另一个
 * 包含同步代码的方法, 并且这些同步的代码用到了相同的锁时, 同步重入就起到作用了. 如果
 * 没有同步重入, 我们就要做很多防范措施去防止一个线程把自己阻塞住. 任何时候都只能有一个线程
 * 持有锁.
 *
 * Recall that a thread cannot acquire a lock owned by another thread.
 * But a thread can acquire a lock that it already owns. Allowing a thread
 * to acquire the same lock more than once enables reentrant synchronization.
 * This describes a situation where synchronized code, directly or indirectly,
 * invokes a method that also contains synchronized code, and both sets of code
 * use the same lock. Without reentrant synchronization, synchronized code would
 * have to take many additional precautions to avoid having a thread cause itself to block.
 */
public class ReentrantLockExample {

    private final ReentrantLock lock = new ReentrantLock();

    private int count = 0;
    private Map<String, String> dataMap = new HashMap<>();

    public void incrementCountThreadSafe() {
        lock.lock();

        try {
            this.count += 1;
        } finally {
            lock.unlock();
        }
    }

    public void addDataThreadSafe() {
        lock.lock();

        try {
            this.dataMap.put("Count", Integer.toString(this.count));
        } finally {
            lock.unlock();
        }
    }

    public void firstLookAtReentrantLock() {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        IntStream.range(0, 10000)
                .forEach(i -> executor.submit(() -> {
                    this.incrementCountThreadSafe();
                    this.addDataThreadSafe();
                }));

        stopExecutor(executor);

        System.out.println("Final value of count:  " + this.count);
        System.out.println("Value of count in map: " + this.dataMap.get("Count"));
    }

    public void moreMethodsOfLock() {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(() -> {
            lock.lock();

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                lock.unlock();
            }
        });

        System.out.println("Locked: " + lock.isLocked());
        System.out.println("Held by me: " + lock.isHeldByCurrentThread());
        boolean locked = lock.tryLock();
        System.out.println("Lock acquired: " + locked);

        stopExecutor(executor);
    }

    private void stopExecutor(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        } finally {
            if (!executor.isTerminated()) {
                executor.shutdownNow();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockExample example = new ReentrantLockExample();
        example.firstLookAtReentrantLock();
        example.moreMethodsOfLock();
    }
}