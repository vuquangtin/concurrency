package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.TreeMap;

import executors.customthreadpoolexecutor.feature.ExampleProductDto.Availability;

public class Java_1050_UtilMapMiscOperation {
	
	public static void main(String args[]) {
		
		new Java_1050_UtilMapMiscOperation().mapOperation();
		new Java_1050_UtilMapMiscOperation().mapPutOperationUserDefinedObject();
		
		new Java_1050_UtilMapMiscOperation().mapGetOrDefault();
		
		new Java_1050_UtilMapMiscOperation().exampleProductDtoOverrideEqualsHashCodeTesting();
	}

	Map<Integer, String> createNumberMap(){
		// Using Diamond operator from Java7
		Hashtable<Integer, String> numberHashtable = new Hashtable<>();
		Map<Integer, String> numberMap = new HashMap<>(numberHashtable);
		// OR
		//Map<Integer, String> numberMap = new HashMap<Integer, String>();
		
		numberMap.put(new Integer(82), "Eighty Two");
		numberMap.put(new Integer(81), "Eighty One");
		numberMap.put(new Integer(85), "Eighty five");
		numberMap.put(new Integer(83), "Eighty Three");
		numberMap.put(new Integer(84), "Eighty Four");
		return numberMap;
	}
	
	
	List<String> createNumberInStringArrayList(){
		String[] numberInStringArray = { "ZERO", "one", "tWO", "Three", "FOUR", "five"};
		List<String> numberInStringArrayList = Arrays.asList(numberInStringArray);
		return numberInStringArrayList;
	}
	
	
	// create map with existing list Pending
	public void createIntegerListOfStringMap() {
		List<String> numberInStringArrayList = createNumberInStringArrayList();
		Map<Integer, List<String>> integerStringListMap = new HashMap<>();
		out.println(numberInStringArrayList + " " + integerStringListMap);
	}
	
	
	
	void mapOperation(){
		out.println("\n\nBy default map values are sorted in asending order and remove the duplicate");
		Map<Integer, String> numberMap = createNumberMap();
		
		out.println("\nTraverse map's keys only");
		for (Integer key : numberMap.keySet()) {
		    System.out.println("Key: " + key);
		}

		out.println("\nTraverse map's values only");
		for (String value : numberMap.values()) {
		    System.out.println("Value: " + value);
		}
		
		out.println("\nTraverse map using entrySet() and Iterator");
		// Get key and corresponding value of Map in Set interface
		Set<Entry<Integer, String>> numberEntrySet = numberMap.entrySet();
		// Move next key and value of Map by iterator
		Iterator<Entry<Integer, String>> numberIterator = numberEntrySet.iterator();
		// OR
		//Iterator<Entry<Integer, String>> keySetIterator2 = numberMap.entrySet().iterator();
		while (numberIterator.hasNext()) {
			// Map.Entry<> OR Entry<>
			Map.Entry<Integer, String> numberIteratorEntry = numberIterator.next();
			if(numberIteratorEntry.getKey() == 81){
				numberIterator.remove(); //avoids a ConcurrentModificationException
			}
			out.println("Key: " + numberIteratorEntry.getKey() + ", Value: " + numberIteratorEntry.getValue());
		}
		
		out.println("\nTraverse map using entrySet() and for each");
		for (Map.Entry<Integer, String> entry : numberMap.entrySet()) {
			out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
		}
		
		
		out.println("\nGet value based on key 82: " + numberMap.get(82));		
		out.println("Value of key 83 to be removed from Map: " + numberMap.remove(83));
        out.println("Value of removed key 83 from Map: " + numberMap.get(83));
		out.println("Does HashMap contains 21 as key: " + numberMap.containsKey(81));
		out.println("Does HashMap contains 21 as value: " + numberMap.containsValue(81));
		out.println("Does HashMap contains Twenty One as value: " + numberMap.containsValue("Eighty One"));
		
		
		numberMap.put(21, "Twenty One");
		numberMap.put(31, "Thirty One");
		numberMap.put(21, "Twenty One"); // duplicate entries can't be added
		//compilation error because 22.0 is not integer
		numberMap.put((int) 22.0, "Twenty Two");
		
		out.println("\nTraverse map using keySet() after put, remove & get operation");
		Iterator<Integer> keySetIterator = numberMap.keySet().iterator();
		while (keySetIterator.hasNext()) {
			Integer key = keySetIterator.next();
			out.println("key: " + key + ", value: " + numberMap.get(key));
		}
        
		
		out.println("\nUnsorted Map\n" + numberMap);
		TreeMap<Integer, String> sortedTreeMap = new TreeMap<Integer, String>(numberMap);
		out.println("Sorted Map\n" + sortedTreeMap);
		
		
        out.println("\nSize of Map: " + numberMap.size());
		numberMap.clear(); // clear means remove all elements from map
		out.println("Size of Map after using clear method: " + numberMap.size());
		out.println("Check HashMap is empty or not: " + numberMap.isEmpty());		
	}
	
	
	void mapPutOperationUserDefinedObject(){
		out.println("\n\nMap Put Operation like \"add key/value pairs\" with User Defined Object");
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		//value of ExampleProductDto2 Object may vary
		ExampleProductDto exampleProductDto = new ExampleProductDto();
		exampleProductDto.setProductName("prod");
		map.put("str1", exampleProductDto);
		out.println("\n1. String/object > \"str1\"/ exampleProductDto.setProductName(\"prod\") pair; \nvalue: " + map.get("str1"));
		
		map.put("str2", Availability.IN_STOCK);
        String str2 = "str2";
        out.println("\n2. String/enum > \"str2\"/ Availability.IN_STOCK pair; \nvalue: " + map.get(str2));
        
		map.put(Availability.SOLD_OUT, "str3");
        Availability avail = Availability.SOLD_OUT;
        out.println("\n3. enum/String pair > Availability.SOLD_OUT/ \"str3\"; \nvalue: " + map.get(avail));
        
        
        // two stages of retrieval: hashCode() > correct bucket & equals() > correct object in the bucket 
		ExampleProductDto productObj = new ExampleProductDto();
		productObj.setProductName("product");
        map.put(productObj, "str4");
        out.println("\n4. ObjectReference/String pair > productObj.setProductName(\"product\")/\"str4\"; \nvalue: " + map.get(productObj));
        
        // changed the value of hashCode to 8 by changing the name
        productObj.setProductName("Product-9");
        out.println("productObj.setProductName(\"Product-9\") > value: " + map.get(productObj));
        
        // both hashCode() & equals() succeeds
        productObj.setProductName("product");
        out.println("productObj.setProductName(\"product\") > value: " + map.get(productObj));
        
        // hashCode() succeeds but equals() test fails
        productObj.setProductName("pencil");
        out.println("productObj.setProductName(\"pencil\") > value: " + map.get(productObj));
        
        
        // User class didn't override hashCode() and equals()
        map.put(new User(), "User key");
        out.println("\n5. object/String pair > new User()/ \"User key\"; \nvalue: " + map.get(new User()));//null
        
        out.println("\n6. Map size: " + map.size());
	}
	
	
	
