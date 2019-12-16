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
public class StreamsExample03 {

	/*
	 * Stream is a data structure which will point/refer to the original collection
	 * created it.Below example shows , after creating stream even if we modify
	 * original collection,those changes are reflected in stream.
	 */
	public static void main(String[] args) {
		List<String> names = new ArrayList<String>();
		names.add("Veera");
		names.add("Venkatesh");
		names.add("Gopal");
		Stream<String> namesStream = names.stream();
		names.add("Rahul");
		out.println("Printing stream elements after modifiying original list");
		namesStream.forEach(out::println);
	}

}
