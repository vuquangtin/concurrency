package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Java_1015_UtilSorting {
	
	public static void main(String... args) {
		new Java_1015_UtilSorting().sortPrimitiveObjectList();
		new Java_1015_UtilSorting().sortUserDefinedObjectList();
	}

	String[] createNumberInStringWithoutNewKeywordArray(){
		String[] numberInStringArray = { "ZERO", "one", "tWO", "Three", "FOUR", "five"};
		return numberInStringArray;
	}
	
	String[] createNumberInStringWithoutNewKeywordArray2(){
		String[] numberInStringArray = { "ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE"};
		return numberInStringArray;
	}
	
	public List<ExampleProductDto> createExampleProductDtoList() {
		List<ExampleProductDto> exampleProductDtoList = new ArrayList<ExampleProductDto>(10);
		exampleProductDtoList.add(new ExampleProductDto(0, "Product-1", 200));
		exampleProductDtoList.add(new ExampleProductDto(4, "Product-5", 500));
		exampleProductDtoList.add(new ExampleProductDto(3, "Product-2", 310));
		exampleProductDtoList.add(new ExampleProductDto(1, "Product-4", 300));
		exampleProductDtoList.add(new ExampleProductDto(10, "Product-10", 10));
		exampleProductDtoList.add(new ExampleProductDto(100, "Product-100", 100));
		exampleProductDtoList.add(new ExampleProductDto(2, "Product-3", 250));
		exampleProductDtoList.add(new ExampleProductDto(200, "Product-200", 200));
		exampleProductDtoList.add(new ExampleProductDto(20, "Product-20", 20));
		return exampleProductDtoList;
	}
	
	
	
	void sortPrimitiveObjectList(){
		
		out.println("\n\n1.a. Sort Array of objects of primitive data-type");
		out.println("Note: Don't implements Comparable for array of objects of primitive data-type; Sorting happens by default");
		String[] numberInStringArray = createNumberInStringWithoutNewKeywordArray();
		
		out.println("\nin reverse order");
		Arrays.sort(numberInStringArray, Collections.reverseOrder());
		for (String str : numberInStringArray) {
			out.print(str + ", ");
		}
		
		out.println("\n\nin natural/alphabetical order");
		Arrays.sort(numberInStringArray);
		for (String str : numberInStringArray) {
			out.print(str + ", ");
		}
		
		
		
		out.println("\n\n\n1.b. Sort Array of objects of primitive data-type using Comparator in reverse order");
		Arrays.sort(numberInStringArray, new StringArrayReverseComparator());
		for (String str : numberInStringArray) {
			out.print(str + ", ");
		}
		
		
		
		out.println("\n\n\n2. a. Sort List of objects of primitive data-type");
		List<String> numberInStringArrayList1 = Arrays.asList(createNumberInStringWithoutNewKeywordArray());
		
		out.println("\nOriginal List before sorting");
		numberInStringArrayList1.forEach(str -> out.print(str + ", "));
		
		out.println("\n\nCase sensitive sort");
		Collections.sort(numberInStringArrayList1);
		numberInStringArrayList1.forEach(str -> out.print(str + ", "));
				
		out.println("\n\nCase insensitive sort");
		Collections.sort(numberInStringArrayList1, String.CASE_INSENSITIVE_ORDER);
		numberInStringArrayList1.forEach(str -> out.print(str + ", "));
		
		out.println("\n\nReverse sort");
		Collections.sort(numberInStringArrayList1, Collections.reverseOrder());
		numberInStringArrayList1.forEach(str -> out.print(str + ", "));
		
		
		
		out.println("\n\n\n2.b. Sort list of objects of primitive data-type using Comparator");
		List<String> numberInStringArrayList3 = Arrays.asList(createNumberInStringWithoutNewKeywordArray());
		
		out.println("\n2.b.i. using inner Comparator class in reverse order");
		Collections.sort(numberInStringArrayList3, new StringArrayReverseComparator());
		numberInStringArrayList3.forEach(str -> out.print(str + ", "));
		
		
		
		out.println("\n\n\n2.b.ii. using Anonymous inner Comparator class in reverse order");
		Comparator<String> stringArrayReverseComparator2 = new Comparator<String>() {
			@Override
			public int compare(String str1, String str2) {
				return str2.compareTo(str1);
			}
		};
		Collections.sort(numberInStringArrayList3, stringArrayReverseComparator2);
		numberInStringArrayList3.forEach(obj -> out.println(obj));
		
		
		
		out.println("\n\n2.b.iii. using Comparator and anonymous class");
		Collections.sort(numberInStringArrayList3, new Comparator<String>() {
			@Override
			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});
		numberInStringArrayList3.forEach(str -> out.print(str + ", "));

		out.println("\n\nusing Comparator and Lambda Expression V.Imp");
		Collections.sort(numberInStringArrayList3, (str1, str2) -> str1.compareTo(str2));
		numberInStringArrayList3.forEach(str -> out.print(str + ", "));
		
		
		
		out.println("\n\n\n2.b.iv. using List.sort(Comparator<? super String> c)");
		out.println("\nusing List.sort(Comparator) and anonymous class Pre Java8");
		numberInStringArrayList3.sort(new Comparator<String>() {
			@Override
			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});
		numberInStringArrayList3.forEach(str -> out.print(str + ", "));
		
		out.println("\n\nusing List.sort(Comparator) and Lambda Expression");
		numberInStringArrayList3.sort((str1, str2) -> str1.compareTo(str2));
		numberInStringArrayList3.forEach(str -> out.print(str + ", "));
		
		out.println("\n\nusing List.sort(Comparator), compareToIgnoreCase() and Lambda Expression as function block (Case insensitive Sorting in descending order)");
		numberInStringArrayList3.sort((str1, str2) -> {
			return str2.compareToIgnoreCase(str1);
		});
		numberInStringArrayList3.forEach(str -> out.print(str + ", "));
		
		
		String[] numberInStringArray2 = createNumberInStringWithoutNewKeywordArray2();
		out.println("\n\nSort another Array of objects of primitive data-type in natural/alphabetical order");
		Arrays.sort(numberInStringArray2);
		for (String str : numberInStringArray2) {
			out.print(str + ", ");
		}
		
		
		out.println("\n\nDefault Sorting: Case sensitive or Natural or Alphabetical Sorting in ascending order");
	}
	
	// Useless; not required for primitive data-type
	class StringArrayReverseComparable implements Comparable<String> {
		@Override
		public int compareTo(String str) {
			return str.compareTo(str);
		}
	}
	
	class StringArrayReverseComparator implements Comparator<String> {
		@Override
		public int compare(String str1, String str2) {
			// switch the use of arguments for reverse sorting or in descending order
			return str2.compareTo(str1);
		}
	}

	
	
	void sortUserDefinedObjectList(){
		
		out.print("\n\n\n3.a. Sort list of user-defined objects using compareTo method and Comparable Interface");
		List<ExampleProductDto> exampleProductDtoList = createExampleProductDtoList();
		
		out.println("\nObject List sorted by ProductName");
		//it is mandatory to implement Comparable interface in corresponding Dto; 
		Collections.sort(exampleProductDtoList);
		exampleProductDtoList.forEach(obj -> out.println(obj));		
		
		out.println("\nObject List sorted by id in reverse order");
		Collections.sort(exampleProductDtoList, Collections.reverseOrder());
		exampleProductDtoList.forEach(obj -> out.println(obj));
		
		
		
		out.print("\n\n3.b. Sort list of user-defined objects using compare method and Comparator Interface");
		
		out.println("\nOriginal List before sorting");
		List<ExampleProductDto> exampleProductDtoList1 = createExampleProductDtoList();
		exampleProductDtoList1.forEach(obj -> out.println(obj));
		
		out.println("\n3.b.i. using inner Comparator class by name");
		Collections.sort(exampleProductDtoList1, new ExampleProductDtoNameComparator());
		displayExampleProductDtoList(exampleProductDtoList1);

		
		
		out.println("\n3.b.ii. using Anonymous inner Comparator class by ProductPrice in increasing order");
		Comparator<ExampleProductDto> exampleProductDtoPriceComparator = new Comparator<ExampleProductDto>() {
			@Override
			public int compare(ExampleProductDto dto1, ExampleProductDto dto2) {
				return dto1.getProductPrice().compareTo(dto2.getProductPrice());
			}
		};
		
		Collections.sort(exampleProductDtoList1, exampleProductDtoPriceComparator);
		exampleProductDtoList1.forEach(obj -> out.println(obj));


		out.println("\nusing Anonymous inner Comparator class by ProductName");
		Comparator<ExampleProductDto> exampleProductDtoNameComparator = new Comparator<ExampleProductDto>() {
			@Override
			public int compare(ExampleProductDto dto1, ExampleProductDto dto2) {
				return dto1.getProductName().compareTo(dto2.getProductName());
			}
		};
		
		Collections.sort(exampleProductDtoList1, exampleProductDtoNameComparator);
		exampleProductDtoList1.forEach(obj -> out.println(obj));
		
		
		
		List<ExampleProductDto> exampleProductDtoList2 = createExampleProductDtoList();
		
		out.println("\n3.b.iii. using Comparator and Lambda Expression by ProductPrice in decreasing order");
		Collections.sort(exampleProductDtoList2, (dto1, dto2) -> dto2.getProductPrice().compareTo(dto1.getProductPrice()));
		exampleProductDtoList2.forEach(obj -> out.println(obj));

		out.println("\nusing Comparator and Lambda Expression by ProductName in decreasing order");
		Collections.sort(exampleProductDtoList2, (dto1, dto2) -> dto2.getProductName().compareTo(dto1.getProductName()));
		exampleProductDtoList2.forEach(obj -> out.println(obj));
		
		out.println("\nComparable: only one way of sorting is possible because it modifies the original array/list");
		out.println("Comparator: multiple sorting is possible because it doesn't modify the original array/list");
		out.println("Check binary search file for proof of concept: Java_1017_UtilSearch");
	}
	
	class ExampleProductDtoNameComparator implements Comparator<ExampleProductDto> {
		@Override
		public int compare(ExampleProductDto one, ExampleProductDto two) {
			return one.getProductName().compareTo(two.getProductName());
		}
	}
	
	class ExampleProductDtoIdComparator implements Comparator<ExampleProductDto> {
		@Override
		public int compare(ExampleProductDto one, ExampleProductDto two) {
			return (one.getProductId() < two.getProductId()) ? -1 : (one.getProductId() > two.getProductId()) ? 1	: 0;
		}
	}
	
	void displayExampleProductDtoList(List<ExampleProductDto> exampleProductDtoList) {
		for (Object obj : exampleProductDtoList) {
			out.println(obj);
		}
	}
	
	
	// Need to work-out to separate the Comparable implementation from Dto > not working
	//List<ExampleProductDtoProductNameComparable> exampleProductDtoList3 = createExampleProductDtoProductNameComparableList();
	//Collections.sort(exampleProductDtoList3);
	/*public List<ExampleProductDtoProductNameComparable> createExampleProductDtoProductNameComparableList() {
		List<ExampleProductDtoProductNameComparable> exampleProductDtoList = new ArrayList<>(10);
		exampleProductDtoList.add((ExampleProductDtoProductNameComparable) new ExampleProductDto(0, "Product-1", 200));
		exampleProductDtoList.add((ExampleProductDtoProductNameComparable) new ExampleProductDto(4, "Product-5", 500));
		exampleProductDtoList.add((ExampleProductDtoProductNameComparable) new ExampleProductDto(3, "Product-2", 310));
		exampleProductDtoList.add((ExampleProductDtoProductNameComparable) new ExampleProductDto(1, "Product-4", 300));
		exampleProductDtoList.add((ExampleProductDtoProductNameComparable) new ExampleProductDto(2, "Product-3", 250));
		return exampleProductDtoList;
	}*/
	/*class ExampleProductDtoProductNameComparable extends ExampleProductDto implements Comparable<ExampleProductDto> {
		private static final long serialVersionUID = 1L;
		
		ExampleProductDtoProductNameComparable(){
			super();
		}
		
		@Override
		public int compareTo(ExampleProductDto exampleProductDto) {
			return (exampleProductDto.getProductName()).compareTo(exampleProductDto.getProductName());
		}
	}*/

}
