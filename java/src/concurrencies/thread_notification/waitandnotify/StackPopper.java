package waitandnotify;


/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/17 19:03
 */
public class StackPopper extends StackUser{

    public StackPopper(String name, StackClass stack) {
        super(name, stack);
    }

    @Override
    public void run() {
        while (true){
            stack.pop();
        }
    }
}