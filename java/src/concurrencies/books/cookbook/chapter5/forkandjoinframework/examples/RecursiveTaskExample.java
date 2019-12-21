package cookbook.chapter5.forkandjoinframework.examples;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/***
 * @see https
 *      ://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading
 *      /fork-and-join.html
 * @author vuquangtin
 *
 */
public class RecursiveTaskExample {

	public static void main(String[] args) {
		List<BigInteger> list = new ArrayList<>();
		for (int i = 3; i < 20; i++) {
			list.add(new BigInteger(Integer.toString(i)));
		}

		BigInteger sum = ForkJoinPool.commonPool().invoke(
				new FactorialTask(list));
		System.out.println("Sum of the factorials = " + sum);
	}

	private static class FactorialTask extends RecursiveTask<BigInteger> {
		private static int SEQUENTIAL_THRESHOLD = 5;
		private List<BigInteger> integerList;

		private FactorialTask(List<BigInteger> integerList) {
			this.integerList = integerList;
		}

		@Override
		protected BigInteger compute() {
			if (integerList.size() <= SEQUENTIAL_THRESHOLD) {
				return sumFactorials();
			} else {
				int middle = integerList.size() / 2;
				List<BigInteger> newList = integerList.subList(middle,
						integerList.size());
				integerList = integerList.subList(0, middle);
				FactorialTask task = new FactorialTask(newList);
				task.fork();
				BigInteger thisSum = this.compute();
				BigInteger thatSum = task.join();
				return thisSum.add(thatSum);
			}
		}

		private BigInteger sumFactorials() {
			BigInteger sum = BigInteger.ZERO;
			for (BigInteger i : integerList) {
				sum = sum.add(CalcUtil.calculateFactorial(i));
			}
			return sum;
		}
	}
}
