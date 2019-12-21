package basic.concurrency;

class NewThread implements Runnable{
	Thread t;
	
	NewThread(){
		//Create a new, second thread
		t = new Thread(this, "Demo Thread");
		System.out.println("Child Thread" + t);
		t.start();
	}
	
	//Entry point for 2nd thread
	public void run(){
		try {
			for (int i = 5; i > 0; i--) {
				System.out.println("Child Thread: "+ i);
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			System.out.println("Child Thread Interrupted");
		}
		System.out.println("Exiting Child Thread");
	}
	
}

public class ThreadDemo {

	public static void main(String[] args) {
		new NewThread();
		try {
			for (int i = 5; i > 0; i--) {
				System.out.println("Main Thread: "+ i);
				Thread.sleep(750);
			}
		} catch (InterruptedException e) {
			System.out.println("Main Thread Interrupted");
		}
		System.out.println("Exiting Main Thread");

	}

}