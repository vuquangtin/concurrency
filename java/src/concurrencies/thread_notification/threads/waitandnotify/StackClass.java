package threads.waitandnotify;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/17 18:42
 */
public class StackClass {

    private Object[] stackArray;
    private volatile int topOfStack;

    public StackClass(int capacity) {
        stackArray = new Object[capacity];
        topOfStack = 1;
    }

    public synchronized Object pop() {
        System.out.println(Thread.currentThread() + ": popping");
        while (isEmpty()) {
            try {
                System.out.println(Thread.currentThread() + ": waiting to pop");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Object obj = stackArray[topOfStack];
        stackArray[topOfStack--] = null;
        System.out.println(Thread.currentThread() + ": notifying after pop");
        notify();
        return obj;
    }

    public synchronized void push(Object element) {
        System.out.println(Thread.currentThread() + ": pushing");
        while (isFull()) {
            try {
                System.out.println(Thread.currentThread() + ": topOfStack is " + topOfStack);
                System.out.println(Thread.currentThread() + ": waiting to push");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stackArray[++topOfStack] = element;
        notify();
        System.out.println(Thread.currentThread() + ": notifying after push");
    }

    /**
     * judge stackArray is full
     * @return boolean
     */
    public boolean isFull() {
        return topOfStack >= stackArray.length - 1;
    }
    /**
     * judge stackArray is empty
     * @return boolean
     */
    public boolean isEmpty() {
        return topOfStack < 0;
    }
}