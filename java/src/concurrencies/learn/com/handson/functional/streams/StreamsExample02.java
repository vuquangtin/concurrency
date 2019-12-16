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
public class StreamsExample02 {

	/*
	 * After calling terminal operation's(forEach,count,min,max etc.) on stream you
	 * cann't reuse that stream any more.
	 */
	public static void main(String[] args) {
		List<String> names = new ArrayList<String>();
		names.add("Veera");
		names.add("Venkatesh");
		names.add("Gopal");
		Stream<String> namesStream = names.stream();
		out.println("Printing stream elements 1st time");
		namesStream.forEach(out::println);
		out.println();
		out.println("Printing stream elements 2nd time");
		namesStream.forEach(out::println);
	}

}
