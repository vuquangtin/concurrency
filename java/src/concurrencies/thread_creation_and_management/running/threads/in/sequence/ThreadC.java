package running.threads.in.sequence;

public class ThreadC extends Thread{

	ResourceLock lock;

	ThreadC(ResourceLock lock){
		this.lock = lock;
	}

	@Override
	public void run() {

		try{
			synchronized (lock) {

				for (int i = 0; i < 100; i++) {

					while(lock.flag!=3){
						lock.wait();
					}

					System.out.print("C ");
					Thread.sleep(1000);
					lock.flag = 1;
					lock.notifyAll();
				}

			}
		}catch (Exception e) {
			System.out.println("Exception 3 :"+e.getMessage());
		}

	}
}