package concurrency.java.bounded.blocking;

import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ConnectionPool {
	
	private BlockingQueue<Connection> connPool = new ArrayBlockingQueue<Connection>(10);
	private AtomicInteger connCount = new AtomicInteger();
	
	public Connection getConnection() throws Exception {
		Connection conn = null;
		try {
			conn = connPool.poll(5, TimeUnit.SECONDS);
			if (conn == null) {
				synchronized(connCount) {
					if (connCount.get() < 10) {
						conn = getNewConnection();
						connPool.offer(conn);
						connCount.incrementAndGet();
					}
				}
				if (conn == null) {
					throw new Exception("Connection Unavailable");
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	private Connection getNewConnection() {
		return null;
	}

}