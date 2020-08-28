package lockobjects.stampedlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by xfan on 16/1/30.
 *
 * Knowledge:
 *
 * `StampedLock` 是 Java 8 引入的新的一种锁. 它像 `ReadWriteLock` 一样也支持读写锁.
 * 不同的是 `StampedLock` 在加锁操作后会返回一个 `long` 类型的 stamp, 之后释放锁或者对
 * 锁进行其他操作时都要用到这个 stamp. `StampedLock` 还支持乐观锁(optimistic locking).
 *
 * Stamped lock 并没有实现同步重入(reentrant synchronization)特性.
 */
public class StampedLockExample2 {

    private final StampedLock lock = new StampedLock();

    private Map<String, String> dataMap = new HashMap<>();

    public void readWriteLockingUsingStampedLock() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable writeTask = () -> {
            long stamp = lock.writeLock();

            try {
                TimeUnit.SECONDS.sleep(1);
                dataMap.put("foo", "bar");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                lock.unlock(stamp);
            }
        };

        Runnable readTask = () -> {
            long stamp = lock.readLock();

            try {
                System.out.println("Read: " + dataMap.get("foo"));
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                lock.unlock(stamp);
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

    public void optimisticLockingInStampedLock() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            long stamp = lock.tryOptimisticRead();

            try {
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                lock.unlock(stamp);
            }
        });

        executor.submit(() -> {
            long stamp = lock.writeLock();

            try {
                System.out.println("Write lock acquired");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                lock.unlock(stamp);
                System.out.println("Write done.");
            }
        });

        stopExecutor(executor);
    }

    public void convertReadLockToWriteLock() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            long stamp = lock.readLock();

            try {
                if (null == dataMap.get("bar")) {
                    lock.tryConvertToWriteLock(stamp);
                    if (stamp == 0L) {
                        System.out.println("Could not convert to write lock.");
                        stamp = lock.writeLock();
                    }

                    dataMap.put("bar", "foo");
                }

                System.out.println("bar:" + dataMap.get("bar"));
            } finally {
                lock.unlock(stamp);
            }
        });

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
    	StampedLockExample2 sample = new StampedLockExample2();

        System.out.println("- - - Read-Write Locking Using StampedLock");
        sample.readWriteLockingUsingStampedLock();

        System.out.println("- - - Optimistic Locking Using StampedLock");
        sample.optimisticLockingInStampedLock();

        System.out.println("- - - Convert a read lock into a write lock");
        sample.convertReadLockToWriteLock();
    }
}