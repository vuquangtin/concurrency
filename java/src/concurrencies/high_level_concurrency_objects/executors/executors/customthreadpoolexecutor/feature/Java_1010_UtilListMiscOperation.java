package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

public class Java_1010_UtilListMiscOperation {
	public static void main(String... args) {
		
		new Java_1010_UtilListMiscOperation().arrayOperation();
		
		new Java_1010_UtilListMiscOperation().listMethods();
		
		new Java_1010_UtilListMiscOperation().primitiveDatalistIteration();
		new Java_1010_UtilListMiscOperation().userDefinedObjListIteration();
		
		new Java_1010_UtilListMiscOperation().copyListConstructor();
		new Java_1010_UtilListMiscOperation().copyListCollectionsCopy();
		new Java_1010_UtilListMiscOperation().copyListCollectionsAddAll();
		new Java_1010_UtilListMiscOperation().copyListListAddAll();
		new Java_1010_UtilListMiscOperation().copyListClone();
		new Java_1010_UtilListMiscOperation().copyListJava8();

		new Java_1010_UtilListMiscOperation().synchronizedArrayListOperation();
		new Java_1010_UtilListMiscOperation().synchronizedCopyOnWriteArrayListOperation();
		
		new Java_1010_UtilListMiscOperation().checkDuplicateEntryInArray();
		
		new Java_1010_UtilListMiscOperation().setOperation();
	}
	
	
	String[] createNumberInStringWithoutNewKeywordArray(){
		String[] numberInStringArray = { "ZERO", "one", "tWO", "Three", "FOUR", "five"};
		return numberInStringArray;
	}
	
	String[] createNumberInStringNewKeywordArray(){
		String[] numberInStringArray = new String[] { "ZERO", "one", "tWO", "Three", "FOUR", "five"};
		return numberInStringArray;
	}
	
	Integer[] createNumberWithoutNewKeywordArray(){
		Integer[] numberArray = { 23, 5, 17, 7, 29, 11, 2, 13, 3, 19 };
		return numberArray;
	}
	
	Integer[] createNumberNewKeywordArray(){
		Integer[] numberArray = new Integer[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };
		return numberArray;
	}
	
	Integer[] createArray1To5(){
		return new Integer[] { 1, 2, 3, 4, 5 };
	}
	
	Integer[] createArray5To13(){
		return new Integer[] { 6, 7, 8, 9, 10, 11, 12, 13 };
	}
	
	ArrayList<String> createNumberInStringList(){
		// Using Diamond operator from Java7
		ArrayList<String> numberInStringList = new ArrayList<>();
		numberInStringList.add("ZERO"); //strings is type safe
		numberInStringList.add("one");
		numberInStringList.add("tWO");
		numberInStringList.add("Three");
		numberInStringList.add("FOUR");
		numberInStringList.add("five");
		return numberInStringList;
	}
		
	List<Integer> createNumberList(){
		// create an empty array list with an initial capacity: 10, 100, 1000 ???
		List<Integer> integerList = new ArrayList<Integer>();
		integerList.add(new Integer(7)); // previous to Java-5, required to wrap an int
		integerList.add(3); // after Java-5, handled by auto-boxing
		integerList.add(5);
		integerList.add(2);
		integerList.add(13);
		integerList.add(11);
		return integerList;
	}
	
	List<ExampleProductDto> createExampleProductDtoList(){
		List<ExampleProductDto> exampleProductDtoList = new ArrayList<>();
		exampleProductDtoList.add(new ExampleProductDto(3, "Product-3", 300));
		exampleProductDtoList.add(new ExampleProductDto(2, "Product-2", 200));
		exampleProductDtoList.add(new ExampleProductDto(5, "Product-4", 400));
		exampleProductDtoList.add(new ExampleProductDto(1, "Product-1", 100));
		exampleProductDtoList.add(new ExampleProductDto(4, "Product-5", 500));
		
		// Set object value using Setter > constructor is not required
		ExampleProductDto exampleProductDto3 = new ExampleProductDto();
		exampleProductDto3.setProductId(3);//here id is duplicate
		exampleProductDto3.setProductName("Product-2");
		exampleProductDto3.setProductPrice(2000);
		exampleProductDtoList.add(exampleProductDto3);
		
		return exampleProductDtoList;
	}
	
	
	
