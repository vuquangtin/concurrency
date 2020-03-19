package running.threads.managers;
import java.util.concurrent.BlockingQueue;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */


public class Producer<T> {

    private BlockingQueue<T> bq;

    public Producer(BlockingQueue<T> bq) {
        this.bq = bq;
    }

    public void put(T t) throws InterruptedException {
        bq.put(t);
    }

}

