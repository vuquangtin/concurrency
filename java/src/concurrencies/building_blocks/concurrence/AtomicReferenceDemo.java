package concurrence;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class AtomicReferenceDemo {

	static String[] names = { "tom", "jack", "lucy" };
	static Baoshu bs = new Baoshu();

	public static void main(String[] args) throws InterruptedException {
		for (String name : names) {
			new Thread(() -> {
				Baoshu newbs = new Baoshu();
				newbs.name = name;
				newbs.num = bs.num + 1;
				bs = newbs;
				System.out.println(bs);
			}).start();
		}
		Thread.sleep(3000);
	}

	static class Baoshu {

		public String name;
		public int num = 0;

		public String toString() {
			return name + " : " + num;
		}
	}
}
