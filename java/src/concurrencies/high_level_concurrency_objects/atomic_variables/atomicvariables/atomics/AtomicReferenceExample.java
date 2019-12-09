package atomicvariables.atomics;

import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.concurrent.ThreadSafe;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;


@Slf4j
@ThreadSafe
public class AtomicReferenceExample {
	static Logger logger = Logger.getLogger(AtomicReferenceExample.class.getName());
    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {

        count.compareAndSet(0, 2);
        count.compareAndSet(0, 1);
        count.compareAndSet(1, 3);
        count.compareAndSet(2, 4);
        count.compareAndSet(3, 6);

        logger.info("count : {}"+ count.get());
    }
}
