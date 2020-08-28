package lockobjects.stampedlock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.StampedLock;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

@Slf4j
public class StampedLockExample {
	static Logger logger = Logger.getLogger(StampedLockExample.class.getName());
    class Point{
        private double x , y;
        private final StampedLock sl = new StampedLock();

        void move(double dx, double dy){
            long stamp = sl.writeLock();
            try{
                x += dx;
                y += dy;
            } finally {
                sl.unlockWrite(stamp);
            }
        }

        /**
         *  乐观读锁
         *  检查发出乐观读锁后是否同时有其它写锁发生, 如果没有, 我们再次获得一个读悲观锁
         * @return
         */
        double distanceFormOrigin(){
            long stamp = sl.tryOptimisticRead();
            double currentX = x, currentY = y;
            if (!sl.validate(stamp)){
                stamp = sl.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    sl.unlockRead(stamp);
                }
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }

        /**
         * 悲观读锁
         * 循环检查当前状态是否符合, 将读锁转换为写锁, 如果转换成功, 替换票据, 进行状态改变, 如果
         * 替换不能成功, 显式的释放读锁, 显示直接进行读锁, 然后再通过循环尝试.
         * @param newX
         * @param newY
         */
        void moveIfAtOrigin(double newX, double newY){
            long stamp = sl.readLock();
            try{
                while (x == 0.0 && y == 0.0){
                    long ws = sl.tryConvertToWriteLock(stamp);
                    if (ws != 0L){
                        stamp = ws;
                        x = newX;
                        y = newY;
                        break;
                    } else {
                        sl.unlockRead(stamp);
                        stamp = sl.writeLock();
                    }
                }
            } finally {
                sl.unlockRead(stamp);
            }
        }
    }

    private static int count = 0;
    private final static StampedLock lock = new StampedLock();

    private static int threadNum = 200;
    private static int clientNum = 5000;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadNum);
        final CountDownLatch countDownLatch = new CountDownLatch(clientNum);

        for (int i = 0; i < clientNum; i++){
            exec.execute(()->{
                try{
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    System.out.println(e);
                }
                countDownLatch.countDown();
            });

        }
        countDownLatch.await();
        exec.shutdown();
        logger.info("{}"+count);
    }

    private static void add() {
        long stamp = lock.writeLock();
        try {
            count++;
        } finally {
            lock.unlock(stamp);
        }
    }

}
