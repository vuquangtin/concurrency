package basic.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

class A1 extends ForkJoinTask<Void> {

	@Override
	public Void getRawResult() {
		return null;
	}

	@Override
	protected void setRawResult(Void value) {

	}

	@Override
	protected boolean exec() {
		return false;
	}
}

class B1 extends A1 {
}

class C1 extends B1 {
}

class D1 extends C1 {
}

class E1 extends D1 {
}

class ProfileThis extends E1 implements Runnable, Callable<Void> {

	@Override
	public Void call() throws Exception {
		System.out.println("This should be profiled.");
		return null;
	}

	@Override
	public void run() {
		System.out.println("This should be profiled.");
	}
}

class SimpleRunnable1 implements SimpleInterface {

	@Override
	public void run() {
		System.out.println("I am a Simple Runnable");
	}
}

class SimpleCallable1 implements Callable<Void> {

	@Override
	public Void call() throws Exception {
		System.out.println("I am a Simple Callable");
		return null;
	}
}

class SubtypeOfSimpleRunnable1 extends SimpleRunnable1 {

	@Override
	public void run() {
		System.out.println("I am a SubtypeOfSimpleRunnable");

	}
}

class SubtypeOfSubtypeOfSimpleRunnable1 extends SubtypeOfSimpleRunnable1 implements Runnable, Callable<Void> {

	@Override
	public void run() {
		System.out.println("I am a SubtypeOfSubtypeOfSimpleRunnable");
	}

	@Override
	public Void call() throws Exception {
		return null;
	}
}

interface SimpleInterface extends Runnable {
}

class TaskTest extends ForkJoinTask<Void> implements Runnable {

	@Override
	public Void getRawResult() {
		return null;
	}

	@Override
	protected void setRawResult(Void value) {
	}

	@Override
	protected boolean exec() {
		return false;
	}

	@Override
	public void run() {
		System.out.println("I am a task");
	}
}

class SecondTask implements Callable<Void> {

	public void run() {
		System.out.println("I am an additional task.");
	}

	@Override
	public Void call() throws Exception {
		return null;
	}
}

class SubtypeOfThread extends Thread {

	public void run() {
		System.out.println("I am a simple Thread");
	}
}

class SubtypeOfSubtypeOfThread extends SubtypeOfThread {

	public void run() {
		System.out.println("I am a SubtypeOfSubtypeOfThread");
	}
}

interface Z extends Runnable {
}

interface Y extends Z {
}

interface X extends Y {
}

interface W extends X {
}

class ThirdTask extends TaskTest implements W {

	@Override
	public void run() {
		System.out.println("Run");
	}

	@Override
	public Void getRawResult() {
		return null;
	}

	@Override
	protected void setRawResult(Void value) {
	}

	@Override
	protected boolean exec() {
		return false;
	}
}

class NewExecutor extends ForkJoinPool {
}

public class TestExecutor {

	public static void main(String... args) throws InterruptedException {

		ExecutorService executor = Executors.newFixedThreadPool(8);
		executor.submit(new SimpleRunnable1());
		executor.submit(new SimpleCallable1());
		executor.submit(new SubtypeOfSimpleRunnable1(), Integer.class);
		executor.submit((Runnable) new SubtypeOfSubtypeOfSimpleRunnable1());

		new TaskTest().run();

		test(executor);

		executor.shutdown();

		new SubtypeOfThread().start();
		new SubtypeOfSubtypeOfThread().start();
	}

	public static void test(ExecutorService executor) {
		executor.submit(new TaskTest());
		executor.submit(new SecondTask());
		executor.submit((Runnable) new ProfileThis());
	}
}