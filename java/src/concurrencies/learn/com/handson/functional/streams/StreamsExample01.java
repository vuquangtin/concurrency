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
public class StreamsExample01 {

	/* This is a simple stream example */
	public static void main(String[] args) {
		List<String> names = new ArrayList<String>();
		names.add("Veera");
		names.add("Venkatesh");
		names.add("Gopal");
		Stream<String> namesStream = names.stream();
		out.println("Printing stream elements");
		namesStream.forEach(out::println);
	}

}
