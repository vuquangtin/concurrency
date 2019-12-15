package running.threads.in.sequence;

public class ThreadB extends Thread{

	ResourceLock lock;

	ThreadB(ResourceLock lock){
		this.lock = lock;
	}

	@Override
	public void run() {

		try{
			synchronized (lock) {

				for (int i = 0; i < 100; i++) {

					while(lock.flag!=2){
						lock.wait();
					}

					System.out.print("B ");
					Thread.sleep(1000);
					lock.flag = 3;
					lock.notifyAll();
				}

			}
		}catch (Exception e) {
			System.out.println("Exception 2 :"+e.getMessage());
		}

	}
}