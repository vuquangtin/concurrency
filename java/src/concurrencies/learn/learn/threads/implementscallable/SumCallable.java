package learn.threads.implementscallable;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现Callable接口，实现多线程分组求和计算
 * 要求：实现一段范围内连续数的计算 例如：求和 1~10000 用5个线程分开算，再求和
 */
public class SumCallable implements Callable<BigDecimal>{
    private long min;
    private long max;

    public SumCallable() {
    }

    public SumCallable(long min, long max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public BigDecimal call() throws Exception {
        BigDecimal sum = new BigDecimal(0);
        for(long i=min;i<=max;i++){
            sum = sum.add(new BigDecimal(i));
        }
        //System.out.println("min="+min+"max="+max+"sum="+sum);
        return sum;
    }

    /**
     * 多线程求和计算
     * @param min 起始值
     * @param max 末位置
     * @param threadNum 线程个数
     * @return
     */
    public BigDecimal getSum(long min,long max,long threadNum){
        //
        List<FutureTask<BigDecimal>> futureTaskList = new ArrayList<>();
        //先分配再计算
        long numSize = max-min+1;
        long groupSize = numSize/threadNum; //分组 大小
        //System.out.println("groupSize = "+groupSize);
        for(long i=0;i<threadNum;i++){
            long begin = 0;
            long end = 0;
            if(i==0){
                begin = min;
            }else{
                begin = min + groupSize * i;
            }
            if(i==threadNum-1){
                end = max;
            }else{
                end = min + groupSize * (i+1) - 1;
            }
            //System.out.println("begin="+begin+" end="+end);
            FutureTask<BigDecimal> futureTask = new FutureTask<>(new SumCallable(begin,end));
            futureTaskList.add(futureTask);
            new Thread(futureTask).start();
        }
        BigDecimal sum = taskListSum(futureTaskList);
        return sum;
    }

    private BigDecimal taskListSum(List<FutureTask<BigDecimal>> taskList){
        BigDecimal sum = new BigDecimal(0);
        for(FutureTask<BigDecimal> task : taskList){
            try {
                sum = sum.add(task.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return sum;
    }
    private static BigDecimal getSumOnly(long min,long max){
        BigDecimal sum = new BigDecimal(0);
        for(long i=min;i<=max;i++){
            sum = sum.add(new BigDecimal(i));
        }
        return sum;
    }
    public static void main(String[] args) {
        long min = 1;
        long max = 1000000000l;
        long beginTime = System.currentTimeMillis();
        SumCallable sumCallable = new SumCallable();
        BigDecimal sum = sumCallable.getSum(min,max,5);
        long endTime = System.currentTimeMillis();
        System.out.println(sum + " 耗时："+"endTime="+endTime+"beginTime="+beginTime);
        System.out.println(sum + " 耗时："+(endTime-beginTime));

        long beginTime2 = System.currentTimeMillis();
        BigDecimal sum2 = getSumOnly(min,max);

        long endTime2 = System.currentTimeMillis();
        System.out.println(sum2 + " 耗时："+"endTime2="+endTime2+"beginTime2="+beginTime2);
        System.out.println(sum2 + " 耗时："+(endTime2-beginTime2));

    }
}
