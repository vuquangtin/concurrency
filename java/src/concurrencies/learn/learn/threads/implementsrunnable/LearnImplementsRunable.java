package learn.threads.implementsrunnable;

/**
 * 实现Runnable接口创建线程
 * 说明：
 *      如果自己的类已经extends另一个类，就无法直接extends Thread，此时，可以实现一个Runnable接口
 */
public class LearnImplementsRunable implements Runnable{
    @Override
    public void run() {
        System.out.println("LearnImplementsRunable");
    }

    public static void main(String[] args) {
        LearnImplementsRunable learnImplementsRunable = new LearnImplementsRunable();
        //为了启动MyThread，需要首先实例化一个Thread，并传入自己的MyThread实例：
        //事实上，当传入一个Runnable target参数给Thread后，Thread的run()方法就会调用target.run()
        Thread thread = new Thread(learnImplementsRunable);
        thread.start();
    }


}
