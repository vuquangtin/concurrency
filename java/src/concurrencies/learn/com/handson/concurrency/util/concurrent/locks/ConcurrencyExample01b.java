/**
 * 
 */
package com.handson.concurrency.util.concurrent.locks;

import static java.lang.System.out;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample01b {

	/*
	 * Example on Lock , using conditions on Locks which behaves like wait and
	 * notify signals on Threads. Trying to invoke await() on Condition variable
	 * without acquiring lock will throw IllegalMonitorStateException. This scenario
	 * is similar to invoking wait() on object "without acquiring lock" in
	 * synchronized block/method which throws IllegalMonitorStateException. This
	 * concept is similar to Mutex and Conditional Variables in Operating System.
	 */

	private static final int MAX_PRODUCABLE_VALUES = 120;

	private static enum Group {
		ECE(0), CSE(1), EEE(2);

		Group(int groupId) {
			this.groupId = groupId;
		}

		private final int groupId;

		public static Group getGroupById(int id) {
			return id == ECE.groupId ? ECE : id == CSE.groupId ? CSE : EEE;
		}

	}

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(4);
		SharedGroupObject sharedGroupObject = new SharedGroupObject();
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		ConcurrentMap<Group, Integer> consumedGroupCount = new ConcurrentHashMap<>();

		executorService.submit(constructAnyGroupValueProducer(sharedGroupObject, countDownLatch));
		executorService
				.submit(constructGroupValueConsumer(sharedGroupObject, countDownLatch, Group.ECE, consumedGroupCount));
		executorService
				.submit(constructGroupValueConsumer(sharedGroupObject, countDownLatch, Group.CSE, consumedGroupCount));
		executorService
				.submit(constructGroupValueConsumer(sharedGroupObject, countDownLatch, Group.EEE, consumedGroupCount));
		countDownLatch.await();
		consumedGroupCount.forEach(
				(group, groupCount) -> out.println("Total Consumed values for group " + group + " is " + groupCount));
		out.println("Total Values consumed by all consumer threads is "
				+ consumedGroupCount.entrySet().stream().map(Map.Entry::getValue).reduce(0, Integer::sum));
		executorService.shutdown();

	}

	private static Callable<Void> constructAnyGroupValueProducer(SharedGroupObject sharedGroupObject,
			CountDownLatch countDownLatch) {
		return () -> {
			ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
			for (int i = 1; i <= MAX_PRODUCABLE_VALUES; i++) {
				int randomGroupId = threadLocalRandom.nextInt() % 3;
				sharedGroupObject.setGroupValue(Group.getGroupById(randomGroupId), i);
			}
			countDownLatch.countDown();
			return null;
		};
	}

	private static Callable<Void> constructGroupValueConsumer(SharedGroupObject sharedGroupObject,
			CountDownLatch countDownLatch, Group group, ConcurrentMap<Group, Integer> consumedGroupCount)
			throws InterruptedException {
		return () -> {
			while (sharedGroupObject.getTotalConsuedValues() != MAX_PRODUCABLE_VALUES) {
				int consumerValue = sharedGroupObject.getValueOfGroup(group);
				out.println("Consumed Value in " + group + " Thread is " + consumerValue);
				if (consumerValue != -1)
					consumedGroupCount.merge(group, 1, Integer::sum);
				/*
				 * -1 value is used to end the program to reset the blocked consumer
				 * threads,Checkout SharedGroupObject.clearBlockedConsumers() for more details.
				 * So that's why this value is not considered as consumed value.
				 */
			}
			out.println("Completed execution in consumer thread group " + group);
			sharedGroupObject.clearBlockedConsumers();
			out.println("Completed clear BLocked Consumers in " + group);
			countDownLatch.countDown();
			return null;
		};
	}

	private static class SharedGroupObject {

		private ReentrantLock reentrantLock = new ReentrantLock(true);
		private ConcurrentMap<Group, Condition> groupConditionMap = new ConcurrentHashMap<>();
		private Condition producerCondition = reentrantLock.newCondition();
		private Integer sharedValue = null;
		private Group producedForGroup = null;
		private int totalConsuedValues;

		{
			// Instance Block.
			groupConditionMap.putIfAbsent(Group.ECE, reentrantLock.newCondition());
			groupConditionMap.putIfAbsent(Group.CSE, reentrantLock.newCondition());
			groupConditionMap.putIfAbsent(Group.EEE, reentrantLock.newCondition());

		}

		public void setGroupValue(Group group, int value) throws InterruptedException {
			try {
				reentrantLock.lock();
				while (sharedValue != null)
					producerCondition.await();
				sharedValue = value;
				producedForGroup = group;
				++totalConsuedValues;
				groupConditionMap.get(group).signalAll();
			} finally {
				reentrantLock.unlock();
			}
		}

		public int getValueOfGroup(Group group) throws InterruptedException {
			try {
				reentrantLock.lock();
				while (sharedValue == null || (sharedValue != null && producedForGroup != group))
					groupConditionMap.get(group).await();
				int valueToBeReturned = sharedValue;
				sharedValue = null;
				producedForGroup = null;
				producerCondition.signal();
				return valueToBeReturned;
			} finally {
				reentrantLock.unlock();
			}
		}

		public int getTotalConsuedValues() {
			return totalConsuedValues;
		}

		/*
		 * This method is only need to stop the blocked consumer thread's.This method is
		 * complicated because of race between threads.
		 */
		public void clearBlockedConsumers() throws InterruptedException {

			groupConditionMap.forEach((group, condition) -> {
				producedForGroup = group;
				/*
				 * Check If sharedvalue is null, this can happen only when all
				 * MAX_PRODUCABLE_VALUES count values are consumed by thread's and still some
				 * consumer thread's are blocked. sharedValue is not null , this can happen only
				 * due to missed signals.
				 */
				sharedValue = sharedValue == null ? -1 : sharedValue;
				try {
					reentrantLock.lock();
					condition.signalAll();
					reentrantLock.unlock();
					// Sleeping just to allow previously wokeup thread to consume value.
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});

		}

	}

}
