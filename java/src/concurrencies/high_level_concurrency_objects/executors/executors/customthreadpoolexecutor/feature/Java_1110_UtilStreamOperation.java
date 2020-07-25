package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import executors.customthreadpoolexecutor.feature.ExampleProductDto.Availability;

public class Java_1110_UtilStreamOperation {

	public static void main(String args[]) {

		new Java_1110_UtilStreamOperation().collectionOperationJava();
		new Java_1110_UtilStreamOperation().collectionOperationJava8();
		new Java_1110_UtilStreamOperation().streamOperationFilterCollection();
		new Java_1110_UtilStreamOperation().StreamToList();	
		new Java_1110_UtilStreamOperation().streamIterate();
		new Java_1110_UtilStreamOperation().streamIterator();
	}
	
	String[] createNumberInStringWithoutNewKeywordArray(){
		String[] numberInStringArray = { "ZERO", "one", "tWO", "Three", "FOUR", "five", " ", "one"};
		return numberInStringArray;
	}
	
	Integer[] createNumberWithoutNewKeywordArray(){
		Integer[] numberArray = { 0, 1, 2, 3, 4, 5, 7, 11, 13, 17, 19, 23, 29, 3 };
		return numberArray;
	}
	
	public List<ExampleProductDto> createExampleProductDtoList() {
		List<ExampleProductDto> exampleProductDtoList = new ArrayList<ExampleProductDto>();
		exampleProductDtoList.add(new ExampleProductDto(0, "Product-1", 200));
		exampleProductDtoList.add(new ExampleProductDto(1, "Product-4", 300));
		exampleProductDtoList.add(new ExampleProductDto(2, "Product-3", 250));
		exampleProductDtoList.add(new ExampleProductDto(3, "Product-2", 310));
		exampleProductDtoList.add(new ExampleProductDto(4, "Product-5", 500));
		return exampleProductDtoList;
	}
	
	public ExampleProductDto[] createExampleProductDtoArray() {
		ExampleProductDto []exampleProductDtoArray = {
		new ExampleProductDto(1, "Product-2", 200),
		new ExampleProductDto(4, "Product-5", 500),
		new ExampleProductDto(2, "Product-3", 300),
		new ExampleProductDto(0, "Product-1", 100),
		new ExampleProductDto(3, "Product-4", 410)};
		return exampleProductDtoArray;
	}
	
	List<ExampleProductDto> dataCreation(){
		List<ExampleProductDto> exampleProductDtoList = new ArrayList<>();
		exampleProductDtoList.add(new ExampleProductDto(2, "Product-2", 200, Availability.SOLD_OUT));
		exampleProductDtoList.add(new ExampleProductDto(5, "Product-5", 500, ExampleProductDto.Availability.IN_STOCK));
		exampleProductDtoList.add(new ExampleProductDto(3, "Product-3", 300, Availability.IN_STOCK));
		exampleProductDtoList.add(new ExampleProductDto(1, "Product-1", 100, ExampleProductDto.Availability.IN_STOCK));
		exampleProductDtoList.add(new ExampleProductDto(4, "Product-4", 400, ExampleProductDto.Availability.SOLD_OUT));
		return exampleProductDtoList;
	}
	
	
	
