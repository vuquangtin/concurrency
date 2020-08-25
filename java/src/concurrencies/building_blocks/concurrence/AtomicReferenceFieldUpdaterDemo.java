package concurrence;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class AtomicReferenceFieldUpdaterDemo {

	volatile Baoshu bs = new Baoshu();
	static AtomicReferenceFieldUpdater<AtomicReferenceFieldUpdaterDemo, Baoshu> bsUpdater = AtomicReferenceFieldUpdater
			.newUpdater(AtomicReferenceFieldUpdaterDemo.class, Baoshu.class,
					"bs");

	public static void main(String[] args) throws InterruptedException {
		AtomicReferenceFieldUpdaterDemo demo = new AtomicReferenceFieldUpdaterDemo();
		String[] names = { "tom", "jack", "lucy" };
		for (String name : names) {
			new Thread(() -> {
				for (;;) {
					Baoshu newbs = new Baoshu();
					newbs.name = name;
					newbs.num = bsUpdater.get(demo).num + 1;
					if (bsUpdater.compareAndSet(demo, bsUpdater.get(demo),
							newbs)) {
						System.out.println(newbs.toString());
						break;
					}
				}
			}).start();
		}
		Thread.sleep(3000);
	}

	static class Baoshu {

		volatile String name;
		volatile int num = 0;

		public String toString() {
			return name + " : " + num;
		}
	}
}