package thread.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CarAssembly {
	private List<Integer> part1 = new ArrayList<Integer>();
	private List<Integer> part2 = new ArrayList<Integer>();

	Object lock1 = new Object();
	Object lock2 = new Object();

	Random random = new Random(1000);

	public List<Integer> getPart1() {
		return part1;
	}

	public List<Integer> getPart2() {
		return part2;
	}

	private void process1(){
		synchronized (lock1) {
			try{
				Thread.sleep(10);
			}catch(InterruptedException ex){
				ex.printStackTrace();
			}
			part1.add(random.nextInt());
		}

	}

	private void process2(){
		synchronized (lock2) {
			try{
				Thread.sleep(10);
			}catch(InterruptedException ex){
				ex.printStackTrace();
			}
			part2.add(random.nextInt());
		}
	}

	public void process(){
		process1();
		process2();
	}


}

class WorkerThread implements Runnable{
	private CarAssembly assembly;

	public WorkerThread(CarAssembly assembly){
		this.assembly = assembly;
	}
	@Override
	public void run(){
		//produce 10000 
		for(int i=0; i<100; i++)
			assembly.process();
	}
}


