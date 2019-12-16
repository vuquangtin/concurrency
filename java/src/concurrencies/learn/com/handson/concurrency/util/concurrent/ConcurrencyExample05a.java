/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample05a {

	/*
	 * Example on ForkJoin.Example on ForkAndJoin using ForkJoinPool and
	 * RecursiveTask to have Fork and join operation.
	 */
	private static final int ARRAY_LENGTH = 11;

	public static void main(String[] args) {

		ForkJoinPool forkJoinPool = new ForkJoinPool();
		Long[] numbers = new Long[ARRAY_LENGTH];
		for (int i = 1; i <= numbers.length; i++)
			numbers[i - 1] = (long) i;

		ArrayAdderTask arrayAdderTask = new ArrayAdderTask(numbers);
		ForkJoinTask<Long> resulTask = forkJoinPool.submit(arrayAdderTask);
		try {
			Long result = resulTask.get();
			System.out.println("Fork Join Result is " + result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}

	private static class ArrayAdderTask extends RecursiveTask<Long> {

		private static final long serialVersionUID = 1L;
		private final Long[] valuesToBeAdded;

		public ArrayAdderTask(Long[] valuesToBeAdded) {
			super();
			this.valuesToBeAdded = valuesToBeAdded;
		}

		@Override
		protected Long compute() {
			System.out.println("Arrays to be added " + Arrays.toString(valuesToBeAdded));
			if (valuesToBeAdded == null || valuesToBeAdded.length == 0)
				return 0l;
			else if (valuesToBeAdded.length == 1)
				return valuesToBeAdded[0];
			else if (valuesToBeAdded.length == 2)
				return valuesToBeAdded[0] + valuesToBeAdded[1];
			else {
				ArrayAdderTask task1 = new ArrayAdderTask(
						Arrays.copyOfRange(valuesToBeAdded, 0, valuesToBeAdded.length / 2 + 1));
				ArrayAdderTask task2 = new ArrayAdderTask(
						Arrays.copyOfRange(valuesToBeAdded, valuesToBeAdded.length / 2 + 1, valuesToBeAdded.length));
				task1.fork();
				task2.fork();
				return task1.join() + task2.join();
			}

		}

	}

}
