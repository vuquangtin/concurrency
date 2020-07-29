package concurrency.java.memory.model.jmm;

import org.apache.log4j.Logger;

import core.java.base.TemplateMethod;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class JavaMemoryModel extends TemplateMethod {

	public static void main(String[] args) {
		new JavaMemoryModel().runTemplateMethod(args);
	}

	class MyRunnable implements Runnable {

		public void run() {
			methodOne();
		}

		public void methodOne() {
			int localVariable1 = 45;
			logger.debug(localVariable1);
			MySharedObject localVariable2 = MySharedObject.sharedInstance;
			logger.debug(localVariable2);
			// ... do more with local variables.
			logger.debug(localVariable2.member1);
			logger.debug(localVariable2.member2);
			logger.debug(localVariable2.object2);
			logger.debug(localVariable2.object4);
			methodTwo();
		}

		public void methodTwo() {
			Integer localVariable1 = new Integer(99);
			logger.debug(localVariable1);

			// ... do more with local variable.
		}
	}

	static class MySharedObject {

		// static variable pointing to instance of MySharedObject

		public static final MySharedObject sharedInstance = new MySharedObject();

		// member variables pointing to two objects on the heap

		public Integer object2 = new Integer(22);
		public Integer object4 = new Integer(44);

		public long member1 = 12345;
		public long member2 = 67890;
	}

	@Override
	public void implementionOne(String[] args) throws Exception {
		// Thread t1 = new Thread(new MyRunnable());
		// t1.start();
		// MyRunnable ob = new MyRunnable();
		// Thread t2 = new Thread(ob);
		// t2.start();

	}

	@Override
	public void implementionTwo(String[] args) throws Exception {
		logger.debug("call");
		Thread t1 = new Thread(new MyObjectRunnable());
		t1.start();
		MyObjectRunnable ob = new MyObjectRunnable();
		Thread t2 = new Thread(ob);
		t2.start();
		Thread t3 = new Thread(ob);
		t3.start();

	}

	@Override
	public void implementionThree(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionFour(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionFive(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionSix(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionSeven(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionEight(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionNine(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionTen(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

}

class MyObjectRunnable implements Runnable {
	protected static Logger logger = Logger.getLogger(MyObjectRunnable.class
			.getName());
	MyObject m = new MyObject();
	MyObject m2 = new MyObject();

	public void run() {
		logger.debug(this);
		mInMethod();
	}

	public void mInMethod() {
		MyObject m3 = new MyObject();
		MyObject m4 = new MyObject();
		logger.debug("m3:\t" + m3);
		logger.debug("m4:\t" + m4);
		logger.debug("m:\t" + m);
		logger.debug("m2:\t" + m2);
	}

}

class MyObject {
	private int intM;
	private Object obj;

	public MyObject() {
	}

	public int getIntM() {
		return intM;
	}

	public void setIntM(int intM) {
		this.intM = intM;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
