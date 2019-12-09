package synchronizedkeyword;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

@Slf4j
public class SyncDemo {
	static Logger logger = Logger.getLogger(SyncDemo.class.getName());

	/**
	 * 修饰一个代码块
	 */
	public void testBlock(int j) {
		synchronized (this) {
			for (int i = 0; i < 10; i++) {
				logger.info("{}:testBlock - {} ");
			}
		}
	}

	/**
	 * 修饰一个方法
	 */
	public synchronized void testMethod(int j){
        for(int i = 0; i < 10; i++){
            logger.info("{}:testMethod - {} ");
        }
    }

	public static void main(String[] args) {
		SyncDemo demo = new SyncDemo();
		SyncDemo demo2 = new SyncDemo();
		ExecutorService executorService = Executors.newCachedThreadPool();

		/*
		 * 此时无论执行 同步代码块 或 同步方法, 都会顺序输出 0 --9 executorService.execute(()->{
		 * demo.testMethod(); }); executorService.execute(()->{
		 * demo.testMethod(); });
		 */

		/*
		 * 同步代码块只作用于不同对象, 不同对象之间是不影响的 executorService.execute(()->{
		 * demo.testBlock(1); });
		 * 
		 * executorService.execute(()->{ demo2.testBlock(2); });
		 */

		/**
		 * 不同对象之间是互不影响的
		 */
		executorService.execute(() -> {
			demo.testMethod(1);
		});

		executorService.execute(() -> {
			demo2.testMethod(2);
		});
	}
}