	void collectionOperationJava(){
		out.println("\n\nUsing Java 7");

		List<String> numberInStringArrayList = Arrays.asList(createNumberInStringWithoutNewKeywordArray());
		out.print("Original List: " + numberInStringArrayList);
		
		int count = 0;
		int countStringLength3 = 0;
		List<String> filteredList = new ArrayList<String>();
		for (String string : numberInStringArrayList) {
			if (string.trim().isEmpty()) {
				count++;
			}
			
			if (string.length() == 3) {
				countStringLength3++;
			}
			
			if (!string.trim().isEmpty()) {
				filteredList.add(string);
			}
		}
		out.print("\nCount of Empty Strings: " + count);		
		out.print("\nCount of Strings of length 3: " + countStringLength3);
		out.print("\nFiltered List with non-empty string\n" + filteredList);
				
		
		out.println("\nEliminate empty string and merge string using comma");
		StringBuilder stringBuilder = new StringBuilder();
		for (String string : numberInStringArrayList) {
			if (!string.trim().isEmpty()) {
				stringBuilder.append(string);
				stringBuilder.append(", ");
			}
		}
		String mergedString = stringBuilder.toString();
		out.print(mergedString.substring(0, mergedString.length() - 2));
		
		
		List<Integer> numberArrayList = Arrays.asList(createNumberWithoutNewKeywordArray());
		out.print("\n\nNumber Array List: " + numberArrayList);
		
		out.print("\nSquare of list of distinct numbers: ");
		List<Integer> squaresList = new ArrayList<Integer>();
		for (Integer number : numberArrayList) {
			Integer square = new Integer(number.intValue() * number.intValue());
			if (!squaresList.contains(square)) {
				squaresList.add(square);
			}
		}
		out.print(squaresList);
		
		
		int max = numberArrayList.get(0);
		int sum = numberArrayList.get(0);
		for (int i = 1; i < numberArrayList.size(); i++) {
			Integer number = numberArrayList.get(i);
			if (number.intValue() > max) {
				max = number.intValue();
			}
			
			sum += numberArrayList.get(i);
		}
		out.print("\nHighest number in List: " + max);
		out.print("\nSum of all numbers: " + sum);
		out.print("\nAverage of all numbers: " + sum / numberArrayList.size());

		
		Random random = new Random();
		out.println("\nRandom Numbers");
		for (int i = 0; i < 5; i++) {
			out.print(random.nextInt() + ", ");
		}		
		// print comma separated numbers  + ", " Pending
		random.ints().limit(5).sorted().forEach(out::print);
	}
	
