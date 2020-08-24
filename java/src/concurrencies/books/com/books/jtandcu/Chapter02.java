package com.books.jtandcu;

import java.math.BigDecimal;

public class Chapter02 {

	private static final BigDecimal FOUR = BigDecimal.valueOf(4);
	private static final int roundingMode = BigDecimal.ROUND_HALF_EVEN;
	private static BigDecimal result;

	public static void main(String[] args) {
//		Runnable r = () -> {
//			result = computePi(50000);
//		};
		Runnable r1 = new Runnable() {

			@Override
			public void run() {
				result = computePi(50000);
			}
		};
		Thread t = new Thread(r1);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {

		}
		System.out.println(result);
	}

	private static BigDecimal computePi(int digits) {
		int scale = digits + 5;
		BigDecimal arctan1_5 = arctan(5, scale);
		BigDecimal arctan1_239 = arctan(239, scale);
		BigDecimal pi = arctan1_5.multiply(FOUR).subtract(arctan1_239).multiply(FOUR);
		return pi.setScale(digits, BigDecimal.ROUND_HALF_UP);
	}

	private static BigDecimal arctan(int inverseX, int scale) {

		BigDecimal result, numer, term;
		BigDecimal invX = BigDecimal.valueOf(inverseX);
		BigDecimal invX2 = BigDecimal.valueOf(inverseX * inverseX);
		numer = BigDecimal.ONE.divide(invX, scale, roundingMode);
		result = numer;
		int i = 1;
		do {
			numer = numer.divide(invX2, scale, roundingMode);
			int denom = 2 * i + 1;
			term = numer.divide(BigDecimal.valueOf(denom), scale, roundingMode);
			if ((i % 2) != 0)
				result = result.subtract(term);
			else
				result = result.add(term);
			i++;
		} while (term.compareTo(BigDecimal.ZERO) != 0);
		return result;
	}
}