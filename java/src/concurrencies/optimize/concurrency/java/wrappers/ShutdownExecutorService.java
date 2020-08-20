package concurrency.java.wrappers;

import java.util.concurrent.ExecutorService;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ShutdownExecutorService extends DelegatingExecutorService implements AutoCloseable
{
    ShutdownExecutorService(ExecutorService delegate)
    {
        super(delegate);
    }

    @Override
    public void close()
    {
        getDelegate().shutdown();
    }
}