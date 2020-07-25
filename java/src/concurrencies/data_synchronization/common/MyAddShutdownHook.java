package common;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyAddShutdownHook {

	public static void main(String[] args) {
		Object o = new Object() {
			@Override
			protected void finalize() {
				// 一旦垃圾收集器准备好释放对象占用的存储空间，它首先调用finalize()
				System.out.println("running finalize......");
			}
		};

		Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("running shutdown hook....")));

		o = null;
		System.gc();

		System.out.println("Calling system exit");
		System.exit(0);
	}
}