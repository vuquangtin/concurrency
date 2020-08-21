package synchronizers.phaser.demo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class PhaserDemo {
	static Logger logger = Logger.getLogger(PhaserDemo.class);

	public static void main(String[] args) throws IOException {
		logger = Log4jUtils.initLog4j();
		method1();
	}

	private static void method1() {
		Phaser phaser = new Phaser() {
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				logger.debug(LocalDateTime.now()
						+ ": onAdvance，registeredParties="
						+ getRegisteredParties() + ", phase=" + getPhase()
						+ ", isTerminated=" + isTerminated() + ", ThreadId="
						+ Thread.currentThread().getId());
				return super.onAdvance(phase, registeredParties);
			}
		};
		logger.debug(LocalDateTime.now() + ": 主线程开始执行异步任务，registeredParties="
				+ phaser.getRegisteredParties() + ", phase="
				+ phaser.getPhase() + ", isTerminated=" + phaser.isTerminated()
				+ ", ThreadId=" + Thread.currentThread().getId());
		phaser.register();
		for (int i = 0; i < 5; i++) {
			phaser.register();
			logger.debug(LocalDateTime.now() + ": 注册一个屏障，registeredParties="
					+ phaser.getRegisteredParties() + ", phase="
					+ phaser.getPhase() + ", isTerminated="
					+ phaser.isTerminated() + ", ThreadId="
					+ Thread.currentThread().getId());
			int finalI = i;
			new Thread(() -> {
				try {
					TimeUnit.SECONDS.sleep(finalI);
					logger.debug(LocalDateTime.now() + ": 到达屏障，等待其他线程，"
							+ finalI + ", registeredParties="
							+ phaser.getRegisteredParties() + ", phase="
							+ phaser.getPhase() + ", isTerminated="
							+ phaser.isTerminated() + ", ThreadId="
							+ Thread.currentThread().getId());
					phaser.arriveAndAwaitAdvance();
					TimeUnit.SECONDS.sleep(finalI);
					logger.debug(LocalDateTime.now() + ": 屏障打开，开始执行剩下任务，"
							+ finalI + ", registeredParties="
							+ phaser.getRegisteredParties() + ", phase="
							+ phaser.getPhase() + ", isTerminated="
							+ phaser.isTerminated() + ", ThreadId="
							+ Thread.currentThread().getId());
					phaser.arriveAndDeregister();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
		phaser.arriveAndDeregister();
		logger.debug(LocalDateTime.now() + ": 主线程执行完毕，registeredParties="
				+ phaser.getRegisteredParties() + ", phase="
				+ phaser.getPhase() + ", isTerminated=" + phaser.isTerminated()
				+ ", ThreadId=" + Thread.currentThread().getId());
	}
}