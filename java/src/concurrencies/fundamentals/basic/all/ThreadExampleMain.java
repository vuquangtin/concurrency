package basic.all;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadExampleMain {

	static boolean flagNeedContinue = false;
	private static int processors;

	public static void main(String... args) throws InterruptedException,
			ExecutionException {

		processors = Runtime.getRuntime().availableProcessors();

		stateExample();
		priorityExample();
		joinExample();
		yieldExample(true);
		yieldExample(false);
		System.out.println("********************************");
		System.out.println("Число процессоров (ядер) = " + processors);
		System.out.println("********************************");
		poolExecutorsExample(1);
		poolExecutorsExample(2);
		poolExecutorsExample(processors);
		poolExecutorsExample(processors * 2);

		testDeadLock();
		strangeLockExample();
		liveLockExample();
		starvationExample();

	}

	private static void stateExample() throws InterruptedException {
		System.out.println("********************************");

		System.out.println("Thread STATE Example:");

		ThreadExample thread1 = new ThreadExample();
		thread1.printInfo();
		thread1.start();
		Thread.sleep(10);
		thread1.setPriority(Thread.MAX_PRIORITY);
		thread1.printInfo();
		thread1.setMustBeWait(true);
		Thread.sleep(10);
		thread1.printInfo();
		thread1.setMustBeWait(false);
		Thread.sleep(10);
		thread1.printInfo();
		thread1.setMustBeRun(false);
		Thread.sleep(10);
		thread1.printInfo();
	} // stateExample()

	private static void priorityExample() throws InterruptedException {
		System.out.println("********************************");
		System.out.println("Thread PRIORITY Example:");

		int MAX_PRIORITIES = 3, MAX_THREADS = 2;

		// запускаем всего 6 потоков по 2 с приоритетами мин., норм, и макс.
		ThreadExample[][] threads = new ThreadExample[MAX_PRIORITIES][MAX_THREADS];
		for (int i = 0; i < MAX_PRIORITIES; i++) {
			for (int j = 0; j < MAX_THREADS; j++) {
				if (i == 0) {
					threads[i][j] = new ThreadExample("Thread_MIN_"
							+ Integer.toString(j));

					threads[i][j].setPriority(Thread.MIN_PRIORITY);
				} else {
					if (i == 2) {
						threads[i][j] = new ThreadExample("Thread_MAX_"
								+ Integer.toString(j));
						threads[i][j].setPriority(Thread.MAX_PRIORITY);
					} else {
						threads[i][j] = new ThreadExample("Thread_NORM_"
								+ Integer.toString(j));
					}

				}
			}
		}

		for (int i = 0; i < MAX_PRIORITIES; i++)
			for (int j = 0; j < MAX_THREADS; j++)
				threads[i][j].printInfo();

		// Стартуем потоки
		for (int i = 0; i < MAX_PRIORITIES; i++)
			for (int j = 0; j < MAX_THREADS; j++)
				threads[i][j].start();

		for (int i = 0; i < MAX_PRIORITIES; i++)
			for (int j = 0; j < MAX_THREADS; j++)
				threads[i][j].printInfo();

		// Потоки начинают выдавать сообщения
		for (int i = 0; i < MAX_PRIORITIES; i++)
			for (int j = 0; j < MAX_THREADS; j++)
				threads[i][j].setNeedSay(true);
		// Thread.sleep(2);

		for (int i = 0; i < MAX_PRIORITIES; i++)
			for (int j = 0; j < MAX_THREADS; j++)
				threads[i][j].printInfo();

		ThreadExample.setMess("NEW MESSAGE");
		Thread.sleep(2);

		for (int i = 0; i < MAX_PRIORITIES; i++)
			for (int j = 0; j < MAX_THREADS; j++)
				threads[i][j].setMustBeRun(false);

		/*
		 * for (int i = 0; i < MAX_PRIORITIES; i++) for(int j =
		 * 0;j<MAX_THREADS;j++) threads[i][j].printInfo();
		 */

		// Thread.sleep(7);

		for (int i = 0; i < MAX_PRIORITIES; i++)
			for (int j = 0; j < MAX_THREADS; j++)
				threads[i][j].printInfo();

	} // priorityExample

	private static void joinExample() throws InterruptedException {
		System.out.println("********************************");
		System.out.println("Thread JOIN Example:");

		System.out.println("Новый поток:");
		ThreadExample threadExample = new ThreadExample();
		threadExample.printInfo();

		threadExample.start();
		Thread.sleep(50);
		threadExample.printInfo();

		System.out.println("Ждём завершения потока 2 сек.:");
		threadExample.join(2000);

		System.out.println("Удивительно, но... ");
		threadExample.printInfo();
		System.out.println("А сейчас... ");
		threadExample.setMustBeRun(false);
		threadExample.join();

		threadExample.printInfo();
	}

	private static void yieldExample(boolean useYield)
			throws InterruptedException {
		long start, finish;
		start = System.currentTimeMillis();
		System.out.println("********************************");
		System.out.println("Thread Example with YIELD = " + useYield);

		/*
		 * threadsCount - количество одновременно выполняемых нитей - на моём
		 * компьютере показательно значение 4, думаю, что это зависит и от
		 * количества ядер процессора/поддерживаемых потоков, и значительно
		 * увеличение этого значения "смазывает" результат по времени исполнения
		 */

		int threadsCount = processors;

		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		ThreadExample threadExample[] = new ThreadExample[threadsCount];
		SetContinue setContinue = new SetContinue("Continue Setter");
		setContinue.setPriority(1);

		for (int i = 0; i < threadsCount; i++) {
			threadExample[i] = new ThreadExample("Thread Example "
					+ Integer.toString(i));

			/*
			 * Следующая строчка программы очень влияет на время выполнения на
			 * моём компьютере
			 */
			threadExample[i].setNeedYield(useYield);
			threadExample[i].setPriority(10);
			threadExample[i].start();

		}

		setContinue.start();

		while (!flagNeedContinue)
			;

		System.out.println("Yield example continue...");

		for (int i = 0; i < threadsCount; i++)
			threadExample[i].setMustBeRun(false);

		for (int i = 0; i < threadsCount; i++)
			threadExample[i].join();

		setContinue.join();

		finish = System.currentTimeMillis();

		System.out.println("Время выполнения программы = " + (finish - start));

	} // yieldExample()

	/* Для демонстрации yieldExample */
	private static class SetContinue extends ThreadExample {

		SetContinue() {
		}

		SetContinue(String name) {
			super(name);
		}

		@Override
		public void run() {
			try {
				sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Set continue = true");
			flagNeedContinue = true;
		}
	} // class SetContinue

	private static void poolExecutorsExample(int nThreads)
			throws ExecutionException, InterruptedException {
		System.out.println("********************************");
		System.out
				.println("Пример работы екзекьюторов и пула процессов, размер пула = "
						+ nThreads);

		ExecutorService executorService = Executors
				.newFixedThreadPool(nThreads);
		Calculate calculate = new Calculate();

		// для хранения "промежуточных результатов расчета
		List<Future<Double>> intermediateResults = new ArrayList<>();
		Long start = System.currentTimeMillis();

		for (int i = 0; i < 400; i++) {
			final int j = i;
			intermediateResults.add(CompletableFuture.supplyAsync(
					() -> calculate.calculate(new Double(j)), executorService));
		}

		Double result = 0.0;
		for (Future<Double> iResult : intermediateResults)
			result += iResult.get();

		Long finish = System.currentTimeMillis();
		System.out.println("Время вычислений: " + (finish - start)
				+ " результат = " + result);
		executorService.shutdown();

	} // poolsExecutorsExample()

	// Класс для демонстрации работы poolsExecutorsExample
	private static class Calculate {
		// Нагрузим процессор вычислениями
		public Double calculate(Double number) {
			for (int i = 0; i < 400_000; i++)
				number += Math.sin(number);
			return number;
		}

	}

	static class A {
		static final B b = new B();
	}

	static class B {
		static final A a = new A();
	}

	private static void testDeadLock() {
		System.out.println("********************************");
		System.out.println("Deadlock Example");
		// Если закомментировать следующую строчку
		// приложение блокируется
		new B();
		new Thread(A::new).start();
		new B();
	}

	private static class StreamSum {

		// НЕ РАБОТАЮЩИЙ ВАРИАНТ - лямбда код
		// - public static final int SUM = IntStream.range(0,
		// 100).parallel().reduce((n, m) -> n + m).getAsInt();

		// РАБОТАЮЩИЙ ВАРИАНТ 1 (убрали parallel())
		static final int SUM = IntStream.range(0, 100).reduce((n, m) -> n + m)
				.getAsInt();

		// РАБОТАЮЩИЙ ВАРИАНТ 2 (лямбда код заменили на "Integer::sum")
		// public static final int SUM = IntStream.range(0,
		// 100).parallel().reduce(Integer::sum).getAsInt();

		void printResult() {
			System.out.println(SUM);
		}
	}

	private static void strangeLockExample() {
		System.out.println("********************************");
		System.out.println("Странная блокировка (strangeLockExample)");
		StreamSum streamSum = new StreamSum();
		streamSum.printResult();
	}

	private static class LiveLock {

		static class Resource {
			private User user;

			Resource(User user) {
				this.user = user;

			}

			User getUser() {
				return this.user;
			}

			synchronized void setUser(User user) {
				this.user = user;
			}

			synchronized void use() {
				System.out.println(user.name + "used resource");
			}

		} // class Resource

		static class User {
			String name;
			boolean usedResource;

			User(String name) {
				this.name = name;
				this.usedResource = false;
			}

			String getName() {
				return name;
			}

			boolean isUsedResource() {
				return usedResource;
			}

			void setUsedResource(boolean usedResource) {
				this.usedResource = usedResource;
			}

			void jointUse(Resource resource, User user) {
				while (!usedResource) {
					if (resource.getUser() != this) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							continue;
						}
						continue;
					}

					if (!user.isUsedResource()) {
						System.out.println(this.getName() + " ждёт, пока "
								+ user.getName() + " использует ресурс");
						resource.setUser(user);
						continue;
					}

					resource.use();
					usedResource = true;
					System.out.println(this.getName() + " обработал ресурс!");
					resource.setUser(user);
				}

			}
		}
	} // class LiveLock

	private static void liveLockExample() throws InterruptedException {
		System.out.println("********************************");
		System.out.println("Пример livelock (liveLockExample)");

		final LiveLock.User user1 = new LiveLock.User("Пользователь № 1");
		final LiveLock.User user2 = new LiveLock.User("Пользователь № 2");

		final LiveLock.Resource resource = new LiveLock.Resource(user1);

		new Thread(() -> user1.jointUse(resource, user2)).start();

		new Thread(() -> user2.jointUse(resource, user1)).start();

		Thread.sleep(10);

		System.out.println("Прерываем Livelock !!!");
		user2.setUsedResource(true);
		user1.setUsedResource(true);

	}

	public static class StarvationDemo {
		private static final Object sharedObj = new Object();

		void demoStart() {
			JFrame frame = createFrame();
			frame.setLayout(new FlowLayout(FlowLayout.LEFT));

			for (int i = 0; i < 5; i++) {
				ProgressThread progressThread = new ProgressThread();
				frame.add(progressThread.getProgressComponent());
				progressThread.start();
			}

			JButton btnExit = new JButton("Exit");

			btnExit.addActionListener(e -> { // если нажата кнопка

				/** Выходим */
				frame.removeAll();
				frame.dispose(); // чистим память
				System.exit(0);
			}); // ----------- Кнопка < Exit >

			frame.add(btnExit);

			frame.setLocationRelativeTo(null);
			frame.setAlwaysOnTop(true);
			frame.setVisible(true);
		}

		private static JFrame createFrame() {
			JFrame frame = new JFrame("Starvation Demo");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(new Dimension(300, 200));
			return frame;
		}

		private static class ProgressThread extends Thread {
			JProgressBar progressBar;

			ProgressThread() {
				progressBar = new JProgressBar();
				progressBar.setString(this.getName());
				progressBar.setStringPainted(true);
			}

			JComponent getProgressComponent() {
				return progressBar;
			}

			@Override
			public void run() {

				int c = 0;
				while (true) {
					synchronized (sharedObj) {
						if (c == 100) {
							c = 0;
						}
						progressBar.setValue(++c);
						try {
							// sleep the thread to simulate long running task
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	private static void starvationExample() {
		System.out.println("********************************");
		System.out.println("Пример starvation ");

		StarvationDemo starvationDemo = new StarvationDemo();

		starvationDemo.demoStart();
	}

	public static class ThreadExample extends Thread {
		private boolean mustBeRun = true;

		private boolean mustBeWait = false;

		private boolean needSay = false;

		public boolean isNeedYield() {
			return needYield;
		}

		void setNeedYield(boolean needYield) {
			this.needYield = needYield;
		}

		private boolean needYield = false;

		private static String message = "Start message";

		ThreadExample() {
		}

		ThreadExample(String name) {
			super(name);
		}

		static void setMess(String mess) {
			System.out.println(Thread.currentThread().getName()
					+ " set message = " + mess);
			message = mess;
		}

		public void setMessage(String message) {
			System.out.println(this.getName() + " setMessage = " + message);
			this.message = message;
		}

		private void say() {
			System.out.println("I am " + this.getName() + " say: " + message);
		}

		void setMustBeRun(boolean mustBeRun) {
			System.out.println(this.getName() + ".mustBeRun = " + mustBeRun);
			this.mustBeRun = mustBeRun;
		}

		void setMustBeWait(boolean mustBeWait) {
			System.out.println(this.getName() + ".mustBeWait = " + mustBeWait);

			this.mustBeWait = mustBeWait;
		}

		void setNeedSay(boolean needSay) {
			System.out.println(this.getName() + ".needSay = " + needSay);

			this.needSay = needSay;
		}

		@Override
		public void start() {
			super.start();
			System.out.println("Start " + this.getName() + " priority = "
					+ this.getPriority());
		}

		@Override
		public void run() {
			while (mustBeRun) {
				if (mustBeWait) {
					try {
						Thread.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					if (!needYield) {
						if (needSay) {
							say();
						}
					} else {
						Thread.yield();
					}
				}
			}
		}

		void printInfo() {
			System.out.println(this.getName() + " priority = "
					+ this.getPriority() + " state = " + this.getState());
		}
	}
}