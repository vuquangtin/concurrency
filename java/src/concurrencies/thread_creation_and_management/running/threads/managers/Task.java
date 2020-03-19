package running.threads.managers;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public abstract class Task implements Runnable {

    protected long id;

    public Task(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}