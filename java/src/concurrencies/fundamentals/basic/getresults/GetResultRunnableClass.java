package basic.getresults;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class RunnableClass implements Runnable {

	private final BlockingQueue<Integer> queue;

	public RunnableClass(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	public void run() {
		int id = 100;

		try {
			Thread.sleep(1000);
			queue.put(id);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class GetResultRunnableClass {
	public static void main(String[] args) {

		BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

		RunnableClass runcls = new RunnableClass(queue);
		new Thread(runcls).start();

		try {
			System.out.println("running");
			Integer response = queue.take();
			System.out.println("response:"+response);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // waits until takes value from
											// queue
	}
}