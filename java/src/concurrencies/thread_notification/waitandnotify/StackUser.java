package waitandnotify;


/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/17 19:01
 */
public abstract class StackUser extends Thread{
    protected StackClass stack;

    public StackUser(String name, StackClass stack) {
        super(name);
        this.stack = stack;
        System.out.println(this);
        setDaemon(true);
        start();
    }
}