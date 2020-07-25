package executors.customthreadpoolexecutor.feature;

public class Java7_0346_CustomBlockingQueueThreadPoolExecutorWorkerThread implements Runnable
{
    private String name = null;
 
    public Java7_0346_CustomBlockingQueueThreadPoolExecutorWorkerThread(String name) {
        this.name = name;
    }
 
    public String getName() {
        return this.name;
    }
 
    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Executing : " + name);
    }
}

