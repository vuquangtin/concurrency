package volatilekeyword;

public class SimpleExampleSpuriouslyFixed {
	public static class Looper extends Thread {
		boolean finish = false;

		@Override
		public void run() {
			while (!finish) {
				System.out.println("finish is " + true);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Looper looper = new Looper();
		looper.start();
		Thread.sleep(1000); // wait 1s
		looper.finish = true;
		System.out.println("Wait for Looper to terminate...");
		looper.join();
		System.out.println("Done.");
	}
}
