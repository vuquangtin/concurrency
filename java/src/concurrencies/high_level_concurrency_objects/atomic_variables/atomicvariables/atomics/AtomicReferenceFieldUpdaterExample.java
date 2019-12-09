package atomicvariables.atomics;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * AtomicReferenceFieldUpdate 更新指定类中的指定字段, 该字段必须使用有 volatile 且非 static 修饰
 *
 */

@Slf4j
public class AtomicReferenceFieldUpdaterExample {
	static Logger logger = Logger.getLogger(AtomicReferenceFieldUpdaterExample.class.getName());
	private static AtomicIntegerFieldUpdater<AtomicReferenceFieldUpdaterExample> updater = AtomicIntegerFieldUpdater
			.newUpdater(AtomicReferenceFieldUpdaterExample.class, "count");

	@Getter
	public volatile int count = 100;

	private static AtomicReferenceFieldUpdaterExample updaterExample = new AtomicReferenceFieldUpdaterExample();

	public static void main(String[] args) {

		if (updater.compareAndSet(updaterExample, 100, 120)) {
			logger.info("update success 1 : " + updaterExample.count);
		}

		if (updater.compareAndSet(updaterExample, 100, 120)) {
			logger.info("update success 2 : {}" + updaterExample.count);
		} else {
			logger.info("update failed : {}" + updaterExample.count);
		}
	}
}
