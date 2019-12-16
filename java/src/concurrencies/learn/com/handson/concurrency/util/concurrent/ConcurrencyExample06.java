/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import static java.lang.System.out;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample06 {

	/*
	 * Example on ConcurrentHashMap.Compute operation can be used to update the
	 * value in concurrent hash-map atomically.Simillarly, we can use
	 * computeIfAbsent,computeIfPresent and merge operations.Using Merge method is
	 * much more flexible we can substitute method references to remappingFunction
	 * Argument of merge function.There are other utility functions in
	 * ConcurrentHashMap checkout api documentation for more.
	 */

	private static final int MAP_LENGTH = 100;
	private static final int THREAD_COUNT = 10;

	public static void main(String[] args) throws InterruptedException {

		CountDownLatch countDownLatchForComputeMethod = new CountDownLatch(THREAD_COUNT);
		ConcurrentMap<Integer, Integer> numberMapForCouputeMethod = new ConcurrentHashMap<>();
		for (int i = 0; i < THREAD_COUNT; i++)
			new Thread(constructMapMutatorThreadUsingComputeMethod(numberMapForCouputeMethod,
					countDownLatchForComputeMethod)).start();
		countDownLatchForComputeMethod.await();
//		numberMap.forEach((key, value) -> out.println("Key - " + key + " has value - " + value));
		long totalEntryValuesUpdatedProperlyToTen = numberMapForCouputeMethod.entrySet().stream()
				.filter((Map.Entry<Integer, Integer> keyValueEntry) -> keyValueEntry.getValue() == 10).count();
		out.println("Total Entry Values Updated Properly To Ten using compute method is "
				+ totalEntryValuesUpdatedProperlyToTen);

		/* Example on Merge method starts */
		CountDownLatch countDownLatchForMergeMethod = new CountDownLatch(THREAD_COUNT);
		ConcurrentMap<Integer, Integer> numberMapForMergeMethod = new ConcurrentHashMap<>();
		for (int i = 0; i < THREAD_COUNT; i++)
			new Thread(constructMapMutatorThreadUsingMergeMethod(numberMapForMergeMethod, countDownLatchForMergeMethod))
					.start();
		countDownLatchForMergeMethod.await();
		long totalEntryValuesUpdatedProperlyToTenUsingMergeFunction = numberMapForMergeMethod.entrySet().stream()
				.filter((Map.Entry<Integer, Integer> keyValueEntry) -> keyValueEntry.getValue() == 10).count();
		out.println("Total Entry Values Updated Properly To Ten using merge method is "
				+ totalEntryValuesUpdatedProperlyToTenUsingMergeFunction);

	}

	private static Runnable constructMapMutatorThreadUsingComputeMethod(
			ConcurrentMap<? super Integer, Integer> concurrentMap, CountDownLatch countDownLatch) {
		Runnable mapMutatorThread = () -> {
			for (int i = 1; i <= MAP_LENGTH; i++)
				concurrentMap.compute(i, (key, value) -> value == null ? 1 : value + 1);
			countDownLatch.countDown();
		};
		return mapMutatorThread;

	}

	private static Runnable constructMapMutatorThreadUsingMergeMethod(
			ConcurrentMap<? super Integer, Integer> concurrentMap, CountDownLatch countDownLatch) {
		Runnable mapMutatorThread = () -> {
			for (int i = 1; i <= MAP_LENGTH; i++)
				concurrentMap.merge(i, 1, Integer::sum);
			countDownLatch.countDown();
		};
		return mapMutatorThread;

	}

}
