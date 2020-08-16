package concurrency.java.optimize.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public final class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;

    private boolean daemon = false;
    private int threadPriority = Thread.NORM_PRIORITY;

    public NamedThreadFactory(String threadName) {
        this(threadName, false);
    }

    public NamedThreadFactory(String threadName, boolean daemon) {
        if(threadName == null) {
            throw new IllegalArgumentException();
        }
        SecurityManager s = System.getSecurityManager();
        this.group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = threadName + '-';
        this.daemon = daemon;
    }

    public NamedThreadFactory(String threadName, ThreadGroup threadGroup) {
        if(threadName == null) {
            throw new IllegalArgumentException();
        }
        if(threadGroup == null) {
            throw new IllegalArgumentException();
        }
        this.group = threadGroup;
        this.namePrefix = threadName + '-';
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    public void setPriority(int priority) {
        this.threadPriority = priority;
    }

    public Thread newThread(Runnable r) {
        final Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if(t.isDaemon() != daemon) {
            t.setDaemon(daemon);
        }
        if(t.getPriority() != threadPriority) {
            t.setPriority(threadPriority);
        }
        return t;
    }

}