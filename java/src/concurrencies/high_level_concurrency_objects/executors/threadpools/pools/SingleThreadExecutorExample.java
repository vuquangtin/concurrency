package threadpools.pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

@Slf4j
public class SingleThreadExecutorExample {
	static Logger logger = Logger.getLogger(SingleThreadExecutorExample.class.getName());

	public static void main(String[] args) {
		ExecutorService exec = Executors.newSingleThreadExecutor();

		for (int i = 0; i < 10; i++) {
			final int index = i;
			exec.execute(new Runnable() {
				@Override
				public void run() {
					logger.info("task:{}" + index);
				}
			});
		}
		exec.shutdown();
	}

}
