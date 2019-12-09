package basic.options;

/*
 * Like the wait() / notify() strategy used in one of the previous examples, join() allows
 * the caller thread to wait until the target thread finishes.
 * If the target thread is already done, join() returns immediately to the caller thread 
 * (that's what happens with thread4 in this example).
 * 
 * Here, when the target thread finishes the sum, the caller thread (main) wakes up and 
 * calls the task.getSum() method which will certainly contains the total sum as the 
 * target thread has already finished its job.
 * 
 * The task4 has a small sleep time and therefore it finishes the sum before the others.
 * Hence, the main thread calls the thread4.join() but immediately returns to its execution
 * as the thread4 is finished.
 */

public class GetResultFromTaskUsingJoinsThreadAPI {
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Thread main started");
		
		GetResultFromTask task1 = new GetResultFromTask(500l);
		GetResultFromTask task2 = new GetResultFromTask(1000l);
		GetResultFromTask task3 = new GetResultFromTask(2000l);
		GetResultFromTask task4 = new GetResultFromTask(50l);
		Thread thread1 = new Thread(task1);	
		Thread thread2 = new Thread(task2);
		Thread thread3 = new Thread(task3);
		Thread thread4 = new Thread(task4);	
		thread1.setName("thread-1");
		thread2.setName("thread-2");
		thread3.setName("thread-3");
		thread4.setName("thread-4");
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		System.out.println("[" + Thread.currentThread().getName() + "] waiting for " + thread1.getName());
		thread1.join();
		System.out.println(thread1.getName() + " finished! Result: " + task1.getSum());
		
		System.out.println("[" + Thread.currentThread().getName() + "] waiting for " + thread2.getName());
		thread2.join();
		System.out.println(thread2.getName() + " finished! Result: " + task2.getSum());
		
		System.out.println("[" + Thread.currentThread().getName() + "] waiting for " + thread3.getName());
		thread3.join();
		System.out.println(thread3.getName() + " finished! Result: " + task3.getSum());
		
		// As thread-4 already finished (smaller sleep time), the join call only immediately
		// returns the control to the caller thread
		System.out.println("[" + Thread.currentThread().getName() + "] waiting for " + thread4.getName());
		thread4.join();
		System.out.println(thread4.getName() + " finished! Result: " + task4.getSum());
		
		System.out.println("Thread main finished");
	}
}

class GetResultFromTask implements Runnable {
	private long sleep;
	private int sum;
	
	public GetResultFromTask(long sleep) {
		this.sleep = sleep;
	}
	
	public void run() {
		for (int i = 1; i <= 10; i++) {
			System.out.println("[" + Thread.currentThread().getName() + "] Adding " + i);
			sum += i;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getSum() {
		return this.sum;
	}
}