	void arrayOperation() {
		out.println("\n\nArray Operation");
		List<Integer> integerList = Arrays.asList(createNumberWithoutNewKeywordArray());

		out.println("\nTraverse list of array of object using for each");
		for (Integer number : integerList) {
			out.print(number + ", ");
		}

		out.println("\n\nConvert list of objects into array of objects using toArray() and traverse using for each");
		Integer numArray[] = new Integer[integerList.size()];
		// toArray() returns an Object[] and copies the content into other array
		// without storing into numArray, integerList can be directly used for iteration > see Case2
		numArray = integerList.toArray(numArray);
		for (Integer number : numArray) {
			out.print(number + ", ");
		}
		
		out.println("\nCase2: Here toArray() does not have any parameter");
		Object[] ob = integerList.toArray();
		for (Object value : ob) {
			out.print(value + ", ");
		}
		
		
		out.println("\n\nConvert an object array to primitive array using ArrayUtils of Apache Commons");
		int[] primitives = ArrayUtils.toPrimitive(numArray);
		out.println(Arrays.toString(primitives));
		
		
		out.println("\n\nCreate list from array using Arrays.asList()");
		List<Integer> numberListNew = Arrays.asList(createNumberNewKeywordArray());
		out.println("Original List: " + numberListNew);
		List<Integer> numberListWithoutNew = Arrays.asList(createNumberWithoutNewKeywordArray());
		out.println("Original List: " + numberListWithoutNew);
		
		
		Integer[] numberArray02 = createNumberWithoutNewKeywordArray();
		// Replace an element at a particular index in Array
		numberArray02[5] = 100;		

	}

	
	
	void listMethods() {
		out.println("\n\n\nList Operations OR Methods");		
		List<String> numberInStrList = createNumberInStringList();
		
		out.println("Size of arrayList: " + numberInStrList.size()); //6
		out.println("index of an ZERO in ArrayList: " + numberInStrList.indexOf("ZERO")); // 0
		out.println("index of an Product-1 in ArrayList: " + numberInStrList.indexOf("Product-1")); //-1
		out.println("First item in the list (index 0): " + numberInStrList.get(0)); // ZERO
		out.println(numberInStrList.contains(2)); //false
		out.println(numberInStrList.contains("tWO")); //true
		numberInStrList.remove("tWO"); 
	    out.println(numberInStrList.size()); //5
	    numberInStrList.add("tWO");
		// Replace an element at a particular index in ArrayList
	    numberInStrList.set(3, "replacement");
	    
	    
		// not working Pending
		//List<Integer> numberList90 = Arrays.asList(createNumberWithoutNewKeywordArray());
		//numberList90.remove(0);
	    //List<Integer> destNumberList = Arrays.asList(createArray5To13());
	    //destNumberList.add(22);

	    
	    ArrayList<String> copyOfStringList02 = new ArrayList<String>();
	    out.println("\nRemove all elements from ArrayList and make the list empty");
	    copyOfStringList02.clear();
	    out.println(copyOfStringList02);
	    
	    
		out.println("\n\nCheck ArrayList is empty or not");
		//isEmpty() will return true if List is empty
		out.println("productList.isEmpty(): " + numberInStrList.isEmpty());
		if(numberInStrList.size() != 0){
			out.println("productList.size() != 0 : " + (numberInStrList.size() == 0));
		}
		if(!numberInStrList.isEmpty()){
		    out.println("!productList.isEmpty(): " + (!numberInStrList.isEmpty()));
		}
		if(numberInStrList.size() != 0){
		    out.println("productList.size() != 0 : " + (numberInStrList.size() != 0));
		}
		
	}

	
	void primitiveDatalistIteration() {
		out.println("\n\n\nPrimitive Data List Iteration");		
		List<String> numberInStrList = createNumberInStringList();
	    
		out.println("\nTraverse using for loop");
		out.println("\nIteration of list with primitive data using for loop and size()");
		for (int i = 0; i < numberInStrList.size(); i++) {
			out.print(numberInStrList.get(i) + ", ");
		}

		out.println("\n\nIteration of list with primitive data using foreach loop from Java5 onwards");
		for (String str : numberInStrList) {
			out.print(str + ", ");
		}
		
		
		out.println("\n\nTraverse using Iterator");
		out.println("\nIteration of list with primitive data using Iterator and while loop");
		Iterator<String> iterator = numberInStrList.iterator();
		while (iterator.hasNext()) {
			out.print(iterator.next() + ", ");
		}

		out.println("\n\nIteration of list with primitive data using ListIterator and while loop");
		ListIterator<String> listIterator = numberInStrList.listIterator();
		while (listIterator.hasNext()) {
			out.print(listIterator.next() + ", ");
		}

		out.println("\n\nIteration of list with primitive data in reverse order using ListIterator and while loop");
		while (listIterator.hasPrevious()) {
			out.print(listIterator.previous() + ", ");
		}		
		
		
		//V.Imp
		List<String> numberInStringArrayList = Arrays.asList(createNumberInStringWithoutNewKeywordArray());
		
		out.println("\n\nTraverse using forEach method and Lambda Expression");
		numberInStringArrayList.forEach(str -> out.print(str + ", "));

		out.println("\n\nTraverse using forEach method and Lambda Expression with type parameter");
		numberInStringArrayList.forEach((String str) -> out.print(str + ", "));

		out.println("\n\nTraverse using Lambda Expression with function block");
		numberInStringArrayList.forEach((String str) -> {
			out.print(str + ", ");
		});
		
		out.println("\n\nTraverse using Consumer Class: Check file Java_1120_UtilFunction");
		
		out.println("\n\nTraverse using forEach method Lambda Expression and stream");
		numberInStringArrayList.stream().forEach(str -> out.print(str + ", "));
		
	}
	
	
	
