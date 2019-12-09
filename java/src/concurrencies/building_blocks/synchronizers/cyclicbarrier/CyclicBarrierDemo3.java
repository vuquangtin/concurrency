package synchronizers.cyclicbarrier;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

/**
 * <p>CyclicBarrier-循环屏障-实例</p>
 *
 * @author hanchao 2018/3/29 22:44
 **/
public class CyclicBarrierDemo3 {
    private static final Logger LOGGER = Logger.getLogger(CyclicBarrierDemo3.class);

    /**
     * <p>CyclicBarrier-循环屏障-模拟多线程计算</p>
     *
     * @author hanchao 2018/3/29 22:48
     **/
    public static void main(String[] args) {
        //数组大小
        int size = 50000;
        //定义数组
        int[] numbers = new int[size];
        //随机初始化数组
        for (int i = 0; i < size; i++) {
            numbers[i] = RandomUtils.nextInt(100, 1000);
        }

        //单线程计算结果
        System.out.println();
        Long sum = 0L;
        for (int i = 0; i < size; i++) {
            sum += numbers[i];
        }
        LOGGER.info("单线程计算结果：" + sum);

        //多线程计算结果
        //定义线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //定义五个Future去保存子数组计算结果
        final int[] results = new int[5];

        //定义一个循环屏障，在屏障线程中进行计算结果合并
        CyclicBarrier barrier = new CyclicBarrier(5, () -> {
            int sums = 0;
            for (int i = 0; i < 5; i++) {
                sums += results[i];
            }
            LOGGER.info("多线程计算结果：" + sums);
        });

        //子数组长度
        int length = 10000;
        //定义五个线程去计算
        for (int i = 0; i < 5; i++) {
            //定义子数组
            int[] subNumbers = Arrays.copyOfRange(numbers, (i * length), ((i + 1) * length));
            //盛放计算结果
            int finalI = i;
            executorService.submit(() -> {
                for (int j = 0; j < subNumbers.length; j++) {
                    results[finalI] += subNumbers[j];
                }
                //等待其他线程进行计算
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }

        //关闭线程池
        executorService.shutdown();
    }
}
