package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;
import static java.util.logging.Logger.getLogger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Java_1990_TimeAPIMisc {

private static final Logger LOGGER = getLogger(Java_1990_TimeAPIMisc.class.getName());
	
	public static void main(String[] args) {
		new Java_1990_TimeAPIMisc().generateDatesPreJava8();
		new Java_1990_TimeAPIMisc().dateFormate();
	}
	
	
	private void generateDatesPreJava8(){
		
		String startDateStr2 = "2018-06-15";
		LocalDate startDate2 = LocalDate.parse(startDateStr2);
		LocalDate endDate2 = LocalDate.now();
		List<String> dateRangeList2 = new ArrayList<>();
		
		while (!startDate2.isAfter(endDate2)) {
		    dateRangeList2.add(startDate2.toString());
		    startDate2 = startDate2.plusDays(1);
		}
		out.println("\nDate Range.......................");
		System.out.println(dateRangeList2);
		
	}
	
	
	private void dateFormate(){
		
		LocalDateTime now2 = LocalDateTime.now();
        System.out.println("Before: " + now2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatDateTime = now2.format(formatter);
        System.out.println("After: " + formatDateTime);
		
		
        out.println("---------------------------");
		String now = "2016-11-09";
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formatDateTime1 = LocalDate.parse(now, formatter1);
        out.println("Before : " + now);
        out.println("After : " + formatDateTime1);
        out.println("After : " + formatDateTime1.format(formatter1));
	}
	
}
