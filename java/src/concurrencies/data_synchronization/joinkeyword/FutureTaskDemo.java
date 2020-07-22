package joinkeyword;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
 
public class FutureTaskDemo {
 
    public final static void main(String... args) {
 
        final Callable<Integer> callable = new Callable<Integer>() {
 
            @Override
            public Integer call() throws InterruptedException {
                System.out.println("starting to sleep thread '"
                        + Thread.currentThread().getName() + "'.....");
                Thread.sleep(3000);
                System.out.println("finished sleeping thread '"
                        + Thread.currentThread().getName() + "'.");
                return new Integer(6);
            }
        };
 
        final ExecutorService execute = Executors.newCachedThreadPool();
        final Future<Integer> futureTask = execute.submit(callable);
 
        System.out.println("about to get() via thread '"
                + Thread.currentThread().getName() + "'.");
        try {
            Integer result = futureTask.get();
            System.out.println("get() = '"+result+"' via thread '"
                    + Thread.currentThread().getName() + "'.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        execute.shutdown();
    }
}