	void mapGetOrDefault(){
		
		List<ExampleProductDto> prodList = dataCreation();
		Stream<ExampleProductDto> prodStream = prodList.stream();
		Map<String, List<ExampleProductDto>> prodMap = prodStream.collect(Collectors.groupingBy(ExampleProductDto::getProductName));
		out.println("\n\nGroup distinct products by their name using groupingBy()");
		out.println(prodMap);
		
		Optional<String> prodId = Optional.of("Product-1,Product-2,Product-3,Product-4,Product-5,Product-6");
		System.out.println(prodId);
		
		out.println("\nProvide group of records for specific product name");
		out.println("and provide empty record list for non-existing product name");
		prodId.ifPresent(p -> {
			Arrays.asList(p.split(",")).forEach(pId -> {
				out.println(prodMap.getOrDefault(pId, Collections.emptyList()));
			});
		});
		
	}
	
	List<ExampleProductDto> dataCreation(){
		List<ExampleProductDto> exampleProductDtoList = new ArrayList<>();
		exampleProductDtoList.add(new ExampleProductDto(2, "Product-2", 200, Availability.SOLD_OUT));
		exampleProductDtoList.add(new ExampleProductDto(21, "Product-2", 201, Availability.IN_STOCK));
		exampleProductDtoList.add(new ExampleProductDto(5, "Product-5", 500, ExampleProductDto.Availability.IN_STOCK));
		exampleProductDtoList.add(new ExampleProductDto(55, "Product-5", 505, ExampleProductDto.Availability.IN_STOCK));
		exampleProductDtoList.add(new ExampleProductDto(3, "Product-3", 300, Availability.IN_STOCK));
		exampleProductDtoList.add(new ExampleProductDto(1, "Product-1", 100, ExampleProductDto.Availability.IN_STOCK));
		exampleProductDtoList.add(new ExampleProductDto(4, "Product-4", 400, ExampleProductDto.Availability.SOLD_OUT));
		return exampleProductDtoList;
	}
	
	
	
	void exampleProductDtoOverrideEqualsHashCodeTesting(){
		ExampleProductDto exampleProductDto1 = new ExampleProductDto(1, "Product-1", 100);
		ExampleProductDto exampleProductDto2 = new ExampleProductDto(1, "Product-1", 200); 
		ExampleProductDto exampleProductDto3 = new ExampleProductDto(3, "Product-3", 300);
		
		List<ExampleProductDto> exampleProductDtoList = new ArrayList<ExampleProductDto>();
		exampleProductDtoList.add(exampleProductDto1);
		exampleProductDtoList.add(exampleProductDto2);
		exampleProductDtoList.add(exampleProductDto3);
		
		out.println("\n\nOverride both equals and hashCode");
		out.println("ExampleProductDtoList: " + exampleProductDtoList);
		out.println("exampleProductDto1.equals(exampleProductDto2): " + exampleProductDto1.equals(exampleProductDto2));// true
		out.println("exampleProductDto1.equals(exampleProductDto3): " + exampleProductDto1.equals(exampleProductDto3));// false
		
		out.println("\nOverride hashCode");
		out.println("exampleProductDto1.hashCode() == exampleProductDto2.hashCode(): " + (exampleProductDto1.hashCode() == exampleProductDto2.hashCode()));// true
		out.println("exampleProductDto1.hashCode() == exampleProductDto3.hashCode(): " + (exampleProductDto1.hashCode() == exampleProductDto3.hashCode()));// true
		
		out.println("\nWithout overridding both equals and hashCode"); //tested
		out.println("exampleProductDto1.equals(exampleProductDto2): false");// false
		out.println("exampleProductDto1.equals(exampleProductDto3): false");// false
		out.println("exampleProductDto1.hashCode() == exampleProductDto2.hashCode(): false");// false
		out.println("exampleProductDto1.hashCode() == exampleProductDto3.hashCode(): false");// false
	}
	
}

class User {
}
