package basic.thread1;

class ThreadLocalManager
{
    public static final ThreadLocal myThreadLocal = new ThreadLocal();
}
 
public class TestThreadLocal implements Runnable
{
 
    private String value;
    private long delayTime;
    private long sleepTime;
 
    public TestThreadLocal(String value, long delayTime, long sleepTime) {
        this.value = value;
        this.delayTime = delayTime;
        this.sleepTime = sleepTime;
    }
 
    @Override
    public void run()
    {
 
        try
        {
            Thread.sleep(this.delayTime);
            System.out.println("[" + this + "] is setting myThreadLocal [" + ThreadLocalManager.myThreadLocal + "] the value : " + this.value);
            ThreadLocalManager.myThreadLocal.set(this.value);
            Thread.sleep(this.sleepTime);
            System.out.println("[" + this + "] is accessing myThreadLocal [" + ThreadLocalManager.myThreadLocal + "] value : "
                    + ThreadLocalManager.myThreadLocal.get());
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
 
    }
 
    public static void main(String[] args)
    {
        TestThreadLocal test1 = new TestThreadLocal("V1", 0, 200);
        System.out.println("Creating test1 : " + test1);
        TestThreadLocal test2 = new TestThreadLocal("V2", 100, 500);
        System.out.println("Creating test2 : " + test2);
        Thread t1 = new Thread(test1);
        Thread t2 = new Thread(test2);
 
        t1.start();
        t2.start();
    }
 
}