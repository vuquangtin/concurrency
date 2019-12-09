package atomicvariables.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.LongAdder;

import javax.annotation.concurrent.ThreadSafe;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 *  LongAdder 和 AtomicLong 的区别
 *
 *  AtomicLong 底层依赖 CAS, CAS 是在一个死循环内不断修改目标的值, 直到修改成功, 如果竞争不激烈的时候,修改
 *  成功的概率很高, 否则修改失败的概率会很高, 在大量修改失败时, 这些原子操作会多次的尝试, 因此性能会受到
 *  一些影响
 *
 *  对于普通类型的 long, double 变量, JVM 允许将 64 位的读操作或写操作拆成两个 32 位的操作,LongAdder
 *  的核心是将 热点数据分离 它可以将内部的核心数据分离成为一个数组, 每一个线程访问时, 通过 Hash 等算法,
 *  运算到其中一个数字进行计数, 而最后的结果为这个数组的求和累加, 其中的热点数据 value 会被分离成多个单元的
 *  cell, 每个 cell 独自维护内部的值, 当前对象的实际值由多个 cell 累计合成, 这样的话热点就进行了有效的分离,
 *  并提高了并行度, LongAdder 将单点的更新压力分散到各点上, 在低并发时, 通过对 base 的更新使性能和 Atomic
 *  基本一致, 而在高并发时, 通过分散提高了性能
 *
 *  LongAdder 的缺点是在统计时如果有并发更新, 可能会导致统计的数据有些误差, 实际使用时, 在处理高并发数据
 *  时可以优先使用 LongAdder 而不是 AtomicLong,  在线程不是很多的情况下, 使用 AtomicLong 更高效,在需要
 *  生成全局唯一的数据时, 只能使用 AtomicLong
 *
 */

@Slf4j
@ThreadSafe
public class LongAdderExample {
	static Logger logger = Logger.getLogger(AtomicReferenceExample.class.getName());
    private static int threadTotal = 200;
    private static int clientTotal = 5000;

    private static LongAdder count = new LongAdder();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int index = 0; index < clientTotal; index++){
            exec.execute(()->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        exec.shutdown();
        logger.info("count : "+count);
    }

    private static void add(){
        count.increment();
    }
}
