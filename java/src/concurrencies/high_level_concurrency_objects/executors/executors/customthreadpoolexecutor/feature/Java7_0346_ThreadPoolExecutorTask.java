package executors.customthreadpoolexecutor.feature;

import java.util.concurrent.TimeUnit;

class Java7_0346_ThreadPoolExecutorTask implements Runnable {
	private String name;

	public Java7_0346_ThreadPoolExecutorTask(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void run() {
		try {
			System.out.println(name + " is completed");
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
