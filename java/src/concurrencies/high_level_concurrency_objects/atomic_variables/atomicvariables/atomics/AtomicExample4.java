package atomicvariables.atomics;

import java.util.concurrent.atomic.AtomicReference;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: AtomicExample4
 * @Description: TODO
 * @date 19-2-16 上午10:10
 */
@Slf4j
	
public class AtomicExample4 {
	static Logger logger = Logger.getLogger(AtomicExample4.class.getName());
	private static AtomicReference<Integer> count = new AtomicReference<>(0);

	public static void main(String[] args) {
		count.compareAndSet(0, 2);
		count.compareAndSet(0, 1);
		count.compareAndSet(1, 3);
		count.compareAndSet(2, 4);
		count.compareAndSet(3, 5);
		logger.info("count:{}" + count.get());
	}
}
