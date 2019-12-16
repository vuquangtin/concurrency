/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author sveera
 *
 */
@Deprecated
public class ConcurrencyExample05b {

	/*
	 * This program is not working as expected.This program needs to be revisited
	 * again.
	 */

	/*
	 * Example on ForkJoin.Example on ForkAndJoin using RecursiveAction to have Fork
	 * and join operation.
	 */
	private static final int ARRAY_LENGTH = 11;

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ForkJoinPool forkJoinPool = new ForkJoinPool();
		Long[] numbers = new Long[ARRAY_LENGTH];
		for (int i = 1; i <= numbers.length; i++)
			numbers[i - 1] = (long) i;

		ArrayPrinterAction arrayAdderTask = new ArrayPrinterAction(numbers);
		forkJoinPool.invoke(arrayAdderTask);
		arrayAdderTask.get();
	}

	private static class ArrayPrinterAction extends RecursiveAction {

		private static final long serialVersionUID = 1L;
		private final Long[] valuesToBeAdded;

		public ArrayPrinterAction(Long[] valuesToBeAdded) {
			super();
			this.valuesToBeAdded = valuesToBeAdded;
		}

		@Override
		protected void compute() {

			System.out.println("Arrays to be printed " + Arrays.toString(valuesToBeAdded));

			if (valuesToBeAdded == null || valuesToBeAdded.length == 0) {
				System.out.println("Null or Empty Array");
				return;
			} else if (valuesToBeAdded.length == 1) {
				System.out.println(valuesToBeAdded[0]);
				return;
			} else {
				ArrayPrinterAction task1 = new ArrayPrinterAction(
						Arrays.copyOfRange(valuesToBeAdded, 0, valuesToBeAdded.length / 2 + 1));
				ArrayPrinterAction task2 = new ArrayPrinterAction(
						Arrays.copyOfRange(valuesToBeAdded, valuesToBeAdded.length / 2 + 1, valuesToBeAdded.length));
				task1.fork();
				task2.fork();
				/*
				 * try { task1.get(); task2.get(); } catch (InterruptedException |
				 * ExecutionException e) { e.printStackTrace(); }
				 */

			}
		}
	}
}
