package basic.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

class SimpleRunnable implements Runnable {
	@Override
	public void run() {
		System.out.println("This is a SimpleRunnable");		
	}	
}

class ComplexRunnable extends SimpleRunnable {
	@Override
	public void run() {
		System.out.println("This is a ComplexRunnable");		
	}
}

class SimpleCallable implements Callable<Object> {
	@Override
	public Object call() throws Exception {
		System.out.println("This is a SimpleCallable");
		return null;
	}
}

class ComplexCallable extends SimpleCallable {
	@Override
	public Object call() throws Exception {
		System.out.println("This is a ComplexCallable");
		return null;
	}
}

class SimpleExecutor implements Executor {
	@Override
	public void execute(Runnable command) {
		new Thread(command).start(); 
	}
}

public class TestJoinAndThreadPool {
    	
    public static void main(String[] args) {

    	Thread simpleThread = new Thread(new SimpleRunnable());
    	simpleThread.start();
    	
    	try {
			simpleThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	Thread simpleThread2 = new Thread(new SimpleRunnable());
    	simpleThread2.start();
    	
    	try {
			simpleThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    
    	Executor simpleExecutor = new SimpleExecutor();
    	simpleExecutor.execute(new SimpleRunnable());
    	
    	ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    	for (int i=1; i<=2; i++) {
    		tpe.execute(new SimpleRunnable());
    		tpe.execute(new ComplexRunnable());
    	}
    	
    	for (int i=1; i<=2; i++) {
    		tpe.submit(new SimpleCallable());
    		tpe.submit(new ComplexCallable());
    	}
    	
    	tpe.shutdown();
    }
}