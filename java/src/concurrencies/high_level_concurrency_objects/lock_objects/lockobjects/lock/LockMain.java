package lockobjects.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */

public class LockMain {

  private Lock myLock = new ReentrantLock();

  public void updateResource() {

    try {
      // Acquire the lock
      myLock.lock();
    } finally {
      // Release the lock
      myLock.unlock();
    }
  }
}