	void userDefinedObjListIteration() {
		out.println("\n\n\nUser Defined Object List Iteration");	
		
		out.println("\nIterate (without generics) of list with user-defined objects using Iterator and while loop");
		List<ExampleProductDto> exampleProductDtoList7 = createExampleProductDtoList();
		@SuppressWarnings("rawtypes")
		Iterator iterator7 = exampleProductDtoList7.iterator();
		while (iterator7.hasNext()) {
			// cast is required for returned value
			ExampleProductDto exampleProductDto = (ExampleProductDto) iterator7.next();
			// Check exception thrown after deleting specific element Pending
			out.println(exampleProductDto);
		}
		
		
		out.println("\n\nIterate (with generics) of list with user-defined objects using Iterator and while loop");
		List<ExampleProductDto> exampleProductDtoList = createExampleProductDtoList();
		Iterator<ExampleProductDto> iterator2 = exampleProductDtoList.iterator();
		while (iterator2.hasNext()) {
			// cast is not required for returned value
			ExampleProductDto exampleProductDto = iterator2.next();
			// Check exception thrown after deleting specific element Pending
			out.println(exampleProductDto);
		}
		

		out.println("\nTraverse (restricted) using classical for loop");
		if(!exampleProductDtoList.isEmpty()){
			for (int i = 0; i < exampleProductDtoList.size(); i++) {
				ExampleProductDto exampleProductDto = null;
				exampleProductDto = exampleProductDtoList.get(i);
				if (2 == (exampleProductDto.getProductId())) {
					out.print(exampleProductDto.getProductId() + " ");
					out.print(exampleProductDto.getProductName() + " ");
					out.println(exampleProductDto.getProductPrice());
				}
			}
		}

	}
	
	
	void copyListConstructor() {
		out.println("\n\n1. Constructor");
		
		out.println("\n1. A. List of objects of primitive data type");
		List<Integer> numberList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		out.println("Original List: " + numberList);
		
		out.println("\n\n1. A. i. Copy into newly created list by passing the list as an argument");
		List<Integer> copyOfIntegerList = new ArrayList<Integer>(numberList);
		out.println("Copied List: " + copyOfIntegerList);
		
		
		out.println("\nImpact on original list after deleting specific element: work independently");
		copyOfIntegerList.remove(1);
		numberList.remove(0);
		out.println("Original List after delete operation: " + numberList);
		out.println("Copied List after delete operation: " + copyOfIntegerList);
		
		
		out.println("\n\n1. A. ii. Copy into existing list by passing the list as an argument");
		List<Integer> numberList05 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
		List<Integer> copyNumberList06 = new ArrayList<>(Arrays.asList(6, 7, 8, 9, 10));
		out.println("Invalid scenario because it force to create duplicate local variable copyNumberList06");
		//List<Integer> copyNumberList06 = new ArrayList<Integer>(numberList05);
		
		
		out.println("\n\n1. B. List of objects of user-defined data type");
		List<ExampleProductDto> exampleProductDtoList10 = createExampleProductDtoList();
		
		out.println("\n1. B. i. Copy into newly created list by passing the list as an argument");
		List<ExampleProductDto> copyExampleProductDtoList = new ArrayList<>(exampleProductDtoList10);
		Iterator<ExampleProductDto> iterator3 = copyExampleProductDtoList.iterator();
		while (iterator3.hasNext()) {
			ExampleProductDto exampleProductDto = iterator3.next();
			// able to print the object because of overridden toString() method in ExampleProductDto
			out.println(exampleProductDto);
		}
				
	}
	
	
	void copyListCollectionsCopy() {
		out.println("\n\n2. Collections.copy(destinationList, sourceList) overlapped the content");
		
		out.println("\n2. A. List of objects of primitive data type");
		List<ExampleProductDto> sourceProdList = createExampleProductDtoList();
		
		out.println("\n2. A. i. Copy into newly created list OR Size of destination-list < size of source-list");
		List<ExampleProductDto> destProdList = new ArrayList<>();
		try {
			out.println("Invalid scenario because size of newly created list is 0 zero");
			out.println("Size of destination-list/1st-arg should be larger than source-list/2nd-arg");
			out.println("IndexOutOfBoundsException: source does not fit in dest");
			Collections.copy(destProdList, sourceProdList);
		} catch (IndexOutOfBoundsException ex) {
			System.out.print(ex);
		}
		out.println("\nSize of sourceProdList: " + sourceProdList.size());
		out.println("Size of destProdList: " + destProdList.size());
		
		
		out.println("\n\n2. A. ii. Copy into existing list with bigger size of destination-list than source-list");
		List<String> sourceStrList2 = new ArrayList<>(Arrays.asList("1", "2", "3"));
		List<String> destStrList2 = new ArrayList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
		out.println("Source List: " + sourceStrList2);
		out.println("Destination List: " + destStrList2);
		Collections.copy(destStrList2, sourceStrList2);
		
		
		out.println("\nImpact on original list after copy operation: Destination overlapped");
		out.println("Source List after copy: " + sourceStrList2);
		out.println("Destination List after copy: " + destStrList2);
		
		
		out.println("\nImpact on original list after deleting specific element");
		out.println("Unwanted scenario to check the impact after copy operation");
		destStrList2.remove(1);
		sourceStrList2.remove(0);
		out.println("Source List after delete: " + sourceStrList2);
		out.println("Destination List after delete: " + destStrList2);
		
		
		out.println("\n\n2. B. List of objects of user-defined data type");
		List<ExampleProductDto> sourceProdList2 = new ArrayList<>();
		sourceProdList2.add(new ExampleProductDto(9, "Product-9", 900));
		List<ExampleProductDto> destProdList2 = createExampleProductDtoList();
		out.println("Destination List: " + destProdList2);

		out.println("\n\n2. B. i. Copy into existing list with bigger size of destination-list than source-list");
		Collections.copy(destProdList2, sourceProdList2);
		out.println("Destination List after copy: " + destProdList2);
		
	}
	
	
	void copyListCollectionsAddAll() {
		out.println("\n\n3. Collections.addAll(destinationList, sourceList) ");
		out.println("append/join ARRAY instead of overlapping; 2nd argument is ARRAY, not list");
		
		out.println("\n3. A. List of objects of primitive data type");
		Integer[] sourceNumberArray = { 6, 7, 8, 9, 10, 11, 12, 13 };
		out.print("Original Array: ");
		for (Integer number : sourceNumberArray) {
			out.print(number + ", ");
		}
		
		out.println("\n\n3. A. i. Copy into newly created list");
		List<Integer> destNumberList02 = new ArrayList<Integer>();
		Collections.addAll(destNumberList02, sourceNumberArray);
		out.println(destNumberList02);
		
		
		out.println("\n3. A. ii. Copy into existing list");
		List<Integer> destNumberList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
		Collections.addAll(destNumberList, sourceNumberArray);
		out.println(destNumberList);
		
		
		out.println("\n\n3. B. List of objects of user-defined data type");
		ExampleProductDto []sourceProdArray = {new ExampleProductDto(100, "Product-100", 100)};

		out.println("\n3. B. i. Copy into existing list");
		List<ExampleProductDto> destProdList2 = createExampleProductDtoList();
		out.println("Destination List: " + destProdList2);
		Collections.addAll(destProdList2, sourceProdArray);
		out.println("Destination List after copy: " + destProdList2);
		
		
		out.println("\n\n3. C. Array of objects of primitive data type");
		List<Integer> destNumberList20 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
		Collections.addAll(destNumberList20, 9, 10);
		out.println(destNumberList20);
		
		
		out.println("\n\n3. D. Array of objects of user-defined data type");
		List<ExampleProductDto> destProdList3 = createExampleProductDtoList();
		Collections.addAll(destProdList3, new ExampleProductDto(200, "Product-200", 200));
		out.println("Destination List after copy: " + destProdList3);
		
	}
	
	
	void copyListListAddAll() {
		out.println("\n\n4. java.util.List.addAll() Append/join list to another list");
		
		out.println("\n4. A. List of objects of primitive data type");
		List<String> sourceStrList01 = new ArrayList<>(Arrays.asList("Six", "Seven"));
				
		out.println("\n4. A. i. Copy into newly created list");
		List<String> destStrList01 = new ArrayList<>();
		destStrList01.addAll(sourceStrList01);
	    out.println(destStrList01);
	    
	    
	    out.println("\n4. A. ii. Copy into existing list");
	    List<String> destStrList02 = createNumberInStringList();
	    destStrList02.addAll(sourceStrList01);
	    out.println(destStrList02);
	    
	    
	    out.println("\n\n4. B. List of objects of user-defined data type");
		List<ExampleProductDto> sourceProdList2 = new ArrayList<>();
		sourceProdList2.add(new ExampleProductDto(99, "Product-99", 99));
		
		out.println("\n4. B. i. Copy into existing list");
		List<ExampleProductDto> destProdList2 = createExampleProductDtoList();
		destProdList2.addAll(sourceProdList2);
		out.println("Destination List after copy: " + destProdList2);
	}
	
	
	void copyListClone() {
		out.println("\n\n5. clone()");
		out.println("Dto implements Cloneable interface; CloneNotSupportedException");
		
		out.println("\n5. A. List of objects of primitive data type");
		
		
		out.println("\n5. B. List of objects of user-defined data type");
		List<ExamProdCloneDto> productList10 = new ArrayList<>();
		productList10.add(new ExamProdCloneDto("Product-9", 9, new  Accessory("Access-10", 10)));
		productList10.add(new ExamProdCloneDto("Product-91", 91, new  Accessory("Access-92", 92)));
		
		out.println("\n5. B. i. Copy into newly created list using (ExamProdCloneDto)item.clone()");
		List<ExamProdCloneDto> cloneList = new ArrayList<ExamProdCloneDto>();
		for (ExamProdCloneDto item : productList10) {
			try {
				// copy each user-defined object of a list one by one
				cloneList.add((ExamProdCloneDto)item.clone());
			} catch (CloneNotSupportedException e) {
				out.println(e);
			}
		}
		out.println(cloneList);
		
		
		out.println("\n\n5. C. Array of objects of primitive data type");
		Integer [] numberArray2 = createNumberWithoutNewKeywordArray();
		
		out.println("\n5. C. i. Copy into newly created ARRAY using array.clone()");
		// copy whole array at a time
		Integer [] copyNumberArray2 = numberArray2.clone();
		for (Integer number : copyNumberArray2) {
			out.print(number + ", ");
		}
		
		
		out.println("\n\n5. D. Array of objects of user-defined data type");
		
		
		out.println("\n\n5. E. Copy Constructor");
		ExamProdCloneDto prodDto1 = new ExamProdCloneDto("Prod-1", 1, new  Accessory("Access-1", 1));
		out.println("Original Object: " + prodDto1);
		ExamProdCloneDto copyProdDto1 = new ExamProdCloneDto(prodDto1);
		out.println("Copied Object:   " + copyProdDto1);
		
		
		// Unable to achieve > changing the original object Pending
		// hints: make instance variable public and try in below way
		out.println("\n\n5. F. Shallow Copy");
		ExamProdCloneDto prodDto2 = new ExamProdCloneDto("Prod-10", 10, new  Accessory("Access-11", 11));
		// OR
		/*ExamProdCloneDto prodDto2 = new ExamProdCloneDto("Prod-10", 10);
		prodDto2.setAccessory(new  Accessory());
		prodDto2.getAccessory().setAccessName("Access-11");
		prodDto2.getAccessory().setAccessPrice(11);*/
		out.println("Original Object: " + prodDto2);
		
		ExamProdCloneDto prodDtoClone2 = null;
		try {
			prodDtoClone2 = (ExamProdCloneDto) prodDto2.clone();
		} catch (CloneNotSupportedException e) {
			out.println(e);
		}
		prodDtoClone2.setProductName("Prod-40");
		prodDtoClone2.getAccessory().setAccessName("Access-41");
		out.println("Cloned Object:   " + prodDtoClone2);
		

		Test2 t1 = new Test2();
		t1.a = 10;
		t1.b = 20;
		t1.c = new Test();
		t1.c.x = 30;
		t1.c.y = 40;

		Test2 t2 = null;
		try {
			t2 = (Test2) t1.clone();
		} catch (CloneNotSupportedException e) {
			out.println(e);
		}
		t2.a = 100;
		t2.c.x = 300;
		// Change in object type field will be reflected in both t2 and t1(shallow copy)
		System.out.println(t1.a + " " + t1.b + " " + t1.c.x + " " + t1.c.y);
		System.out.println(t2.a + " " + t2.b + " " + t2.c.x + " " + t2.c.y);
		/*10 20 300 40
		100 20 300 40*/
		
		
		// Change in object type field of prodDtoClone4 will not be reflected in prodDto4(deep copy)
		out.println("\n\n5. G. Deep Copy");
		ExamProdCloneDto prodDto4 = new ExamProdCloneDto("Prod-20", 20, new  Accessory("Access-21", 21));
		out.println("Original Object: " + prodDto4);
		
		ExamProdCloneDto prodDtoClone4 = null;
		try {
			prodDtoClone4 = (ExamProdCloneDto) prodDto4.clone();
		} catch (CloneNotSupportedException e) {
			out.println(e);
		}
		prodDtoClone4.setProductName("Prod-30");
		prodDtoClone4.setAccessory(new  Accessory("Access-31"));
		out.println("Cloned Object:   " + prodDtoClone4);
		
	}
	
	
	void copyListJava8() {
		out.println("\n\n6. Java 8; overwrite, not overlapping");
		
		out.println("\n6. B. List of objects of user-defined data type");
		List<ExampleProductDto> sourceProdList2 = createExampleProductDtoList();
		
		out.println("\n6. B. i. Copy into newly created list");
		List<ExampleProductDto> destProdList2 = sourceProdList2.stream().collect(Collectors.toList());
		out.println(destProdList2);

		
		out.println("\nApply filter to skip part of the list: skip or don't copy 2 elements");
		List<ExampleProductDto> destProdList4 = sourceProdList2.stream().skip(2).collect(Collectors.toList());
		out.println(destProdList4);
		
		
		out.println("\n\n6. B. ii. Copy into existing list");
		List<ExampleProductDto> sourceProdList6 = new ArrayList<>();
		sourceProdList6.add(new ExampleProductDto(99, "Product-99", 99));
		List<ExampleProductDto> destProdList6 = createExampleProductDtoList();
		out.println("Original list: " + destProdList6);
		destProdList6 = sourceProdList6.stream().collect(Collectors.toList());
		out.println("Original list after copy: " + destProdList6);
		
	}
	
	
	void synchronizedArrayListOperation() {
		out.println("\n\nSynchronized ArrayList Operation");
		List<String> stringList = createNumberInStringList();

		out.println("\nSynchronized List cloned using existing list");
		List<String> syncList = Collections.synchronizedList(stringList);
		out.println(syncList);

		
		out.println("\nSynchronized List using LinkedList()");
		List<String> synchronizedArrayList = Collections.synchronizedList(new ArrayList<>()); //new LinkedList<>()
		// final NameList list = new NameList(); //unable to resolve the compilation error
		synchronizedArrayList.add("one");
		synchronizedArrayList.add("two");
		synchronized (synchronizedArrayList) {
			synchronizedArrayList.add("three");
		}
		synchronizedArrayList.add("four");

		// ConcurrentModificationException: if try to remove element while iterating with or without sync block
		if (synchronizedArrayList.size() > 0) {
			synchronizedArrayList.remove(0);
		}
		
		out.println("Iterating synchronized List (working with or without synchronized block)");
		synchronized (synchronizedArrayList) {
			Iterator<String> iterator = synchronizedArrayList.iterator();
			while (iterator.hasNext()){
				out.println(iterator.next());
			}
		}

		
		List<String> stringLinkedList = new LinkedList<>();
		stringLinkedList.add("nine");
		synchronized (stringLinkedList) {
			stringLinkedList.add("ten");
		}
		// need to check iteration in linkedList (not working even in sync block) Pending
		out.println("\nLinkedList: " + stringLinkedList);
		
	}

	
	void synchronizedCopyOnWriteArrayListOperation() {
		List<String> numberInStringList = createNumberInStringList();
		CopyOnWriteArrayList<String> synchronizedList = new CopyOnWriteArrayList<String>(numberInStringList);

	    out.println("\n\nIterating Synchronized CopyOnWriteArrayList");
	    //Synchronized block is not required in case of CopyOnWriteArrayList
	    Iterator<String> iterator = synchronizedList.iterator(); 
	    while (iterator.hasNext()){
	    	out.println(iterator.next());
	    }
	}


