package lockobjects.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: LockExample_ReentrantLock
 * @ProjectName example
 * @Description: TODO
 * @date 19-2-26 下午8:59
 */
@Slf4j
public class LockExample_ReentrantLock {
	static Logger logger = Logger.getLogger(LockExample_ReentrantLock.class.getName());

	public static void main(String[] args) {
		ReentrantLock reentrantLock = new ReentrantLock();
		Condition condition = reentrantLock.newCondition();

		new Thread(() -> {
			try {
				reentrantLock.lock();
				logger.info("wait signal"); // 1
				condition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info("get signal"); // 4
			reentrantLock.unlock();
		}).start();

		new Thread(() -> {
			reentrantLock.lock();
			logger.info("get lock"); // 2
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				condition.signalAll();
				logger.info("send signal ~ "); // 3
				reentrantLock.unlock();
			}).start();
	}
}
