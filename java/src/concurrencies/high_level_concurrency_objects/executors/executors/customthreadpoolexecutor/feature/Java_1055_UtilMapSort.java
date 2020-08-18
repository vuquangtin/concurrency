package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import executors.customthreadpoolexecutor.feature.ExampleProductDto.Availability;

public class Java_1055_UtilMapSort {
	
	public static void main(String[] args) {
		new Java_1055_UtilMapSort().sortDateTime();
		new Java_1055_UtilMapSort().sortAlphaNumericList();
		new Java_1055_UtilMapSort().sortByValue();
		new Java_1055_UtilMapSort().convertMapToList();
		new Java_1055_UtilMapSort().filterSpecificObjFromList();
		new Java_1055_UtilMapSort().getSkippedObjFromList();
		new Java_1055_UtilMapSort().getSpecificObjFromList();
	}
	
	
	private List<ExampleProductDto> createDateTimeList() {
		List<ExampleProductDto> unsortedList = new ArrayList<>();
		unsortedList.add(new ExampleProductDto(002, "20161109102959", 2, Availability.SOLD_OUT));
		unsortedList.add(new ExampleProductDto(200, "20161109102955", 2, Availability.SOLD_OUT));
		unsortedList.add(new ExampleProductDto(020, "20161109103001", 2, Availability.SOLD_OUT));
		unsortedList.add(new ExampleProductDto(022, "20161109105757", 2, Availability.IN_STOCK));
		unsortedList.add(new ExampleProductDto(005, "20161121102459", 2, ExampleProductDto.Availability.IN_STOCK));
		unsortedList.add(new ExampleProductDto(054, "20161121102959", 2, ExampleProductDto.Availability.IN_STOCK));
		unsortedList.add(new ExampleProductDto(003, "20161121102759", 2, Availability.IN_STOCK));
		unsortedList.add(new ExampleProductDto(010, "20161121102459", 2, ExampleProductDto.Availability.IN_STOCK));
		unsortedList.add(new ExampleProductDto(100, "20161009102959", 2, ExampleProductDto.Availability.IN_STOCK));
		unsortedList.add(new ExampleProductDto(001, "20161009104959", 2, ExampleProductDto.Availability.IN_STOCK));
		unsortedList.add(new ExampleProductDto(004, "20161009104959", 2, ExampleProductDto.Availability.SOLD_OUT));
		return unsortedList;
	}
	
