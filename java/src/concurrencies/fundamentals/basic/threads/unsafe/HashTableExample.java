package basic.threads.unsafe;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import javax.annotation.concurrent.ThreadSafe;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

@Slf4j
@ThreadSafe
public class HashTableExample {
	static Logger logger = Logger.getLogger(HashTableExample.class.getName());
    private static int threadNum = 200;
    private static int clientNum = 5000;
    private static Map<Integer, Integer> table = new Hashtable<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadNum);
        final CountDownLatch countDownLatch = new CountDownLatch(clientNum);
        for (int i = 0; i < clientNum; i++){
            final  int threadNum = i;
            exec.execute(()->{
                try{
                    semaphore.acquire();
                    add(threadNum);
                    semaphore.release();
                }catch (Exception e){
                    System.out.println(e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        exec.shutdown();
        logger.info("size : " + table.size());
    }

    public static void add(int threadNum){
        table.put(threadNum, threadNum);
    }

}
