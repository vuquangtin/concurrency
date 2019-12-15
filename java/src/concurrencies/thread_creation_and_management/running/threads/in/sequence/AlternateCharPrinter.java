package running.threads.in.sequence;

public class AlternateCharPrinter {

    public static char ch = 65;

    private static void createAndStartThreads(int count) {
        Object lock = new Object();
        for (int i = 0; i < count; i++) {
            new Thread(new AlternateCharRunner((char) (65 + i), lock)).start();
        }

    }

    public static void main(String[] args) {
        createAndStartThreads(26);
    }

}

class AlternateCharRunner implements Runnable {

    private char ch;
    private Object lock;
    private static int runnerCount;

    public AlternateCharRunner(char ch, Object lock) {
        this.ch = ch;
        this.lock = lock;
        runnerCount++;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                while (ch != AlternateCharPrinter.ch) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(AlternateCharPrinter.ch++);
                if (AlternateCharPrinter.ch == (65 + runnerCount)) {
                    AlternateCharPrinter.ch = 65;
                }
                lock.notifyAll();
            }
        }
    }

}