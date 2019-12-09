package basic.beginner;
/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class Temp3 {
	 
    private boolean isReady;
 
    public static void main(String[] args) {
        Temp3 t3 = new Temp3();
        while(!t3.isReady){
            try {
                System.out.println("Not ready dear.");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}