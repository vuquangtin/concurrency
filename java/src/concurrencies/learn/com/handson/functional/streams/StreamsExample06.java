/**
 * 
 */
package com.handson.functional.streams;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author veera
 *
 */
public class StreamsExample06 {

	/*
	 * Stream.flatmap example
	 */
	public static void main(String[] args) {
		List<String> names = new ArrayList<String>();
		names.add("Veerabhadrudu");
		names.add("Venkatesh");
		names.add("Gopal");
		names.add("Veerakumar");
		names.add("Veerashekar");
		Stream<String> originalNamesStream = names.stream();
		Stream<Character> chars = originalNamesStream.flatMap(name -> name.chars().mapToObj(i -> (char) i));
		out.println("Filtered Stream names ");
		chars.forEach(out::println);
	}

}
