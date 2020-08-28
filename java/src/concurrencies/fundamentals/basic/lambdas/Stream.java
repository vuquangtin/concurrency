package basic.lambdas;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Stream {

	public static void main(String[] args) {
		List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd",
				"", "jkl");

		List<String> filtered = strings.stream()
				.filter(string -> !string.isEmpty())
				.collect(Collectors.toList());

		List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
		// get list of unique squares
		List<Integer> squaresList = numbers.stream().map(i -> i * i).distinct()
				.collect(Collectors.toList());

		System.out.println(squaresList);

		Random random = new Random();
		random.ints().limit(10).forEach(System.out::println);
	}
}