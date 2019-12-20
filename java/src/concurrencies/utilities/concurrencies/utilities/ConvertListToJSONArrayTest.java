package concurrencies.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ConvertListToJSONArrayTest {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("India");
		list.add("Australia");
		list.add("England");
		list.add("South Africa");
		list.add("West Indies");
		list.add("Newzealand");
		list.add("10000");

		// this method converts a list to JSON Array
		String jsonStr = new JSONArray(Arrays.asList(list)).toString();
		System.out.println(jsonStr);
	}
}
