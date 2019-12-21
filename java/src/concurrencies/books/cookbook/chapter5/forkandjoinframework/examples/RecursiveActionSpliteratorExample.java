package cookbook.chapter5.forkandjoinframework.examples;


import java.math.BigInteger;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;


public class RecursiveActionSpliteratorExample {

    public static void main (String[] args) {
        List<BigInteger> list = new ArrayList<>();
        for (int i = 3; i < 20; i++) {
            list.add(new BigInteger(Integer.toString(i)));
        }
        ForkJoinPool.commonPool().invoke(new FactorialTask(list.spliterator()));
    }

    private static class FactorialTask extends RecursiveAction {
        private static int SEQUENTIAL_THRESHOLD = 5;
        private Spliterator<BigInteger> spliterator;


        private FactorialTask (Spliterator<BigInteger> integerSpliterator) {
            this.spliterator = integerSpliterator;
        }

        @Override
        protected void compute () {
            long size = spliterator.estimateSize();
            if (size <= SEQUENTIAL_THRESHOLD) {
                showFactorials();
            } else {
                FactorialTask task = new FactorialTask(spliterator.trySplit());
                task.fork();
                this.compute();
            }
        }

        private void showFactorials () {
            spliterator.forEachRemaining(i -> {
                System.out.printf("[%s] : %s! = %s, thread = %s %n",
                                    LocalTime.now(), i,
                                    CalcUtil.calculateFactorial(i),
                                    Thread.currentThread().getName());
                try { //sleep to simulate long running task
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }
    }
}

