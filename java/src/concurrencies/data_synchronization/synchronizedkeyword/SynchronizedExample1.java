package synchronizedkeyword;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: SynchronizedExample1
 * @Description: TODO
 * @date 19-2-16 下午2:23
 */
@Slf4j
public class SynchronizedExample1 {
	static Logger logger = Logger.getLogger(SynchronizedExample1.class.getName());
    /**修饰代码块*/
    public void test1(int j){
        synchronized (this){
            for (int i = 0; i < j; i++) {
                logger.info("test1 - {}"+i);
            }
        }
    }
    /**修饰一个方法*/
    public synchronized void test2(int j){
        for (int i = 0; i < j; i++) {
            logger.info("test2 - {}"+i);
        }
    }

    public static void main(String[] args) {
         SynchronizedExample1 example1 = new SynchronizedExample1();
        SynchronizedExample1 example2 = new SynchronizedExample1();
        ExecutorService executorService = Executors.newCachedThreadPool();
         /**通过线程池和两次调用来模拟同步代码块的调用情况*/
         executorService.execute(()->{
             example1.test1(10);
         });
        executorService.execute(()->{
            example2.test2(5);
        });
    }
}
