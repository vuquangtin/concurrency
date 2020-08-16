package concurrency.java.optimize.tasks;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class  DemoTask implements Runnable
{
    private String name = null;

    public DemoTask(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Executing : " + name);
    }
}