	public void checkDuplicateEntryInArray() {
		out.println("\n\nCheck duplicate entry in array");
		Integer[] duplicateInArray = new Integer[] { 2, 3, 5, 7, 3 };
		//Integer[] duplicateInArray = createNumberWithoutNewKeywordArray();
		//duplicateInArray[duplicateInArray.length + 1] = 3;
		
		out.println("\nCompare each elements to all other elements of array & complexity O(n^2)");
		for (int i = 0; i < duplicateInArray.length; i++) {
			for (int j = 0; j < duplicateInArray.length; j++) {
				if (duplicateInArray[i].equals(duplicateInArray[j]) && i != j) {
					// avoid first time entry using counter > 1 Pending
					out.println("Array contains duplicate entry");
				}
			}
		}

		out.println("\nCompare size of List and Set");
		List<Integer> duplicateInList = Arrays.asList(duplicateInArray);
		Set<Integer> inputSet = new HashSet<Integer>(duplicateInList);
		if (inputSet.size() < duplicateInList.size()) {
			out.println("Array contains duplicate entry");
		}
		
		out.println("\nSet doesn't contain duplicate and add() return false if try to add duplicates");
		Set<Integer> duplicateInSet = new HashSet<Integer>();
		for (Integer num : duplicateInArray) {
			if (!duplicateInSet.add(num)) {
				out.println("Tried to add duplicate entry");
			}
		}		
	}
	
	
	
	void setOperation(){
		out.println("\n\nConverting ArrayList into HashSet to remove the duplicate element");
		out.println("Set removes duplicate entries");
		Set<ExampleProductDto> exampleProductDtoSet = new HashSet<ExampleProductDto>(createExampleProductDtoList());
		out.println(exampleProductDtoSet);
	}
	
}
