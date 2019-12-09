package synchronizedkeyword;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class FactorialSquareCalculator extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	private Integer n;

	public FactorialSquareCalculator(Integer n) {
		this.n = n;
	}

	@Override
	protected Integer compute() {
		if (n <= 1) {
			return n;
		}

		FactorialSquareCalculator calculator = new FactorialSquareCalculator(
				n - 1);

		calculator.fork();

		return n * n + calculator.join();
	}

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		FactorialSquareCalculator calculator = new FactorialSquareCalculator(10);
		forkJoinPool.execute(calculator);
		
	}
}