package initiation;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/17 16:48
 */
public class CallableExample implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int i = 0;
        for (;i<100;i++){
            System.out.println(Thread.currentThread().getName()+": "+i);
        }
        return i;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableExample ctt = new CallableExample();
        FutureTask<Integer> callBack = new FutureTask<>(ctt);
        new Thread(callBack,"Thread1").start();
        System.out.println(Thread.currentThread().getName()+": "+callBack.get());
    }
}