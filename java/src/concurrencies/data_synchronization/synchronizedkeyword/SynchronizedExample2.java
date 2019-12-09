package synchronizedkeyword;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: SynchronziedExample2
 * @Description: TODO
 * @date 19-2-16 下午3:01
 */
@Slf4j
public class SynchronizedExample2 {
	static Logger logger = Logger.getLogger(SynchronizedExample2.class.getName());
    /**修饰类*/
    public void test1(int j){
        synchronized (SynchronizedExample2.class){
            for (int i = 0; i < j; i++) {
                logger.info("test1 - {}"+i);
            }
        }
    }
    /**修饰一个静态方法*/
    public static synchronized void test2(int j){
        for (int i = 0; i < j; i++) {
            logger.info("test2 - {}"+i);
        }
    }

    public static void main(String[] args) {
        SynchronizedExample2 example1 = new SynchronizedExample2();
        SynchronizedExample2 example2 = new SynchronizedExample2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        /**通过线程池和两次调用来模拟同步代码块的调用情况*/
        executorService.execute(()->{
            test2(10);
        });
        executorService.execute(()->{
            test2(5);
        });
    }
}
