package basic.jmm;

import java.util.Random;

/**
 * Example1.
 */
public class Example1 {

	/**
	 * @param args
	 *            args.
	 */
	public static void main(String[] args) {
		new Thread(new MyRunnable()).start();
		new Thread(new MyRunnable()).start();
	}

	/**
	 * MyRunnable.
	 */
	private static class MyRunnable implements Runnable {
		/**
		 * Run.
		 */
		@Override
		public void run() {
			this.methodOne();
		}

		/**
		 * Method one.
		 */
		public void methodOne() {
			int localVar = 45;
			MySharedObject localVar2 = MySharedObject.SHARED_INSTANCE;
			localVar2.obj2 +=new Random().nextInt() ;
			localVar+=new Random().nextInt() ;
			System.out.println(localVar2);
			System.out.println(localVar);
			MySharedObject2 localVar3 =new  MySharedObject2();
			localVar3.obj2 +=new Random().nextInt() ;
			System.out.println(localVar3);
			methodTwo();
		}

		/**
		 * Method two.
		 */
		public void methodTwo() {
			Integer localVar = 99;
		}
	}

	/**
	 * MySharedObject.
	 */
	private static class MySharedObject {
		// Points to instance of MySharedObject.
		public static final MySharedObject SHARED_INSTANCE = new MySharedObject();

		public Integer obj2 = 10;
		public Integer obj4 = 44;

		public long member1 = 12345;
		public long member2 = 54321;

		/**
		 * @return string.
		 */
		@Override
		public String toString() {
			return "MySharedObject{" + "obj2=" + obj2 + ", obj4=" + obj4 + ", member1=" + member1 + ", member2="
					+ member2 + '}';
		}
	}
	private static class MySharedObject2 {
		// Points to instance of MySharedObject.
		//public static final MySharedObject SHARED_INSTANCE = new MySharedObject();

		public Integer obj2 = 10;
		public Integer obj4 = 44;

		public long member1 = 12345;
		public long member2 = 54321;

		/**
		 * @return string.
		 */
		@Override
		public String toString() {
			return "MySharedObject2{" + "obj2=" + obj2 + ", obj4=" + obj4 + ", member1=" + member1 + ", member2="
					+ member2 + '}';
		}
	}
}