package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java_1920_Time {

	public static void main(String[] args) throws IOException {
		new Java_1920_Time().compareDate();
		new Java_1920_Time().dateOperation();
		new Java_1920_Time().generateDates();
	}
	
	
	private void compareDate() {
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		LocalDate ldate1 = LocalDate.now();
		out.println("Current Date Time = " + ldate1);
		
		
		LocalDateTime specificDate1 = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30);
		out.println("Specific Date Time = " + dtf2.format(specificDate1));
		
		
		
		LocalTime ltime1 = LocalTime.now();
		out.println("Current Time = " + ltime1);
		
		LocalTime time2 = LocalTime.parse("06:30:31");
		out.println("Current Time = " + time2);
		
		LocalTime time3 = LocalTime.of(6, 30, 31);
		out.println("Current Time = " + time3);
		
		
		String dt = dtf2.format(LocalDateTime.of(ldate1.getYear(), ldate1.getMonth(), ldate1.getDayOfMonth(), time2.getHour(), time2.getMinute(), time2.getSecond()));
		out.println("Custom Date Time = " + dt);
		
		LocalDateTime today2 = LocalDateTime.of(LocalDate.now(), LocalTime.now());
		out.println("Current Date Time = " + dtf2.format(today2));
		
		LocalDateTime today3 = LocalDateTime.of(ldate1, ltime1);
		out.println("Current Date Time = " + dtf2.format(today3));
		
		
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
        LocalDate date1 = LocalDate.of(2009, 12, 31);
        LocalDate date2 = LocalDate.of(2010, 01, 31);

        out.println("\ndate1 : " + dtf.format(date1));
        out.println("date2 : " + dtf.format(date2));

        out.println("\nIs...");
        if (date1.isAfter(date2)) {
            out.println("Date1 is after Date2");
        }

        if (date1.isBefore(date2)) {
            out.println("Date1 is before Date2");
        }

        if (date1.isEqual(date2)) {
            out.println("Date1 is equal Date2");
        }
        
        out.println("\nCompareTo...");
        if (date1.compareTo(date2) > 0) {
            out.println("Date1 is after Date2");
        } else if (date1.compareTo(date2) < 0) {
            out.println("Date1 is before Date2");
        } else if (date1.compareTo(date2) == 0) {
            out.println("Date1 is equal to Date2");
        } else {
            out.println("How to get here?");
        }
	}
	
	
	private void dateOperation() {
		
		out.println("\n\nYearMonth.now():" + YearMonth.now());
		YearMonth preMonth2 = YearMonth.now().minusMonths(1);
		out.println("preMonth:" + preMonth2);
		out.println("First Day Of Pre Month:" + preMonth2.atDay(1));
		out.println("Last Day Of Pre Month:" + preMonth2.atEndOfMonth());
		
		
		
		//LocalDate now = LocalDate.of(2019, 1, 13);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate now = LocalDate.now();
		Month preMonth = now.minusMonths(1).getMonth();
		out.println("\n\nPre Month: " + preMonth);
		
		LocalDate firstDayOfPreMonth = null;
		if(preMonth.equals(Month.DECEMBER)){
			firstDayOfPreMonth = LocalDate.of(now.getYear()-1, preMonth, 1);
		} else {
			firstDayOfPreMonth = LocalDate.of(now.getYear(), preMonth, 1);
		}
		out.println("first Day Of Pre Month: " + firstDayOfPreMonth.format(formatter));
		
		LocalDate lastDayOfPreMonth = firstDayOfPreMonth.with(lastDayOfMonth());
		out.println("last Day Of Pre Month: " + lastDayOfPreMonth.format(formatter));
		
		
		DateTimeFormatter monYearFor = DateTimeFormatter.ofPattern("yyyyMM");
		out.println("Month Year: " + lastDayOfPreMonth.format(monYearFor));
		
		
		LocalDate date = LocalDate.of(2014, Month.JULY, 16);
		date = date.with(Month.OCTOBER);
		out.println("date: " + date);
		
		LocalDate dateOfFirstMonday = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)); // 2014-07-07
	    out.println("get first day of Month and first Monday in a month: " + dateOfFirstMonday);
		
		LocalDate specificDate = LocalDate.of(2014, 2, 13);
		LocalDate specificDate2 = LocalDate.now();
		LocalDate firstDayOfMonth = specificDate2.with(firstDayOfMonth());
		out.println(firstDayOfMonth);
	}


	private void generateDates() throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String startDateStr = null;
		out.println("\n\nEnter date in YYYYmmDD format, 0 to quit.");
		//startDateStr = br.readLine();
		startDateStr = "2018-05-01";
		out.println("Start Date: " + startDateStr);
		
        
		LocalDate startDate = LocalDate.parse(startDateStr);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        
		Stream<LocalDate> localDateRangeStream = Stream.iterate(startDate, sDate -> sDate.plusDays(1)).limit(60).filter(this::isDateWithinLimit);
		List<String> dateRangeStrList = localDateRangeStream.parallel().map(date -> date.format(formatter).toString()).collect(Collectors.toList());
		out.println("Date Range: " + dateRangeStrList);
		

		
		String startDateStr2 = "2018-06-01";
		LocalDate startDate2 = LocalDate.parse(startDateStr2);

		// Replace isDateWithinLimit with Predicate function Pending
		//Predicate<LocalDate> predicate = !startDate.isAfter(endDate); // expecting date, not boolean
		
		
		// it is required to use limit(n) with steam, otherwise it will iterate in infinite loop
		Stream<LocalDate> localDateRangeStream2 = Stream.iterate(startDate2, sDate -> sDate.plusDays(1)).limit(60).filter(this::isDateWithinLimit);
		localDateRangeStream2.forEach(System.out::println); // It is n't required to convert Date into String in case of Stream;
		
		
		Stream<LocalDate> localDateRangeStream3 = Stream.iterate(startDate2, sDate -> sDate.plusDays(1)).limit(60).filter(this::isDateWithinLimit);
		List<String> dateRangeList4 = new ArrayList<>();
		localDateRangeStream3.forEach(i -> {
			dateRangeList4.add(i.toString());
		});
		out.println(dateRangeList4); // It is mandatory to convert Date into String in case of List;
		
	}
	
	
	public boolean isDateWithinLimit(LocalDate startDate) {
		LocalDate endDate = LocalDate.now();
		if(!startDate.isAfter(endDate)) {
			return true;
		}
		return false;
	}
	
}
