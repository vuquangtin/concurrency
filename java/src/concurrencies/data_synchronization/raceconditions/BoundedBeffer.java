package raceconditions;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BoundedBeffer {

    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    private int puts, takes, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            //说明队列已满，需要等待（测试条件谓词）
            while (count == items.length) {
                notFull.await();
            }
            if (++puts == items.length) {
                puts = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take(Object x) throws InterruptedException {
        lock.lock();
        try {
            //说明队列元素为空，没有值可等待值的插入（测试条件谓词）
            while (count == 0) {
                notEmpty.await();
            }
            if (++takes == items.length) {
                takes = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}