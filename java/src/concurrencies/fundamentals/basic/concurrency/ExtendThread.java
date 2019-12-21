package basic.concurrency;

//Create a second Thread

class ChildThread extends Thread{
		
	ChildThread(){
		//Create a new, second thread
		super("Demo Thread");
		System.out.println("Child Thread" + this);
		start();
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

public class ExtendThread {

	public static void main(String[] args) {
		new ChildThread();
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