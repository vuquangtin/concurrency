package basic.options;

/*
 * Instantiate a thread by using Runnable interface
 */

public class InstantiateUsingRunnable {
	
	public static void main(String[] args) {
		
		System.out.println("Thread main started");
		
		Thread thread1 = new Thread(new InstantiateUsingTask("any data that the thread may need to process"));	
		Thread thread2 = new Thread(new InstantiateUsingTask("any data that the thread may need to process"));	
		thread1.start();
		thread2.start();
		
		System.out.println("Thread main finished");
	}
}

class InstantiateUsingTask implements Runnable {
	private String anyData;
	
	public InstantiateUsingTask(String anyData) {
		this.anyData = anyData;
	}
	
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("[" + Thread.currentThread().getName() + "] [data=" + this.anyData + "] Message " + i);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}