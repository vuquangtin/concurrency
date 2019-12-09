package synchronizedkeyword;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

@Slf4j
public class SyncDemo2 {
	static Logger logger = Logger.getLogger(SyncDemo2.class.getName());
    /**
     * 修饰一个静态方法
     */
    public static synchronized void testStaticBlock(int j){
        for(int i = 0; i < 10; i++){
            logger.info("{}:test static Method - {} ");
        }
    }

    /**
     *  修饰一个方法
     */
    public synchronized void testClass(int j){
        synchronized (SyncDemo2.class){
            for(int i = 0; i < 10; i++){
                logger.info("{}:testBlock - {} ");
            }
        }
    }


    public static void main(String[] args) {
        SyncDemo2 demo = new SyncDemo2();
        SyncDemo2 demo2 = new SyncDemo2();
        ExecutorService executorService = Executors.newCachedThreadPool();

        /*
            sychronized 修饰的静态方法与 sychronized 修饰一个类的结果是一致的
        executorService.execute(()->{
            demo.testStaticBlock(1);
        });

        executorService.execute(()->{
            demo.testStaticBlock(2);
        });*/

        executorService.execute(()->{
            demo.testClass(1);
        });

        executorService.execute(()->{
            demo.testClass(2);
        });

    }
}
