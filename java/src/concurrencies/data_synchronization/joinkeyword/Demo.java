package joinkeyword;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class Demo {
	public static void main(String[] args) throws InterruptedException {
		Thread t2 = new SampleThread(1);
		t2.start();
		System.out.println("Invoking join");
		t2.join();
		System.out.println("Returned from join");
		
		Thread t1 = new SampleThread(0);
		t1.join();  //returns immediately

	}
}
