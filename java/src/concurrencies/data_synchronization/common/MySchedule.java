package common;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MySchedule {

    static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

    public static void main(String[] args) {

        scheduledExecutorService.scheduleWithFixedDelay(() -> System.out.println("start schedule"), 0, 5, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(() -> System.out.println("start schedule2"), 0, 10, TimeUnit.SECONDS);

    }
}