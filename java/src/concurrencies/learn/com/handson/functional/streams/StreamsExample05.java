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
public class StreamsExample05 {

	/*
	 * New immutable stream get's created when we run non-terminal operations on
	 * existing stream.This can be used to construct chains of calls on the Stream
	 * object.You cann't use previous stream after applying non-terminal operation
	 * on previous stream.It result's stream already close error.Un comment below
	 * code to see that behavior.
	 */
	public static void main(String[] args) {
		List<String> names = new ArrayList<String>();
		names.add("Veerabhadrudu");
		names.add("Venkatesh");
		names.add("Gopal");
		names.add("Veerakumar");
		names.add("Veerashekar");
		Stream<String> originalNamesStream = names.stream();
		Stream<String> filteredStream = originalNamesStream.filter(name -> name.matches("^Veera.*$"));
		/*
		 * out.println("Original Stream names ");
		 * originalNamesStream.forEach(out::println);
		 */
		out.println("Filtered Stream names ");
		filteredStream.forEach(out::println);

	}

}
