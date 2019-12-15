package running.threads.in.sequence;

/**
 * One thread prints 1. Another 2. And another 3.
 * The program prints 123123123.... upto 10 times.
 * This is an easy program which can be modified to print ABCABCABC...
 * or it can be modified to have one thread print 1,4,7.. Another 2,5,8...
 * And another 3,6,9... And the program should print 1,2,3,4,5,6,7,8,9...
 *
 * @author Sinu John
 * www.sinujohn.com
 */
public class ThreadOrderControl {

    public static final int MAX_INT = 3;

    public static void main(String[] args) throws InterruptedException {
        MyState state = new MyState();

        for (int i = 1; i <= MAX_INT; i ++) {
            final Thread t = new Thread(new MyRunnableThreadOrderControl(10, i, state));
            t.start();
        }
    }
}

class MyState {
    private int state = 1;

    public int getState() {
        return state;
    }

    public void incrState() {
        this.state = this.state % ThreadOrderControl.MAX_INT + 1;
    }
}

class MyRunnableThreadOrderControl implements Runnable {

    private final int max;
    private final int value;
    private final MyState state;

    MyRunnableThreadOrderControl(int max, int value, MyState state) {
        this.max = max;
        this.value = value;
        this.state = state;
    }

    @Override
    public void run() {
        int count = 0;
        while(count < max) {
            synchronized (this.state) {
                if (this.state.getState() == this.value) {
                    System.out.println(count + " : " + value);
                    count++;
                    this.state.incrState();
                }
            }
        }
    }
}
 
