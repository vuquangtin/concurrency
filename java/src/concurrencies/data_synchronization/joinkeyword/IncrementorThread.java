package joinkeyword;

public class IncrementorThread extends Thread {
	public IncrementorThread(int i, IntHolder aHolder) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		for (int i = 0; i < 10; i++) {
			System.out.println(i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
