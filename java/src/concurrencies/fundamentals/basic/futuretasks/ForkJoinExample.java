package basic.futuretasks;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

@Slf4j
public class ForkJoinExample extends RecursiveTask<Integer> {
	static Logger logger = Logger.getLogger(ForkJoinExample.class.getName());
    public static final int threshold = 5;
    private int start;
    private int end;

    public ForkJoinExample(int start, int end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= threshold;
        if (canCompute){
            for (int i = start; i <= end; i++){
                sum += i;
            }
        } else {
            int mid = (start + end) / 2;
            ForkJoinExample leftTask = new ForkJoinExample(start, mid);
            ForkJoinExample rightTask = new ForkJoinExample(mid + 1, end);

            leftTask.fork();
            rightTask.fork();

            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        ForkJoinExample task = new ForkJoinExample(1, 100);

        Future<Integer> res = pool.submit(task);
        try {
            logger.info("result : {}"+res.get());
        } catch (Exception e){
            logger.error("exception", e);
        }
    }
}
