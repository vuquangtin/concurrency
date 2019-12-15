package executors.completions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorCompletionServiceTest<T> {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(pool);
        List<Future<Integer>> futureList = new ArrayList<>();
        futureList.add(executorCompletionService.submit(new CompletionTask("task1", 5000)));
        futureList.add(executorCompletionService.submit(new CompletionTask("task2", 3000)));
        futureList.add(executorCompletionService.submit(new CompletionTask("task3", 6000)));
        futureList.add(executorCompletionService.submit(new CompletionTask("task4", 1000)));


        futureList.forEach(integerFuture -> {
            try {
                Future<Integer> future = executorCompletionService.take();
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        pool.shutdown();
    }
    private void test(){
        ExecutorCompletionService<T> executorCompletionService = new ExecutorCompletionService<>(Executors.newFixedThreadPool(10));
    }
}