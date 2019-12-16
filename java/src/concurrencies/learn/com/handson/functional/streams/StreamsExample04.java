/**
 * 
 */
package com.handson.functional.streams;

import static java.lang.System.out;

import java.util.stream.Stream;

/**
 * @author veera
 *
 */
public class StreamsExample04 {

	/*
	 * Using Stream.generate method to create infinite random number generator.
	 */
	public static void main(String[] args) {
		Stream<Double> infiniteRandomNumbers = Stream.generate(Math::random);
		out.println("Printing infinite Random Numbers");
		infiniteRandomNumbers.forEach(out::println);
		
	}

}
