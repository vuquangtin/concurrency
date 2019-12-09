package basic.beginner;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
@ThreadSafe
public class ConnectionPoolWatchDog2 {
    //we increment this counter
    @GuardedBy("this") private AtomicInteger borrowers;
 
    //we decrement this counter
    @GuardedBy("this") private AtomicInteger connections; 
 
    public ConnectionPoolWatchDog2() {
        this.borrowers = new AtomicInteger(1);
        this.connections = new AtomicInteger(10);
    }
 
    public void borrowOne() {
        //do some tasks
        synchronized(this){
            //increment the borrowers count
            this.borrowers.getAndIncrement();
            //decrement the remaining conenction count
            this.connections.getAndDecrement();
        }
        //do some other tasks
    }
 
    public void returnOne(){
        //do some tasks
        synchronized(this){
            //increment the remaining conenction count
            this.connections.getAndIncrement();
            //decrement the borrowers count
            this.borrowers.getAndDecrement();
        }
        //do some other tasks
    }
}