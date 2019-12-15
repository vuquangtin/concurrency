package running.threads.in.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * or you can use Executor Framework
 * 
 * @author admin
 *
 */
public class SequenceUseExecutor {
	int i = 1;

	public static void main(String[] args) {
		SequenceUseExecutor s = new SequenceUseExecutor();
		Common c1 = new Common(s, 1);
		Common c2 = new Common(s, 2);
		Common c3 = new Common(s, 3);

		List<Runnable> l = new ArrayList<>();
		l.add(c1);
		l.add(c2);
		l.add(c3);

		ExecutorService es = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 3; i++) {
			es.submit(l.get(i));
		}
		es.shutdown();
	}
}

class Common implements Runnable {
	SequenceUseExecutor s;
	int o;

	Common(SequenceUseExecutor s, int o) {
		this.s = s;
		this.o = o;
	}

	public void run() {
		synchronized (s) {
			for (int z = 0; z < 100; z++) {
				if (s.i > 3)
					s.i = 1;
				while (s.i != o) {
					try {
						s.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(o);
				s.i++;
				s.notifyAll();
			}
		}
	}
}