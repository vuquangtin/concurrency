package basic.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by xfan on 16/1/30.
 *
 * Knowledge:
 *
 * `ReadWriteLock` 通过一对锁(read lock 和 write lock)来分别控制读操作和写操作.
 * 如果没有线程在对变量在进行写操作, 那么是可以允许多个线程并行地进行度操作.
 * 所以当没有线程持有 write lock 时, read lock 可以同时同时被多个线程持有.
 * 在读操作比写操作频繁的情况下, 读写锁可以提高系统的性能和吞吐量.
 */
public class ReadWriteLockExample {

    private Map<String, String> dataMap = new HashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void firstLookAtReadWriteLock() {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable writeTask = () -> {
            lock.writeLock().lock();

            try {
                TimeUnit.SECONDS.sleep(1);
                dataMap.put("Foo", "Bar");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                lock.writeLock().unlock();
            }
        };

        Runnable readTask = () -> {
            lock.readLock().lock();

            try {
                System.out.println("Read: " + dataMap.get("Foo"));
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                lock.readLock().unlock();
            }
        };

        executor.submit(writeTask);

        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);

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
        ReadWriteLockExample sample = new ReadWriteLockExample();
        sample.firstLookAtReadWriteLock();
    }
}