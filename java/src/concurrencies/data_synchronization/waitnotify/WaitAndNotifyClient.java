package waitnotify;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
class StackImpl {
	private Object[] stackArray;
	private volatile int topOfStack;

	StackImpl(int capacity) {
		stackArray = new Object[capacity];
		topOfStack = -1;
	}

	public synchronized Object pop() {
		System.out.println(Thread.currentThread() + ": popping");
		while (isEmpty())
			try {
				System.out.println(Thread.currentThread() + ": waiting to pop");
				wait(); // (1)
			} catch (InterruptedException e) {
			}
		Object obj = stackArray[topOfStack];
		stackArray[topOfStack--] = null;
		System.out.println(Thread.currentThread() + ": notifying after pop");
		notify(); // (2)
		return obj;
	}

	public synchronized void push(Object element) {
		System.out.println(Thread.currentThread() + ": pushing");
		while (isFull())
			try {
				System.out.println(Thread.currentThread() + ": waiting to push");
				wait(); // (3)
			} catch (InterruptedException e) {
			}
		stackArray[++topOfStack] = element;
		System.out.println(Thread.currentThread() + ": notifying after push");
		notify(); // (4)
	}

	public boolean isFull() {
		return topOfStack >= stackArray.length - 1;
	}

	public boolean isEmpty() {
		return topOfStack < 0;
	}
}

abstract class StackUser extends Thread { // (5) Stack user

	protected StackImpl stack; // (6)

	StackUser(String threadName, StackImpl stack) {
		super(threadName);
		this.stack = stack;
		System.out.println(this);
		setDaemon(true); // (7) Daemon thread
		start(); // (8) Start this thread.
	}
}

class StackPopper extends StackUser { // (9) Popper
	StackPopper(String threadName, StackImpl stack) {
		super(threadName, stack);
	}

	public void run() {
		while (true)
			stack.pop();
	}
}

class StackPusher extends StackUser { // (10) Pusher
	StackPusher(String threadName, StackImpl stack) {
		super(threadName, stack);
	}

	public void run() {
		while (true)
			stack.push(new Integer(1));
	}
}

public class WaitAndNotifyClient {
	public static void main(String[] args) throws InterruptedException { // (11)

		StackImpl stack = new StackImpl(5);

		new StackPusher("A", stack);
		new StackPusher("B", stack);
		new StackPopper("C", stack);
		System.out.println("Main Thread sleeping.");
		Thread.sleep(1000);
		System.out.println("Exit from Main Thread.");
	}
}
