package kills;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
 
/**
 * @author Crunchify.com 
 * Version: 1.0.1
 * 
 */
 
public class CrunchifyRunner implements Callable<Boolean> {
 
	private int workerNumber;
 
	public int getNumber() {
		return workerNumber;
	}
 
	public void setNumber(int workerNumber) {
		this.workerNumber = workerNumber;
	}
 
	public CrunchifyRunner(int workerNumber) {
		this.workerNumber = workerNumber;
		setNumber(workerNumber);
	}
 
	SimpleDateFormat crunchifyFormatter = new SimpleDateFormat("dd-MMMMM-yyyy hh:mm:ss");
 
	public Boolean call() throws InterruptedException {
		try {
			if (workerNumber == 4) {
 
				// Sleep for 20 Seconds to generate long running thread.
				Thread.sleep(20000);
			} else {
				Thread.sleep(50);
			}
		} catch (InterruptedException ie) {
			log("\n" + crunchifyFormatter.format(new Date()) + " crunchifyWorker task " + workerNumber + " interrupted.");
			log("\n=======> Basically once thread is timed out, it should be cancelled and interrupted. (timedout ==> cancelled ==> interrupted)");
		}
 
		// Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity. Occasionally a
		// method may wish to test whether the current thread has been interrupted, and if so, to immediately throw this exception.
		return true;
	}
 
	public void log(String info) {
		System.out.println(info);
 
	}
}