package threads.waitandnotify;


/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/17 19:04
 */
public class StackPusher extends StackUser{

    public StackPusher(String name, StackClass stack) {
        super(name, stack);
    }

    @Override
    public void run() {
        while (true){
            stack.push(1);
        }
    }
}