package running.threads.in.sequence;

/***
 * @see https://javabypatel.blogspot.com/2017/06/running-threads-in-sequence-in-java.html
 * @author admin
 *
 */
public class ThreadRunningInSequence {

    public static void main(String[] args) {

    	ResourceLock lock = new ResourceLock();

        ThreadA a=new ThreadA(lock);
        ThreadB b=new ThreadB(lock);
        ThreadC c=new ThreadC(lock);

        a.start();
        b.start();
        c.start();
    }
}