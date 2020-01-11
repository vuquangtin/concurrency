package learn.threads.implementscallable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现Callable接口通过FutureTask包装器来创建Thread线程
 * 说明：
 *      该线程方法可以支持获取到返回值结果
 */
public class LearnImplementsCallable implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        System.out.println("LearnImplementsCallable");
        return 1;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> oneCallable = new LearnImplementsCallable();
        //由Callable<Integer>创建一个FutureTask<Integer>对象：
        FutureTask<Integer> task = new FutureTask<Integer>(oneCallable);
        //注释：FutureTask<Integer>是一个包装器，它通过接受Callable<Integer>来创建，它同时实现了Future和Runnable接口。
        //由FutureTask<Integer>创建一个Thread对象：
        Thread oneThread = new Thread(task);
        oneThread.start();
        //Callable接口支持返回执行结果，此时需要调用FutureTask.get()方法实现，
        //此方法会阻塞主线程直到获取‘将来’结果；当不调用此方法时，主线程不会阻塞！
        int num = task.get();
        System.out.println(num);
        //至此,一个线程就创建完成了.
    }

}