	private void filterLatestTimeStamp(List<ExampleProductDto> unsortedList) {
		String date = "20161109";
		String time = "102959"; 
		String dtStr = date.concat(time); 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime formatDateTime = LocalDateTime.parse(dtStr, formatter); 
		System.out.println("After : " + formatDateTime.format(formatter));
		
		Map<Integer, List<ExampleProductDto>> prodMap = unsortedList.stream().collect(Collectors.groupingBy(ExampleProductDto::getProductPrice));
		prodMap.forEach((pName, pl) -> {
			if(!pl.isEmpty() && pl.size()>1){
				Collections.sort(pl, (p1, p2) ->
				LocalDateTime.parse(p2.getProductName(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) 
					.compareTo(LocalDateTime.parse(p1.getProductName(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
			}
			out.println("\n\nsort Date Time List in descending order (swap parameter sequence)");
			out.println(pl);
		}); 
	}
	
	private void sortDateTime() {
		List<ExampleProductDto> unsortedList = createDateTimeList();
		filterLatestTimeStamp(unsortedList);
		//out.println(sortedList.get(0));
	}
	
	
	Map<String, ExampleProductDto> mapCreation(){
		Map<String, ExampleProductDto> unsortMap = new HashMap<>();
		unsortMap.put("Prod-2", new ExampleProductDto(2, "Product-2", 2, Availability.SOLD_OUT));
		unsortMap.put("Prod-22", new ExampleProductDto(22, "Product-22", 22, Availability.IN_STOCK));
		unsortMap.put("Prod-5", new ExampleProductDto(5, "Product-5", 5, ExampleProductDto.Availability.IN_STOCK));
		unsortMap.put("Prod-54", new ExampleProductDto(54, "Product-54", 54, ExampleProductDto.Availability.IN_STOCK));
		unsortMap.put("Prod-3", new ExampleProductDto(3, "Product-3", 3, Availability.IN_STOCK));
		unsortMap.put("Prod-10", new ExampleProductDto(10, "Product-10", 10, ExampleProductDto.Availability.IN_STOCK));
		unsortMap.put("Prod-1", new ExampleProductDto(1, "Product-1", 1, ExampleProductDto.Availability.IN_STOCK));
		unsortMap.put("Prod-4", new ExampleProductDto(4, "Product-4", 4, ExampleProductDto.Availability.SOLD_OUT));
		return unsortMap;
	}
	
	List<ExampleProductDto> createAlphaNumericList(){
		List<ExampleProductDto> unsortList = new ArrayList<>();
		unsortList.add(new ExampleProductDto(2, "P999999999999992", 2, Availability.SOLD_OUT));
		unsortList.add(new ExampleProductDto(200, "P999999999999200", 2, Availability.SOLD_OUT));
		unsortList.add(new ExampleProductDto(20, "P999999999999920", 2, Availability.SOLD_OUT));
		unsortList.add(new ExampleProductDto(22, "P999999999999922", 2, Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(5, "P999999999999995", 2, ExampleProductDto.Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(54, "P999999999999954", 2, ExampleProductDto.Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(3, "P999999999999993", 2, Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(10, "P999999999999910", 2, ExampleProductDto.Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(100, "P999999999999100", 2, ExampleProductDto.Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(1, "P999999999999991", 2, ExampleProductDto.Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(4, "P999999999999994", 2, ExampleProductDto.Availability.SOLD_OUT));
		return unsortList;
	}
	private void sortAlphaNumericList() {
		List<ExampleProductDto> unsortList = createAlphaNumericList();
		Map<Integer, List<ExampleProductDto>> prodMap = unsortList.stream().collect(Collectors.groupingBy(ExampleProductDto::getProductPrice));
		prodMap.forEach((pName, pl) -> {
			if(!pl.isEmpty() && pl.size()>1){
				Collections.sort(pl, (p1, p2) ->
				(p1.getProductName()).compareTo(p2.getProductName()));
			}
			out.println("\n\nsort Alpha Numeric List in increasing order (no change in parameter sequence)");
			out.println(pl);
		});
	}
	
	
	private void sortByValue() {
		
		Map<String, ExampleProductDto> prodMap = mapCreation();
		
		out.println("Unsort Map");
		//Map<String, Integer> prodMap = mapCreation();
		printMap(prodMap);

		// Convert Map to List of Entry object
		List<Map.Entry<String, ExampleProductDto>> list = new LinkedList<>(prodMap.entrySet());

		// Sort list of Entry object using Collections.sort() with Comparator;
		Collections.sort(list, (Map.Entry<String, ExampleProductDto> e1, Map.Entry<String, ExampleProductDto> e2) ->
				(e1.getValue()).compareTo(e2.getValue()));

		// Loop the sorted list and put it into a new insertion order Map LinkedHashMap
		Map<String, ExampleProductDto> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<String, ExampleProductDto> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		// OR
		/*Iterator<Map.Entry<String, ExampleProductDto>> it = list.iterator();
		while (it.hasNext()) {
			Map.Entry<String, ExampleProductDto> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}*/
		
		out.println("\nSort map by value");
		printMap(sortedMap);
	}

	public static <K, V> void printMap(Map<K, V> map) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			out.print("Key: " + entry.getKey() + ", Value: " + entry.getValue());
		}
	}
	
	
	List<ExampleProductDto> createMultiGroupList(){
		List<ExampleProductDto> unsortList = new ArrayList<>();
		unsortList.add(new ExampleProductDto(2, "2", 2, Availability.SOLD_OUT));
		unsortList.add(new ExampleProductDto(200, "200", 2, Availability.SOLD_OUT));
		unsortList.add(new ExampleProductDto(20, "20", 2, Availability.SOLD_OUT));
		unsortList.add(new ExampleProductDto(22, "22", 3, Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(5, "5", 3, ExampleProductDto.Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(54, "54", 3, ExampleProductDto.Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(3, "3", 4, Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(10, "10", 4, ExampleProductDto.Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(100, "100", 4, ExampleProductDto.Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(1, "1", 3, ExampleProductDto.Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(4, "4", 2, ExampleProductDto.Availability.SOLD_OUT));
		return unsortList;
	}
	
	private void convertMapToList() {
		out.println("\n\nConvert map of List of obj into list");
		List<ExampleProductDto> multiGroupList = createMultiGroupList();
		Map<Integer, List<ExampleProductDto>> prodPriceMap = multiGroupList.stream().collect(Collectors.groupingBy(ExampleProductDto::getProductPrice));
		
		out.println("Using values of map");
		List<ExampleProductDto> prodList1 = new ArrayList<>();
		prodPriceMap.values().stream().forEach(val-> { 
			val.forEach(prodList1::add); 
		});
		out.print(prodList1);
		
		out.println("\nUsing entry of map");
		List<ExampleProductDto> prodList2 = new ArrayList<>();
		prodPriceMap.entrySet().stream().forEach(entry-> {
			entry.getValue().forEach(prodList2::add);
		});
		out.print(prodList2);
	}
	
	private void filterSpecificObjFromList() {
		out.println("\n\nFilter specific object from list of objects");
		List<ExampleProductDto> prodList = createMultiGroupList();		
		List<ExampleProductDto> prod1020List = new ArrayList<>(); 
		
		Predicate<ExampleProductDto> predl0 = p -> p.getProductName().equalsIgnoreCase("10"); 
		Predicate<ExampleProductDto> pred20 = p -> p.getProductName().equalsIgnoreCase("20");
		
		if(!prodList.isEmpty() && prodList.stream().filter(predl0).findFirst().isPresent()) { 
			prod1020List.add(prodList.stream().filter(predl0).findFirst().get()); 
		} 
		if(!prodList.isEmpty() && prodList.stream().filter(pred20).findFirst().isPresent()) {
			prod1020List.add(prodList.stream().filter(pred20).findFirst().get());
		}
		out.println(prod1020List);
	}
	
	List<ExampleProductDto> createMultiGroupList2(){
		List<ExampleProductDto> unsortList = new ArrayList<>();
		unsortList.add(new ExampleProductDto(2, "2", 2, Availability.SOLD_OUT));
		unsortList.add(new ExampleProductDto(200, "200", 200, Availability.SOLD_OUT));
		unsortList.add(new ExampleProductDto(20, "20 ", 20, Availability.SOLD_OUT));
		unsortList.add(new ExampleProductDto(3, "3", 3, Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(10, " 10", 10, ExampleProductDto.Availability.IN_STOCK));
		unsortList.add(new ExampleProductDto(100, "100", 100, ExampleProductDto.Availability.IN_STOCK));
		return unsortList;
	}
	private void getSkippedObjFromList() {
		List<ExampleProductDto> prodList = createMultiGroupList();
		Map<Integer, List<ExampleProductDto>> prodPriceMap = prodList.stream().collect(Collectors.groupingBy(ExampleProductDto::getProductPrice));
		
		prodPriceMap.forEach((price, pl) -> {
			out.println("\nBefore skip");
			out.println(pl);
			skipSpecificObjFromList(pl);
		});
		
		List<ExampleProductDto> prodList2 = createMultiGroupList2();
		out.println("\nOriginal List");
		prodList2.forEach((p) -> {
			out.print(p);
		});
		skipSpecificObjFromList(prodList2);
	}
	private void skipSpecificObjFromList(List<ExampleProductDto> prodList) {
		out.println("\nSkip specific object from list of objects");
		List<ExampleProductDto> prod1020List = new ArrayList<>(); 
		Predicate<ExampleProductDto> predl0 = p -> !p.getProductName().trim().equalsIgnoreCase("10"); 
		Predicate<ExampleProductDto> pred20 = p -> !p.getProductName().trim().equalsIgnoreCase("20");
		if(!prodList.isEmpty() && prodList.stream().filter(predl0.or(pred20)).findFirst().isPresent()) { 
			prod1020List = prodList.stream().filter(predl0.and(pred20)).collect(Collectors.toList());
		}
		out.println(prod1020List);
	}
	
	private void getSpecificObjFromList() {
		List<ExampleProductDto> prodList = createMultiGroupList2();
		out.println("\nOriginal List");
		prodList.forEach((p) -> {
			out.print(p);
		});
		findSpecificObjFromList(prodList);
	}
	private void findSpecificObjFromList(List<ExampleProductDto> prodList) {
		out.println("\nFind specific object from list of objects");
		Predicate<ExampleProductDto> predl0 = p -> p.getProductName().trim().equalsIgnoreCase("10"); 
		Predicate<ExampleProductDto> pred20 = p -> p.getProductName().trim().equalsIgnoreCase("20");
		
		// filter the data at the point where the condition is satisfied.
		List<ExampleProductDto> selectedList = new ArrayList<>();
		prodList.stream().filter(predl0.or(pred20)).forEach(p -> {
			selectedList.add(p);
		});
		out.println(selectedList);
		
		List<ExampleProductDto> selectedList2 = new ArrayList<>();
		prodList.stream().forEach(p -> {
			// filter() is just checking the condition; not doing any filter operation
			if(!prodList.isEmpty() && prodList.stream().filter(predl0.or(pred20)).findFirst().isPresent()){
				selectedList2.add(p);
			}
		});
		out.println(selectedList2);
	}

}
