package concurrency.java.memory.model;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class LoopJitOptimization {
	
	private volatile int volatileCounter = 0;
	private int counter = 0;
	
	void volatileLoop() {
		for(int i = 0; i < 20; i++) {
			volatileCounter++;
		}
	}
	
	void loop() {
		for(int i = 0; i < 20; i++) {
			counter++;
		}
	}
	
	public static void main(String[] args) {
		LoopJitOptimization loopUnrolling = new LoopJitOptimization();
		for(int i = 0; i < 15000; i++) {
			loopUnrolling.loop();
			loopUnrolling.volatileLoop();
		}
		System.out.println(loopUnrolling.volatileCounter);
		System.out.println(loopUnrolling.counter);
	}

}