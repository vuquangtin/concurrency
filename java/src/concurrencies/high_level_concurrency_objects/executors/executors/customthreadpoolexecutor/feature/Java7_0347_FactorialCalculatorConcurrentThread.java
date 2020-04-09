package executors.customthreadpoolexecutor.feature;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Test;

public class Java7_0347_FactorialCalculatorConcurrentThread implements Callable<Long> {

	private final int number;

	public Java7_0347_FactorialCalculatorConcurrentThread(int number) {
		this.number = number;
	}

	@Override
	public Long call() {
		long output = 0;
		try {
			output = factorial(number);
		} catch (InterruptedException ex) {
			Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
		}
		return output;
	}

	private long factorial(int number) throws InterruptedException {
		if (number < 0) {
			throw new IllegalArgumentException("Number must be greater than zero");
		}
		long result = 1;
		while (number > 0) {
			Thread.sleep(2); // adding delay for example
			result = result * number;
			number--;
		}
		return result;
	}
}
