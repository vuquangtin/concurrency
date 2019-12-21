package basic.thread1;

public class Job implements Runnable {

    private PrintQueue printQueue;

    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.printf("%s: Going to print a job\n", Thread.currentThread().getName());
        printQueue.printJob();
        System.out.printf("%s: The document has been printed\n",Thread.currentThread().getName());
    }

    /*
     创建10个线程，访问printJob，观察线程是否同步执行
     */
    public static void main(String[] args) {
        PrintQueue printQueue=new PrintQueue();
        Thread[] threads = new Thread[10];
        for (int i = 0;i<10;i++) {
            threads[i] = new Thread(new Job(printQueue), "Thread" + i);
        }
        for (int i=0;i<10;i++) {
            threads[i].start();
        }
    }
}