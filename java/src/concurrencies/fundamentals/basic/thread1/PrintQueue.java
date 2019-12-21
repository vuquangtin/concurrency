package basic.thread1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintQueue {

    /**
     * 控制一个或多个资源访问的计数器
     */
    private final Semaphore semaphore;

    //存放可打印的对象
    private boolean freePrinters[];
    private Lock lockPrinters;

    public PrintQueue() {
        //初始化，使用公平模式，默认为非公平模式，在公平模式下，等待时间最长的先获得锁
        //设置3个可用资源
        this.semaphore = new Semaphore(3,true);
        this.freePrinters =new boolean[3];
        for (int i=0;i<3;i++){
            freePrinters[i] = true;
        }
        lockPrinters = new ReentrantLock();
    }

    public void printJob() {
        //获取信息量
        try {
            semaphore.acquire();
            int assignedPrinter = getPrinter();
            long duration = (long) (Math.random() * 10);
//            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n", Thread.currentThread().getName(),duration);
            System.out.printf("%s: PrintQueue: Printing a Job in Printer %d during %d seconds\n", Thread.currentThread().getName(),assignedPrinter, duration);
            TimeUnit.SECONDS.sleep(duration);
            freePrinters[assignedPrinter]=true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); //释放
        }
    }

    //获取当前的打印序列
    private int getPrinter() {
        int ret = -1;
        try {
            lockPrinters.lock();
            for (int i=0; i<freePrinters.length; i++) {
                if (freePrinters[i]){
                    ret=i;
                    freePrinters[i]=false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockPrinters.unlock();
        }
        return ret;
    }
}