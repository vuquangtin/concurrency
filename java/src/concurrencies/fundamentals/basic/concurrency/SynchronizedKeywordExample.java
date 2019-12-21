package basic.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by xfan on 16/1/28.
 *
 * Knowledge:
 *
 * Java 内部的并发(Synchronization)是围绕着一个叫做内部锁(intrinsic lock)或监视器锁(monitor lock)的内部实体实现的.
 *
 * 每个对象都有一个与它关联的内部锁. 当访问对象中被 synchronized 修饰的方法或代码块时, 需要先获取这个对象的内部锁,
 * 执行完毕时再释放内部锁. 当一个线程拥有了对象的内部锁时, 其他要访问相同方法或代码块的线程就要暂停执行以等待之前的线程释放锁.
 *
 * 当一个线程释放了一个内部锁, 这个动作就与下一个获取这个锁的动作建立了 "happens-before" 的关系.
 */
public class SynchronizedKeywordExample {

    private int count = 0;

    public void nonThreadSafeIncrement() {
        this.count = this.count + 1;
    }

    /**
     * 当一个线程执行一个 synchronized 方法时, 它会自动去获取这个方法所属的对象的内部锁, 当方法返回(包括因出现异常而返回)时再释放锁.
     * 当 synchronized 方法是静态方法时, 线程获取的内部锁是 `Class` 对象的内部锁.
     */
    public synchronized void threadSafeIncrement() {
        this.count = this.count + 1;
    }

    /**
     * 当使用 synchronized 声明, 必须显示地指定提供内部锁的对象.
     */
    public void threadSafeIncrementInSynchronizedBlock() {
        synchronized (this) {
            this.count = this.count + 1;
        }
    }

    public void incrementConcurrently() {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        IntStream.range(0, 10000)
                .forEach(i -> executor.submit(this::threadSafeIncrementInSynchronizedBlock));

        stopExecutor(executor);

        System.out.println("Final value of count: " + this.count);
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
        SynchronizedKeywordExample example = new SynchronizedKeywordExample();
        example.incrementConcurrently();
    }
}