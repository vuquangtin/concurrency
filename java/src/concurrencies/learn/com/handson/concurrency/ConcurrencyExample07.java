/**
 * 
 */
package com.handson.concurrency;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample07 {

	/*
	 * Example on Wait,notify IllegalMonitorStateException.In this example
	 * ProducerConsumerSharedObject class methods are not synchronized. So, calling
	 * wait / notify methods on unlocked object will through
	 * IllegalMonitorException. In order to invoke wait/notify on an Object , first
	 * we need to acquire lock on that Object using synchronized block/method.
	 */
	public static void main(String[] args) {

		ProducerConsumerSharedObject sharedObject = new ProducerConsumerSharedObject();
		Thread producerThread = new Thread(() -> {
			try {
				sharedObject.setSharedValue(String.valueOf(1));
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		producerThread.start();
		Thread consumerThread = new Thread(() -> {
			try {
				System.out.println("Shared Object value is " + sharedObject.getSharedValue());
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		consumerThread.start();

	}

	private static class ProducerConsumerSharedObject {

		private String sharedValue;

		public String getSharedValue() throws InterruptedException {
			while (sharedValue == null)
				wait();
			String valuetoBeRetuned = sharedValue;
			sharedValue = null;
			notify();
			return valuetoBeRetuned;
		}

		public void setSharedValue(String sharedValue) throws InterruptedException {
			while (this.sharedValue != null)
				wait();
			this.sharedValue = sharedValue;
			notify();
		}

		@Override
		public String toString() {
			return "SharedObject [sharedValue=" + sharedValue + "]";
		}

	}

}
