package com.rxjava3.schedulers.threads;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Rx_01_00_Scheduler_NewThread {
	static Logger logger = Logger.getLogger(Rx_01_00_Scheduler_NewThread.class
			.getName());

	public void newSchedulerStart(String[] objs) {
		schedulerSubscribe(observableSet(objs, "newThread() : A"));
		schedulerSubscribe(observableSet(objs, "newThread() : B"));

	}

	public Observable<String> observableSet(String[] objs, String title) {

		return Observable.fromArray(objs).map(data -> title + " - " + data);
	}

	// Schedulers.newThread()
	public void schedulerSubscribe(Observable<String> scheduler) {
		ExecutorService executors = Executors.newFixedThreadPool(1);
		Scheduler scheduler2 = Schedulers.from(executors);
		scheduler.subscribeOn(scheduler2).subscribe(str -> {
			for (int i = 0; i < 2; i++) {
				logger.info(str);
				System.out.println(str);
				Thread.sleep(100);
			}

		});
		executors.shutdown();
	}

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		Rx_01_00_Scheduler_NewThread test = new Rx_01_00_Scheduler_NewThread();
		String[] objs = { "RED", "GREEN", "BLUE" };

		test.newSchedulerStart(objs);
	}
}