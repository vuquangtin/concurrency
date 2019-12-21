package kills;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
 
/**
 * @author Crunchify.com 
 * Version: 1.0.2
 * 
 */
 
public class CrunchifyJavaTaskTimeout {
 
	@SuppressWarnings({ "rawtypes", "unchecked" })
 
	public static void main(final String[] args) {
 
		// Creates an ExecutorCompletionService using the supplied executor for base task execution and a LinkedBlockingQueue as a completion queue.
		CompletionService<Boolean> crunchifyService = new ExecutorCompletionService<Boolean>(Executors.newFixedThreadPool(1));
 
		Collection<CrunchifyRunner> crunchifyThreads = new ArrayList<CrunchifyRunner>(50);
 
		crunchifyThreads.add(new CrunchifyRunner(1));
		crunchifyThreads.add(new CrunchifyRunner(2));
		crunchifyThreads.add(new CrunchifyRunner(3));
		crunchifyThreads.add(new CrunchifyRunner(4));
 
		SimpleDateFormat crunchifyFormatter = new SimpleDateFormat("dd-MMMMM-yyyy hh:mm:ss");
 
		List<Future<Boolean>> crunchifyFutures = new ArrayList<Future<Boolean>>(crunchifyThreads.size());
		try {
 
			// Let's first add all tasks to future
			for (Callable crunchifyTask : crunchifyThreads) {
 
				crunchifyFutures.add(crunchifyService.submit(crunchifyTask));
			}
			for (int count = 1; count <= crunchifyFutures.size(); count++) {
 
				// Let's put Future timeout to 3 Seconds
				Future<Boolean> crunchifyResult = crunchifyService.poll(3000, TimeUnit.MILLISECONDS);
				if (crunchifyResult == null) {
 
					log(crunchifyFormatter.format(new Date()) + "\n ==> crunchifyWorker task " + count + " timedout.");
 
					// So lets cancel the first futures we find that haven't completed
					for (Future crunchifyFuture : crunchifyFutures) {
						if (crunchifyFuture.isDone()) {
							continue;
						} else {
							crunchifyFuture.cancel(true);
							log(" ==> crunchifyWorker task " + count +" cancelled.");
							break;
						}
					}
					continue;
				} else {
					try {
						if (crunchifyResult.isDone() && !crunchifyResult.isCancelled() && crunchifyResult.get()) {
							log(crunchifyFormatter.format(new Date()) + " ==> crunchifyWorker task " + count + " completed.");
 
						} else {
							log(crunchifyFormatter.format(new Date()) + " ==> crunchifyWorker task failed");
						}
					} catch (ExecutionException exception) {
						log(exception.getMessage());
					}
				}
			}
		} catch (InterruptedException exception) {
 
			// Log exception message
			log(exception.getMessage());
		} finally {
 
			// Cancel by interrupting any existing tasks currently running in Executor Service
			for (Future<Boolean> future : crunchifyFutures) {
				future.cancel(true);
			}
		}
		log("\n=======> All tasks completed. Now long running thread 4 should be interrupted immediately after this.");
		System.exit(0);
	}
 
	private static void log(String string) {
		System.out.println(string);
 
	}
 
}