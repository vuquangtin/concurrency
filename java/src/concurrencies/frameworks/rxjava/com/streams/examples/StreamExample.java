package com.streams.examples;

import java.util.Arrays;
import java.util.List;

public class StreamExample {

	static List<Integer> numbers = Arrays.asList(7, 2, 5, 4, 2, 1);

	public static void main(String[] args) {
		withoutStream();
		withStream();
	}

	public static void withoutStream() {
		long count = 0;
		for (Integer number : numbers) {
			if (number % 2 == 0) {
				count++;
			}
		}
		System.out.printf("There are %d elements that are even", count);
	}

	public static void withStream() {
		long count = numbers.stream().filter(num -> num % 2 == 0).count();
		System.out.printf("\nThere are %d elements that are even", count);
	}

}