	void collectionOperationJava8(){
		out.print("\n\n\nPredicate Functional Interface Java8 Lambda");
		
		List<String> numberInStringArrayList = Arrays.asList(createNumberInStringWithoutNewKeywordArray());		
		
		out.println("\n\nTraverse Original List Or All Strings ");
		out.println("using Sequential Stream");
		numberInStringArrayList.stream().forEach(str -> out.print(str + ", "));
		
		out.println("\nusing Method Reference");
		// need to find out the way to use comma separator
		numberInStringArrayList.forEach(out::print);

		out.println("\nusing Parallel Stream (sequence of elt may be different)");
		numberInStringArrayList.stream().parallel().forEach(str -> out.print(str + ", "));
		
		out.println("\nusing always true Predicate Functional Interface");
		stringOperationPredicateFunction(numberInStringArrayList, str -> true);
		
		out.println("\nusing always true Predicate Functional Interface and stream");
		stringOperationPredicateFunction2(numberInStringArrayList, str -> true);
		
		out.println("\nusing always true Predicate Functional Interface, stream and filter");
		stringPredicateFunctionLambda(numberInStringArrayList, str -> true);
		
		
		out.print("\n\nPrint no string using always false Predicate Functional Interface");
		stringOperationPredicateFunction(numberInStringArrayList, (str) -> false);

		
		out.println("\n\nCount no. of elts. in list using Sequential Stream: " + numberInStringArrayList.stream().count());
		out.println("Count no. of elts. in list using Parallel Stream: " + numberInStringArrayList.stream().parallel().count());
		
		long count = numberInStringArrayList.stream().filter(str -> str.trim().isEmpty()).count();
		out.println("Count of Empty Strings using Sequential Stream: " + count);

		count = numberInStringArrayList.parallelStream().filter(str -> str.trim().isEmpty()).count();
		out.println("Count of empty string using Parallel Stream: " + count);
		
		count = numberInStringArrayList.parallelStream().filter(str -> str.trim().isEmpty()).count();
		out.println("Count of Empty Strings using parallel Stream: " + count);

		List<String> filteredSetOfStringList = numberInStringArrayList.stream().filter(str -> !str.trim().isEmpty()).collect(Collectors.toList());
		out.println("\nFiltered List with non-empty string\n" + filteredSetOfStringList);
		
		String mergedString = numberInStringArrayList.stream().filter(str -> !str.trim().isEmpty()).collect(Collectors.joining(", "));
		out.println("\nEliminate empty string and separate using comma\n" + mergedString);
		
		count = numberInStringArrayList.stream().filter(str -> str.length() == 3).count();
		out.println("\nCount of Strings of length 3 using Sequential Stream: " + count);

		out.println("\nStrings of length 3 using Sequential Stream");
		Stream<String> sequentialStream1 = numberInStringArrayList.stream();
		Predicate<String> stringLength3Predicate1 = str -> str.length() == 3;
		Stream<String> filteredStringLength3SStream1 = sequentialStream1.filter(stringLength3Predicate1);
		List<String> stringLength3FilteredList1 = filteredStringLength3SStream1.collect(Collectors.toList());
		out.printf("Filtered list: %s %n", stringLength3FilteredList1);

		out.println("\nStrings of length 3 using Predicate as function block");
		Stream<String> sequentialStream2 = numberInStringArrayList.stream();
		Predicate<String> stringLength3Predicate2 = (str) -> {
			boolean stringLength3 = false;
			stringLength3 = str.length() == 3;
			return stringLength3;
		};
		sequentialStream2.filter(stringLength3Predicate2).forEach(str -> out.print(str + ", "));
		
		out.println("\n\nStrings of length 3 using Predicate annonymous function");
		Stream<String> sequentialStream4 = numberInStringArrayList.stream();
		Predicate<String> stringLength3Predicate4 = new Predicate<String>() {
			@Override
			public boolean test(String str) {
				return str.length() == 3;
			}
		};
		sequentialStream4.filter(stringLength3Predicate4).forEach(str -> out.print(str + ", "));
		
		out.println("\n\nStrings of length 3 using Parallel Stream");
		Stream<String> parallelStream = numberInStringArrayList.parallelStream();
		Stream<String> filteredStringLength3PStream = parallelStream.filter(str -> str.length() == 3);
		filteredStringLength3PStream.forEach(str -> out.print(str + ", "));
		
		out.println("\n\nString which starts with t and 3 letters long using logical functions and(), or(), xor()");	
		Predicate<String> strStartsWithTP = (str) -> str.startsWith("t");
		Predicate<String> str4LettersLongP = (str) -> str.length() == 3;
		Stream<String> filteredSStream2 = numberInStringArrayList.stream().filter(strStartsWithTP.and(str4LettersLongP));
		filteredSStream2.forEach((str) -> out.println(str));
		
		out.println("\nString which starts with t and 3 letters long using logical operators");	
		Predicate<String> combinedPredicate = (str) -> str.startsWith("t") & str.length() == 3;
		Stream<String> filteredSStream3 = numberInStringArrayList.stream().filter(combinedPredicate);
		filteredSStream3.forEach((str) -> out.println(str));
		
		out.println("\nStrings of length 3 using Sequential Stream & Predicate Functional Interface");
		Predicate<String> stringLength3Predicate = (String str) -> str.length() == 3;
		stringPredicateFunctionLambda(numberInStringArrayList, stringLength3Predicate);
		
		out.println("\n\nStrings of length 3 using Lambda & Predicate Functional Interface but without stream");
		Predicate<String> stringLength3Predicate3 = (str) -> str.length() == 3;
		numberInStringArrayList.forEach((String str) -> {
			if (stringLength3Predicate3.test(str)) {
				out.print(str + ", ");
			}
		});
		
		
		
		List<Integer> numList = Arrays.asList(createNumberWithoutNewKeywordArray());
		
		out.println("\n\nFind Prime no with Custom Predicate Function");
		Predicate<Integer> numberPrimePredicate = (n) -> isPrime(n);
		List<Integer> primeNumbers = getPrimeList(numList, numberPrimePredicate);
		out.println(primeNumbers);
		
		out.println("\nFind Prime no using filter");
		List<Integer> primeNo = getPrimeList2(numList, numberPrimePredicate);
		out.println(primeNo);
		
		
		Predicate<Integer> numberEvenPredicate = n -> n % 2 == 0;
		
		
		
		out.print("\n\nConvert all strings of list into uppercase");
		List<String> setOfStringList2 = new ArrayList<>();
		for (String str : numberInStringArrayList) {
			setOfStringList2.add(str.toUpperCase());
		}
		out.printf("\nOriginal List: %s, \nusing pre Java 8: %s %n", numberInStringArrayList, setOfStringList2);
		
		// Need to explore more (map, reduce, ::) Pending
		out.println("\nusing Stream Map, distinct and collect");
		List<String> filteredSetOfStringList3 = numberInStringArrayList.stream().map(str -> str.toUpperCase()).distinct().collect(toList());
		out.println(filteredSetOfStringList3);
		
		out.println("\nusing Stream Map, Method Reference and collect");
		List<String> filteredSetOfStringList2 = numberInStringArrayList.stream().map(String::toUpperCase).collect(Collectors.toList());
		out.println(filteredSetOfStringList2);
		
		String formattedStrings = numberInStringArrayList.stream().map(str -> str.toUpperCase()).collect(Collectors.joining(", "));
		out.println("\nusing Stream Map and collect with comma separator\n" + formattedStrings);
		
		out.println("\nusing Stream Map, Method Reference in one line");
		numberInStringArrayList.stream().map((String str) -> str.toUpperCase()).forEach(out::print);
		
	}	
	
