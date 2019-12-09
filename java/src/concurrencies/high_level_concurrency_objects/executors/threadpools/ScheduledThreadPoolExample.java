package threadpools;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: CachedThreadPoolExample
 * @ProjectName example
 * @Description: newScheduledThreadPool
 * 可以设置核心线程数和延迟调度时间
 * 创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。
 * @date 19-2-28 下午4:12
 */
@Slf4j
public class ScheduledThreadPoolExample {
	static Logger logger = Logger.getLogger(ScheduledThreadPoolExample.class.getName());
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newScheduledThreadPool(5);
         ((ScheduledExecutorService) executorService).scheduleAtFixedRate(new Runnable() {
             @Override
             public void run() {
                 logger.warn("schedule run ");
             }
             //延迟一秒每隔三秒调度任务
         },1,3,TimeUnit.SECONDS);
//        executorService.shutdown();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                logger.warn("schedule run ");
            }
        },new Date(),5*1000);
    }
}
