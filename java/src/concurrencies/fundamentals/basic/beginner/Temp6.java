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
public class Temp6 {
    private String name;
 
    public String getName() {
        synchronized(this.name){
            return name;
        }
    }
 
    public void setName(String name) {
        synchronized(this.name){
            this.name = name;
        }
    }
}