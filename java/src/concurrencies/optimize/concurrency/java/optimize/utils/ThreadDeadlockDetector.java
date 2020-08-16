package concurrency.java.optimize.utils;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public final class ThreadDeadlockDetector {

    private final Timer threadCheck = new Timer("ThreadDeadlockDetector", true);
    private final ThreadMXBean mbean;
    private final boolean isObjectMonitorUsageSupported;
    private final boolean isSynchronizerUsageSupported;
    private final long deadlockCheckPeriod;

    private final Set<Listener> listeners = new CopyOnWriteArraySet<Listener>();
    private final AtomicBoolean started = new AtomicBoolean(false);

    /**
     * The number of milliseconds between checking for deadlocks.
     * It may be expensive to check for deadlocks, and it is not
     * critical to know so quickly.
     */
    private static final long DEFAULT_DEADLOCK_CHECK_PERIOD = 10000L;

    public ThreadDeadlockDetector() {
        this(DEFAULT_DEADLOCK_CHECK_PERIOD);
    }

    public ThreadDeadlockDetector(long deadlockCheckPeriod) {
        this.mbean = ManagementFactory.getThreadMXBean();
        this.isObjectMonitorUsageSupported = mbean.isObjectMonitorUsageSupported();
        this.isSynchronizerUsageSupported = mbean.isSynchronizerUsageSupported();
        this.deadlockCheckPeriod = deadlockCheckPeriod;
    }

    public void start() {
        if(started.compareAndSet(false, true)) {
            threadCheck.schedule(new TimerTask() {
                public void run() {
                    checkForDeadlocks();
                }
            }, 10, deadlockCheckPeriod);
        }
    }

    public void stop() {
        threadCheck.cancel();
    }

    private void checkForDeadlocks() {
        long[] ids = findDeadlockedThreads();
        if(ids != null && ids.length > 0) {
            ThreadInfo[] infos = mbean.getThreadInfo(ids, isObjectMonitorUsageSupported, isSynchronizerUsageSupported);
            fireDeadlockDetected(infos);
        }
    }

    private long[] findDeadlockedThreads() {
        // JDK 1.5 only supports the findMonitorDeadlockedThreads()
        // method, so you need to comment out the following three lines
        if(isSynchronizerUsageSupported) {
            return mbean.findDeadlockedThreads();
        } else {
            return mbean.findMonitorDeadlockedThreads();
        }
    }

    private void fireDeadlockDetected(ThreadInfo[] threadInfos) {
        for(Listener l : listeners) {
            l.deadlockDetected(threadInfos);
        }
    }

    public boolean addListener(Listener l) {
        return listeners.add(l);
    }

    public boolean removeListener(Listener l) {
        return listeners.remove(l);
    }

    /**
     * This is called whenever a problem with threads is detected.
     */
    public interface Listener {
        void deadlockDetected(ThreadInfo[] deadlockedThreads);
    }

    public static final class StderrDeadlockReporter implements ThreadDeadlockDetector.Listener {
        public void deadlockDetected(ThreadInfo[] threads) {
            System.err.println("Deadlocked Threads:");
            System.err.println("-------------------");
            for(ThreadInfo ti : threads) {
                System.err.println(ti);
            }
        }
    }
}