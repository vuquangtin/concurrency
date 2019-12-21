package basic.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

class SimpleRunnable2 implements Runnable {
	@Override
	public void run() {
		print();
	}

	public void print() {
		System.out.println("This is a SimpleRunnable");
	}
}

class ComplexRunnable2 extends SimpleRunnable2 {
	@Override
	public void run() {
		print();
	}

	public void print() {
		System.out.println("This is a ComplexRunnable");
	}
}

class ExtremeRunnable2 extends ComplexRunnable2 {
	@Override
	public void run() {
		print();
	}

	public void print() {
		System.out.println("This is a ExtremeRunnable");
	}
}

class GuardianRunnable2 extends ExtremeRunnable2 {
	@Override
	public void run() {
		print();
	}

	public void print() {
		System.out.println("This is a GuardianRunnable");
	}
}

class SimpleThread2 extends Thread {
	public SimpleThread2(Runnable runnable) {
		super(runnable);
	}
}

class ComplexThread2 extends SimpleThread2 {
	public ComplexThread2(Runnable runnable) {
		super(runnable);
	}
}

class ExtremeThread2 extends ComplexThread2 {
	public ExtremeThread2(Runnable runnable) {
		super(runnable);
	}
}

class GuardianThread2 extends ExtremeThread2 {
	public GuardianThread2(Runnable runnable) {
		super(runnable);
	}
}

class SimpleCallable2 implements Callable<Object> {
	@Override
	public Object call() throws Exception {
		print();
		return null;
	}

	public void print() {
		System.out.println("This is a SimpleCallable");
	}
}

class ComplexCallable2 extends SimpleCallable2 {
	@Override
	public Object call() throws Exception {
		print();
		return null;
	}

	public void print() {
		System.out.println("This is a ComplexCallable");
	}
}

class ExtremeCallable2 extends ComplexCallable2 {
	@Override
	public Object call() throws Exception {
		print();
		return null;
	}

	public void print() {
		System.out.println("This is a ExtremeCallable");
	}
}

class GuardianCallable2 extends ExtremeCallable2 {
	@Override
	public Object call() throws Exception {
		print();
		return null;
	}

	public void print() {
		System.out.println("This is a GuardianCallable");
	}
}

interface RunnableAndCallable<T> extends Runnable, Callable<T> {
}

interface A {
}

interface B {
}

interface AandB extends A, B {
}

class ABRC<T> implements AandB, RunnableAndCallable<T> {
	public void test() {
		String str = new String("Test");
		int a = 3 + 99;
		int b = 90 + 1;
	}

	@Override
	public void run() {
		test();
	}

	@Override
	public T call() throws Exception {
		int a = 1;
		return null;
	}
}

class SimpleRunnableAndCallable<T> implements RunnableAndCallable<T> {
	@Override
	public void run() {
		System.out.println("Run");
	}

	@Override
	public T call() throws Exception {
		System.out.println("Call");
		return null;
	}
}

class ComplexRunnableAndCallable extends SimpleRunnableAndCallable<Object> {
}

class ExtremeRunnableAndCallable extends ComplexRunnableAndCallable {
}

class GuardianRunnableAndCallable extends ExtremeRunnableAndCallable {
}

public class TestRunnableAndCallable {

	public static void main(String[] args) {

		Thread simpleThread0 = new SimpleThread2(new ABRC<Object>());
		simpleThread0.start();

		Thread simpleThread1a = new SimpleThread2(new SimpleRunnableAndCallable<Object>());
		simpleThread1a.start();

		Thread simpleThread1b = new SimpleThread2(new ComplexRunnableAndCallable());
		simpleThread1b.start();

		Thread simpleThread1c = new SimpleThread2(new ExtremeRunnableAndCallable());
		simpleThread1c.start();

		Thread simpleThread1 = new SimpleThread2(new SimpleRunnable2());
		simpleThread1.start();

		Thread simpleThread2 = new ComplexThread2(new ComplexRunnable2());
		simpleThread2.start();

		Thread simpleThread3 = new ExtremeThread2(new ExtremeRunnable2());
		simpleThread3.start();

		Thread simpleThread4 = new GuardianThread2(new GuardianRunnable2());
		simpleThread4.start();

		ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

		tpe.execute(new SimpleRunnable2());
		tpe.execute(new ComplexRunnable2());
		tpe.execute(new ExtremeRunnable2());
		tpe.execute(new GuardianRunnable2());

		tpe.submit(new SimpleCallable2());
		tpe.submit(new ComplexCallable2());
		tpe.submit(new ExtremeCallable2());
		tpe.submit(new GuardianCallable2());

		tpe.shutdown();
	}
}