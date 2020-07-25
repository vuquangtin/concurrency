package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.time.ZonedDateTime;
import java.time.ZoneId;

public class Java_1910_TimeAPIZone {
	public static void main(String args[]) {
		Java_1910_TimeAPIZone java8tester = new Java_1910_TimeAPIZone();
		java8tester.testZonedDateTime();
	}

	public void testZonedDateTime() {

		// Get the current date and time
		ZonedDateTime date1 = ZonedDateTime.parse("2007-12-03T10:15:30+05:30[Asia/Karachi]");
		out.println("date1: " + date1);

		ZoneId id = ZoneId.of("Europe/Paris");
		out.println("ZoneId: " + id);

		ZoneId currentZone = ZoneId.systemDefault();
		out.println("CurrentZone: " + currentZone);
	}
}
