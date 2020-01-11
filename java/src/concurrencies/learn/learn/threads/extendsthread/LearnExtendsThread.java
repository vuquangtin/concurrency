package learn.threads.extendsthread;

/**
 * 继承Thread类创建线程
 * 说明：
 *      Thread类本质上是实现了Runnable接口的一个实例，代表一个线程的实例。
 *      启动线程的唯一方法就是通过Thread类的start()实例方法。
 *      start()方法是一个native方法，它将启动一个新线程，并执行run()方法。
 *      这种方式实现多线程很简单，通过自己的类直接extend Thread，并复写run()方法，就可以启动新线程并执行自己定义的run()方法。
 */
public class LearnExtendsThread extends Thread{
    private Integer num; // 正在执行的任务数

    public LearnExtendsThread(Integer num) {
        this.num = num;
    }
    @Override
    public void run() {
        // System.out.println("LearnExtendsThread");
        System.out.println(Thread.currentThread().getName()+" 正在执行第 "+ num + "个任务");
        try {
            Thread.sleep(500);// 模拟执行任务需要耗时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" 执行完毕第 " + num + "个任务");
    }

    public static void main(String[] args) {
        LearnExtendsThread learnExtendsThread = new LearnExtendsThread(1);
        learnExtendsThread.start();
    }

}
