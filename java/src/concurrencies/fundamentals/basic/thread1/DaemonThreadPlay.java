package basic.thread1;

/**
 * How does the JVM terminate daemon threads? or How to write daemon threads
 * that terminate gracefully
 * -------------------------------------------------------------------------
 * 
 * 
 * Hypothetical scenario: I have a daemon thread responsible for some I/O, the
 * main thread finishes and returns, and the JVM decides to terminate my daemon
 * thread.
 * 
 * How does it do so? Interrupt? Finalize? How can I code my daemon thread so
 * that it reacts gracefully when terminated?
 * 
 * 
 * @author vuquangtin
 *
 */
public class DaemonThreadPlay {
	public static void main(String[] args) {
		Thread daemonThread = new Thread() {
			public void run() {
				while (true) {
					try {
						System.out.println("Try block executed");
						Thread.sleep(1000l);
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			}

			@Override
			public void finalize() {
				System.out.println("Finalize method called");
			}
		};
		daemonThread.setDaemon(true);
		daemonThread.start();

		try {
			Thread.sleep(2500l);
		} catch (Throwable t) {
			// NO-OP
		}
	}
}
