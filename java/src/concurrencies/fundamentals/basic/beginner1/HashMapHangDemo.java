package basic.beginner1;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Jerry Lee (oldratlee at gmail dot com)
 * @see <a href="http://coolshell.cn/articles/9606.html">Java HashMap的死循环</a>@<a
 *      href="http://weibo.com/haoel">左耳朵耗子</a>
 * @see https
 *      ://github.com/oldratlee/fucking-java-concurrency#beer-%E6%97%A0%E5%90
 *      %8C%
 *      E6%AD%A5%E7%9A%84%E4%BF%AE%E6%94%B9%E5%9C%A8%E5%8F%A6%E4%B8%80%E4%B8%
 *      AA%E7%BA%BF%E7%A8%8B%E4%B8%AD%E4%BC%9A%E8%AF%BB%E4%B8%8D%E5%88%B0
 */
public class HashMapHangDemo {
	final Map<Integer, Object> holder = new HashMap<Integer, Object>();

	public static void main(String[] args) {
		HashMapHangDemo demo = new HashMapHangDemo();
		for (int i = 0; i < 100; i++) {
			demo.holder.put(i, null);
		}

		Thread thread = new Thread(demo.getConcurrencyCheckTask());
		thread.start();
		thread = new Thread(demo.getConcurrencyCheckTask());
		thread.start();

		System.out.println("Start get in main!");
		for (int i = 0;; ++i) {
			for (int j = 0; j < 10000; ++j) {
				demo.holder.get(j);

				// 如果出现hashmap的get hang住问题，则下面的输出就不会再出现了。
				// 在我的开发机上，很容易在第一轮就观察到这个问题。
				System.out.printf("Got key %s in round %s\n", j, i);
			}
		}
	}

	ConcurrencyTask getConcurrencyCheckTask() {
		return new ConcurrencyTask();
	}

	private class ConcurrencyTask implements Runnable {
		Random random = new Random();

		@Override
		public void run() {
			System.out.println("Add loop started in task!");
			while (true) {
				holder.put(random.nextInt() % (1024 * 1024 * 100), null);
			}
		}
	}
}