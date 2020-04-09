package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Java_1017_UtilSearch {

	public static void main(String... args) {
		
		new Java_1017_UtilSearch().searchBinary1();
		new Java_1017_UtilSearch().searchBinary2();
	}
	
	String[] createNumberInStringWithoutNewKeywordArray(){
		String[] numberInStringArray = { "ZERO", "one", "tWO", "Three", "FOUR", "five"};
		return numberInStringArray;
	}
	
	void searchBinary1() {
		out.println("\n\nBinary Search");
		String[] numberInStringArray = createNumberInStringWithoutNewKeywordArray(); 

		out.println("Sort the array in natural order or alphabetically before search operation");
		Arrays.sort(numberInStringArray);
		for (String str : numberInStringArray) {
			out.print(str + ", ");
		}
		
		out.println("\nPosition of one after simple sort: " + Arrays.binarySearch(numberInStringArray, "one"));
		
		
		out.println("\nRe-sort the array in reverse order using Comparator");
		Arrays.sort(numberInStringArray, new StringArrayReverseComparator());
		for (String str : numberInStringArray) {
			out.print(str + ", ");
		}
		
		out.println("\n\nPosition of one without passing the Comparator to binarySearch(): " + Arrays.binarySearch(numberInStringArray, "one"));
		out.println("1: Actual insertion point: (-(insertion_point - 1))");
		out.println("First insertion point: -1 because 0 is valid result for a successful search");
		out.println("2: Sorting using Comparator is visible only when Comparator is passed as an arg");
		out.println("That's why Comparator does not modify original array/list & here result is based on first sorted array");

		out.println("\nPosition of one by passing the Comparator to binarySearch(): " + Arrays.binarySearch(numberInStringArray, "one", new StringArrayReverseComparator()));
		
	}

	class StringArrayReverseComparator implements Comparator<String> {
		@Override
		public int compare(String str1, String str2) {
			// switch the use of arguments for reverse sorting or in descending order
			return str2.compareTo(str1);
		}
	}

	void searchBinary2() {
		// creating List of 1M
		List<Integer> numbers = new ArrayList<Integer>(1000000);
		// records initializing List
		for (int i = 0; i < numbers.size(); i++) {
			numbers.add(new Integer(i));
		}

		// performing contains search
		long startTime = System.nanoTime();
		boolean isExist = numbers.contains(new Integer(1000000));
		long totalTime = System.nanoTime() - startTime;
		out.println("\n\nTime to search 1Mth Record using contains() is {} nano seconds: " + totalTime + " " + isExist);

		// performing binary search
		startTime = System.nanoTime();
		Collections.sort(numbers); // List needs to be sorted for Binary Search
		Integer number = Collections.binarySearch(numbers, new Integer(1000000));
		totalTime = System.nanoTime() - startTime;
		out.println("Time to search 1Mth Record using binary search is {} nano seconds: " + totalTime + " " + number);
	}
	
}
