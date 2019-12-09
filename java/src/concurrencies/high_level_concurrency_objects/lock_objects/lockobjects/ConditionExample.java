package lockobjects;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

@Slf4j
public class ConditionExample {
	static Logger logger = Logger.getLogger(ConditionExample.class.getName());
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(()->{
            try {
                lock.lock();
                logger.info("wait signal");    //1
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("get signal");         //4
            lock.unlock();
        }).start();

        new Thread(()->{
            lock.lock();
            logger.info("get lock");           //2
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            condition.signalAll();
            logger.info("send signal ");       //3
            lock.unlock();
        }).start();

    }
}