	void stringOperationPredicateFunction(List<String> stringList, Predicate<String> conditionOrPredicate) {
		for (String str : stringList) {
			if (conditionOrPredicate.test(str)) {
				out.print(str + ", ");
			}
		}
	}
	
	void stringOperationPredicateFunction2(List<String> stringList, Predicate<String> conditionOrPredicate) {
		stringList.stream().forEach(str -> {
			if (conditionOrPredicate.test(str)) {
				out.print(str + ", ");
			}
		});
	}
	
	public void stringPredicateFunctionLambda(List<String> stringList, Predicate<String> conditionOrPredicate) {
		stringList.stream().filter((string) -> (conditionOrPredicate.test(string))).forEach((str) -> {
			out.print(str + ", ");
		});
	}
	
	public List<Integer> getPrimeList(List<Integer> numberList, Predicate<Integer> predicate) {
		List<Integer> sortedNumbers = new ArrayList<>();
		// return boolean value after testing the provided data like num2
		numberList.stream().filter((num) -> (predicate.test(num))).forEach((num2) -> {
			sortedNumbers.add(num2);
		});
		return sortedNumbers;
	}
	public List<Integer> getPrimeList2(List<Integer> numberList, Predicate<Integer> predicate) {
		List<Integer> sortedNumbers = new ArrayList<>();
		numberList.stream().filter(this::isPrime).forEach((num2) -> {
			sortedNumbers.add(num2);
		});
		return sortedNumbers;
	}
	public boolean isPrime(int number) {
		if (number == 1) {
			return false;
		}
		for (int i = 2; i < Math.sqrt(number); i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

	
	
	void streamOperationFilterCollection(){
		// streamMap
		// stream().map returns a stream consisting of the results of applying the given function to the elements of this stream.
		// stream() converts list to Stream, map() converts each element to upper-case and collect results into a new list
		
		out.println("\n\nFilter Collection using java.util.stream.Stream.filter(Predicate<? super className> predicate)");
		List<ExampleProductDto> exampleProductDtoCollectionList = dataCreation();

		out.println("\nAll Products");
		exampleProductDtoCollectionList.forEach(out::println);

		
		Predicate<ExampleProductDto> exampleProductDtoPricePredicate = (obj) -> obj.getProductPrice() < 301;
		Predicate<ExampleProductDto> exampleProductDtoNamePredicate = obj -> obj.getProductName().equalsIgnoreCase("Product-1");
		Predicate<ExampleProductDto> exampleProductDtoAvailabilityInPredicate = obj -> obj.getAvailability() == ExampleProductDto.Availability.IN_STOCK;
		Predicate<ExampleProductDto> exampleProductDtoAvailabilityOutPredicate = obj -> obj.getAvailability().equals(Availability.SOLD_OUT);
		
		out.println("\nFiltered user defined list using Lambda and Predicate as parameter of filter() method");
		exampleProductDtoCollectionList.stream().parallel().filter(exampleProductDtoPricePredicate).forEach(obj -> out.println(obj.getProductName()));
		
		out.println("\nFiltered user defined list using Lambda and Predicate as parameter of user-defined method");
		displaySpecificProduct(exampleProductDtoCollectionList, exampleProductDtoPricePredicate);
		
		out.println("\nFiltered user defined list using Lambda, Predicate and forEach()");
		exampleProductDtoCollectionList.stream().filter(product -> product.getProductPrice() < 301).forEach(out::println);
		
		
		Stream<ExampleProductDto> productDtoListStream = exampleProductDtoCollectionList.stream();
		Stream<ExampleProductDto> availableProductStream = productDtoListStream.filter(exampleProductDtoAvailabilityInPredicate);
		out.println("\nNo. of Products IN_STOCK: " + availableProductStream.count());
		out.println("Note: Both the above stream variables can't be reused, otherwise \"IllegalStateException: stream has already been operated upon or closed\"");
		
		
		
		int noArray[] = { 1, 2, 3, 4, 5 };
		IntStream noArrStream = Arrays.stream(noArray);
		out.println("\nSum of array of numbers: " + noArrStream.sum());
		
		Stream<ExampleProductDto> productDtoListStream2 = exampleProductDtoCollectionList.stream();
		Stream<ExampleProductDto> availableProductStream2 = productDtoListStream2.filter(exampleProductDtoAvailabilityInPredicate);
		IntStream priceAvailableProductIntStream = availableProductStream2.mapToInt(ExampleProductDto::getProductPrice);
		out.println("\nTotal price of all available products using sum() and mapToInt(): " + priceAvailableProductIntStream.sum());

		Stream<ExampleProductDto> productDtoListStream3 = exampleProductDtoCollectionList.stream();
		IntStream priceProductIntStream2 = productDtoListStream3.mapToInt(obj -> obj.getProductPrice());
		out.println("\nTotal Price of all products: " + priceProductIntStream2.sum());
		
		Stream<ExampleProductDto> exampleProductDtoListStream1 = exampleProductDtoCollectionList.stream();
		DoubleStream priceProductDoubleStream = exampleProductDtoListStream1.mapToDouble((ExampleProductDto obj) -> obj.getProductPrice());
		out.println("\nTotal Price of all products: " + priceProductDoubleStream.sum());
		
		Stream<ExampleProductDto> productDtoListPStream2 = exampleProductDtoCollectionList.stream().parallel(); 
		double totalPrice = productDtoListPStream2.map(exampleProductDto -> exampleProductDto.getProductPrice()).reduce(0, Integer::sum);
		out.println("\nTotal price of all products: " + totalPrice);
		
		Stream<ExampleProductDto> productDtoListPStream3 = exampleProductDtoCollectionList.stream().parallel();
		double totalPrice2 = productDtoListPStream3.map(ExampleProductDto::getProductPrice).reduce(0, Integer::sum);
		out.println("\nTotal price of all products: " + totalPrice2);
		
		
		
		out.println("\n\nStream.reduce() with Accumulator which reduce Array into single number or string");
		
		OptionalInt sum2 = Arrays.stream(noArray).reduce((x, y) -> x + y);
  	  	out.println("\nSum of Array (OptionalInt): "+ sum2);
  	  	
  	  	OptionalInt sum6 = Arrays.stream(noArray).reduce(Integer::sum);
  	  	out.println("\nSum of Array (OptionalInt and built-in method reference): "+ sum6);
  	  	
  	  	OptionalInt sum7 = Arrays.stream(noArray).reduce(StatisticsUtility::addIntData);
	  	out.println("\nSum of Array (OptionalInt and custom method reference): "+ sum7);
  	  	
  	  	out.print("\nSum of Array (ifPresent): ");
  	  	Arrays.stream(noArray).reduce((x, y) -> x + y).ifPresent(s -> out.println(s));
		
  	  	int sum3 = Arrays.stream(noArray).reduce(0, (x, y) -> x + y);
		out.println("\nSum of Array (initial value 0 zero): "+ sum3);
		// 0 zero or "" empty string ensure about certainty of some result; That's why Optional is not required.
		
		String[] strArray = { "aa", "bb", "cc", "dd", "ee" };
		String resultStr = Arrays.stream(strArray).reduce(", ", (a, b) -> a + b);
		out.println("\nConcatenate array of Strings (initial value \"\" empty string): " + resultStr);
		
		String resultStr2 = Arrays.stream(strArray).reduce("\nConcatenate array of Strings with separator: ", (a, b) -> a + ", " + b);
		out.println(resultStr2);
		
		
		
		out.println("\n\nStream.reduce() with Identity, Accumulator and Combiner"); // skip
		List<Integer> list2 = Arrays.asList(2, 3, 4);
		// 2*2 + 2*3 + 2*4 = 18.
		int res = list2.parallelStream().reduce(2, (s1, s2) -> s1 * s2, (p, q) -> p + q);
		out.println(res);

		List<String> strArray2 = Arrays.asList("aa", "bb", "cc", "dd", "ee");
		String result6 = strArray2.parallelStream().reduce("-", (s1, s2) -> s1 + s2, (p, q) -> p + q);
		out.println(result6);
		
		
		
		Stream<ExampleProductDto> productDtoListStream4 = exampleProductDtoCollectionList.stream();
		OptionalDouble avg = productDtoListStream4.mapToInt(obj -> obj.getProductPrice()).average();
		if (avg.isPresent()) {
			out.println("\nAverage: " + avg.getAsDouble());
			out.println("Note: average() returns OptionalDouble because to avoiod the condition of infinity");
		} else {
			out.println("Average was not calculated");
		}
		
		out.println("\nSummary Statistics Java8 Lambda");
		Stream<ExampleProductDto> productDtoListStream5 = exampleProductDtoCollectionList.stream();
		IntStream priceProductIntStream3 = productDtoListStream5.mapToInt((obj) -> obj.getProductPrice());
		IntSummaryStatistics intSummaryStatistics2 = priceProductIntStream3.summaryStatistics();
		out.println("Count of numbers: " + intSummaryStatistics2.getCount());
		out.println("Lowest number: " + intSummaryStatistics2.getMin());
		out.println("Highest number: " + intSummaryStatistics2.getMax());
		out.println("Sum of all numbers: " + intSummaryStatistics2.getSum());
		out.println("Average of all numbers: " + intSummaryStatistics2.getAverage());
		
		final Collection<String> result = exampleProductDtoCollectionList.stream() // Stream<String>
				.mapToInt(ExampleProductDto::getProductPrice) // IntStream
				.asLongStream() // LongStream
				.mapToDouble(points -> points / totalPrice2) // DoubleStream
				.boxed() // Stream<Double>
				.mapToLong(weigth -> (long) (weigth * 100)) // LongStream
				.mapToObj(percentage -> percentage + "%") // Stream<String>
				.collect(Collectors.toList()); // List<String>		
		out.println("\nCalculate the percentage of total price of each products: " + result);
		
		
		
		out.println("\n\nApplying 10% VAT on each product");
		for (ExampleProductDto obj : exampleProductDtoCollectionList) {
			double price = obj.getProductPrice() + .10 * obj.getProductPrice();
			out.print(price + ", ");
		}		
		out.println();
		
		exampleProductDtoCollectionList.stream().map((obj) -> obj.getProductPrice() + .10 * obj.getProductPrice()).forEach(str -> out.print(str + ", "));

		double total = 0;
		for (ExampleProductDto obj : exampleProductDtoCollectionList) {
			double price = obj.getProductPrice() + .10 * obj.getProductPrice();
			total = total + price;
		}
		out.println("\nTotal using pre Java8: " + total);
		
		// Need to explore more (map, reduce, ::) Pending
		double bill = exampleProductDtoCollectionList.stream().map((obj) -> obj.getProductPrice() + .10 * obj.getProductPrice()).reduce((sum, price) -> sum + price).get();
		out.println("Total using map and reduce stream: " + bill);		
		
		
		
		out.println("\nGroup distinct products by their Availability using groupingBy()");
		Stream<ExampleProductDto> productDtoListStream6 = exampleProductDtoCollectionList.stream();
		Map<Availability, List<ExampleProductDto>> availableProductMap = productDtoListStream6.distinct().collect(Collectors.groupingBy(ExampleProductDto::getAvailability));
		out.println(availableProductMap);
		
		
		
		// Sync (add below details in Java notes) Pending
		/*out.println("\n\nGet ExampleAccessoryDto from ExampleProductDto");
		List<ExampleProductDto> exampleProductDtoList4 = createExampleProductDtoList();
		List<ExampleAccessoryDto> exampleAccessoryDtoList = new ArrayList<>(); 
		Iterator<ExampleProductDto> exampleProductDtoiter = exampleProductDtoList4.iterator();
		while(exampleProductDtoiter.hasNext()){
			// NullPointerException > need to check after adding records Pending
			// verify with working code Pending
			exampleProductDtoiter.next().getExampleAccessoryDtoList().forEach(exampleAccessoryDtoList::add);
		}
		out.print(exampleAccessoryDtoList);*/
		
		
		// Sync (add below details in Java notes) Pending
		out.println("\n\nConvert Object List To String List");		
		List<ExampleProductDto> exampleProductDtoList5 = createExampleProductDtoList();
		List<String> idStringList = exampleProductDtoList5.stream().map(object -> Objects.toString(object.getProductId(), null)).collect(Collectors.toList());
		out.println(idStringList);
		
		List<String> idStringList2 = new ArrayList<>(exampleProductDtoList5.size());
		for (Object object : exampleProductDtoList5) {
			// unable to use "object.getProductId()" like above Pending
			idStringList2.add(Objects.toString(object, null));
		}
		out.println(idStringList2);
		
		List<String> idStringList3 = new ArrayList<>(exampleProductDtoList5.size());
		for (Object object : exampleProductDtoList5) {
			// unable to use "object.getProductId()" like above Pending
			idStringList3.add(object != null ? object.toString() : null);
		}
		out.println(idStringList3);
		
		
		
	}
	
	void displaySpecificProduct(List<ExampleProductDto> exampleProductDtoList, Predicate<ExampleProductDto> exampleProductDtoPredicate) {
		exampleProductDtoList.stream().parallel().forEach(exampleProductDto1 -> {
			if (exampleProductDtoPredicate.test(exampleProductDto1)) {
				out.println(exampleProductDto1);
			}
		});
		/*for (int i = 0; i < exampleProductDtoList.size(); i++) {
			ExampleProductDto exampleProductDto1 = exampleProductDtoList.get(i);	
		}*/
	}
	
	
	
	void StreamToList(){
		
		out.print("\n\nArray to Sequential Array Stream: ");
		// Returns a sequential Stream with the specified array as its source
		Stream<String> numberInStringArraySequentialArrayStream = Arrays.stream(createNumberInStringWithoutNewKeywordArray());
		numberInStringArraySequentialArrayStream.forEach(p -> out.print(p + ", "));
		
		out.print("\nArray to Sequential Stream: ");
		Stream<String> numberInStringArraySequentialStream = Stream.of(createNumberInStringWithoutNewKeywordArray());
		// Returns a sequential Stream containing a single element
		numberInStringArraySequentialStream.forEach(p -> out.print(p + ", "));


		out.println("\n\nConvert Stream To List");
		
		numberInStringArraySequentialStream = Stream.of(createNumberInStringWithoutNewKeywordArray());
		List<String> listOfStream = numberInStringArraySequentialStream.collect(Collectors.toList());
		out.println("using Collectors.toList() method: " + listOfStream);

		numberInStringArraySequentialStream = Stream.of(createNumberInStringWithoutNewKeywordArray());
		listOfStream = numberInStringArraySequentialStream.collect(Collectors.toCollection(ArrayList::new));
		out.println("using Collectors.toCollection method: " + listOfStream);

		numberInStringArraySequentialStream = Stream.of(createNumberInStringWithoutNewKeywordArray());
		ArrayList<String> list = new ArrayList<>();
		numberInStringArraySequentialStream.forEach(list::add);
		out.println("by adding each elet. in newly created list: " + list);

		numberInStringArraySequentialStream = Stream.of(createNumberInStringWithoutNewKeywordArray());
		ArrayList<String> myList = new ArrayList<>();
		numberInStringArraySequentialStream.parallel().forEachOrdered(myList::add);
		out.println("by adding each elet. in newly created list and Parallel Stream: " + myList);

		Stream<String> streamOfNames = Stream.of(createNumberInStringWithoutNewKeywordArray());
		Object[] arrayOfString = streamOfNames.toArray();
		List<Object> listOfNames = Arrays.asList(arrayOfString);
		out.println("by passing array of Object or Object[] to toArray(): " + listOfNames);

		Stream<String> streamOfShapes = Stream.of(createNumberInStringWithoutNewKeywordArray());
		String[] arrayOfShapes = streamOfShapes.toArray(String[]::new);
		List<String> listOfShapes = Arrays.asList(arrayOfShapes);
		out.println("by passing array of String or String[] to toArray(): " + listOfShapes);
	}
	
	
	void streamIterate(){
		out.println("\n\nwhile loop replacement");
		
		out.println("stream of the first 10 natural numbers");
		// 1L is starting point and also represent data type Long
		Stream<Long> tenNaturalNumbers = Stream.iterate(1L, n  ->  n  + 1).limit(10);
		tenNaturalNumbers.forEach(System.out::println);
		// not working
		//Stream.iterate(1L, n -> n + 1).takeWhile(n -> n < 10).forEach(System.out::println);
		
		
		out.println("filters the values generated from an iterate function");
		Stream.iterate(2L, n  ->  n  + 1).filter(this::isOdd).limit(5).forEach(System.out::println);
		
		
		out.println("discard some elements from a stream, use the skip operation.");
		Stream.iterate(2L, n  ->  n  + 1).filter(this::isOdd).skip(100).limit(5).forEach(System.out::println);
		
		
		out.println("produce an infinite Stream using Stream.iterate()");
		Stream.iterate(2, (Integer n) -> n*n).limit(5).forEach(System.out::println);
		
		
		out.println("produce an infinite Stream using Stream.generate()");
		Stream.generate(Math::random).limit(5).forEach(System.out::println);
		
	}
	
	public boolean isOdd(long number) {
		if (number % 2 == 0) {
			return false;
		}
		return true;
	}
	
	
	void streamIterator(){
		out.println("\n\nIterator replacement or Iterate list of objects");
		
		out.println("\nUsing Iterable, StreamSupport, spliterator()");
		Iterator<String> strListIter = Arrays.asList("A", "B", "C", "D").iterator();
		Iterable<String> iterable = () -> strListIter;
		Stream<String> strStream = StreamSupport.stream(iterable.spliterator(), false);
		strStream.forEach(out::println);
		
		Iterator<String> strListIter2 = Arrays.asList("A", "B", "C").iterator();
		Stream<String> strStream2 = StreamSupport.stream(Spliterators
				.spliteratorUnknownSize(strListIter2, Spliterator.ORDERED), false);
		strStream2.forEach(out::println);
	}
	
	// unused
	public static <T> Stream<T> iteratorToStream(final Iterator<T> iterator, final boolean parallell) {
		Iterable<T> iterable = () -> iterator;
		return StreamSupport.stream(iterable.spliterator(), parallell);
	}
	
}

class StatisticsUtility {
	public static int addIntData(int num1, int num2) {
		return num1 + num2;
	}
}
