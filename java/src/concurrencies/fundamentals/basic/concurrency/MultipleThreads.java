package basic.concurrency;

//Create a second Thread

class Threads implements Runnable{
	String threadName;
	Thread t;
	
	Threads(String name){
		//Create a new thread
		threadName = name;
		t = new Thread(this, threadName);
		System.out.println("New Thread: " + t);
		t.start();
	}
	
	//Entry point for thread
	public void run(){
		try {
			for (int i = 5; i > 0; i--) {
				System.out.println(threadName + " : " + i);
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			System.out.println(threadName + " Interrupted");
		}
		System.out.println("Exiting " + threadName);
	}
	
}

public class MultipleThreads {

	public static void main(String[] args) {
		Threads tObjOne = new Threads("One");
		Threads tObjTwo = new Threads("Two");
		Threads tObjThree = new Threads("Three");
		System.out.println("Thread One is alive: "+tObjOne.t.isAlive());
		System.out.println("Thread Two is alive: "+ tObjTwo.t.isAlive());
		System.out.println("Thread Three is alive: "+ tObjThree.t.isAlive());
		
		try {
				System.out.println("Waiting for Threads to finish.");
				tObjOne.t.join();
				tObjTwo.t.join();
				tObjThree.t.join();
					
		} catch (InterruptedException e) {
			System.out.println("Main Thread Interrupted");
		}
		System.out.println("Thread One is alive: "+tObjOne.t.isAlive());
		System.out.println("Thread Two is alive: "+ tObjTwo.t.isAlive());
		System.out.println("Thread Three is alive: "+ tObjThree.t.isAlive());
		System.out.println("Exiting Main Thread");

	}

}
