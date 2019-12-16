package threads.waitandnotify;


/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/17 19:05
 */
public class WaitAndNotifyExample {
    public static void main(String[] args) {
        StackClass stack = new StackClass(5);
        new StackPusher("Pusher1", stack);
        new StackPusher("Pusher2", stack);
        new StackPopper("Popper1", stack);
        System.out.println("Main Thread sleeping.");

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Exit from Main Thread.");
    }
}