package com.thread.shutdownhook;

import java.io.IOException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Shutdownhook {

	public static void main(String[] args) throws IOException {

		// this is the shutdown hook
		// looks like we can have multiple shutdown hooks as well
		// and they run in no particular order
		// they just run
		// when are they called?? they are called right after the lasst line in
		// the program executes....

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutdown hook 1 is running");
			}
		});
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutdown hook 2 is running");
			}
		});
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutdown hook 3 is running");
			}
		});
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutdown hook 4 is running");
			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutdown Hook 5 is running !");
			}
		});
		/*
		 * System.out.println("Application Terminating ..."); try {
		 * Thread.sleep(10000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		// Process process = Runtime.getRuntime().exec("google-chrome");
		// System.out.println("Google Chrome successfully started");
		Runtime.getRuntime().traceInstructions(true);
		System.out.println(Runtime.getRuntime().freeMemory()); // -> will give
																// JVM's avail
																// free mem
		System.out.println(Runtime.getRuntime().totalMemory());
		System.out.println(Runtime.getRuntime().availableProcessors());

	}
}
