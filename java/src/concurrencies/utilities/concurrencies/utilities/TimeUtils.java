package concurrencies.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * 
 * @author tinvq
 *         http://stackoverflow.com/questions/8345023/need-to-get-current-timestamp
 *         -in-java
 */
public class TimeUtils {
	static Logger logger = Logger.getLogger(TimeUtils.class.getName());

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd";// HH:mm:ss";
	public static final String DATE_TIME_STAMP = "MM-dd-yyyy_h:mm:ss";
	public static final String DATE_TIME_STAMP_MACOS = "MM-dd-yyyy_h-mm-ss";
	public static final String DATE_FORMAT_ACCESS_TOKEN = "dd MMMM yyyy";// 15
	public static int MILLISECONDS_TO_MINUTES = 60000;
	public static int MILLISECONDS_TO_SECONDS = 1000; // August

	// 2016

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		if (checkCurrent(1, 58, 2, 59) == true) {
			if (logger.isDebugEnabled()) {
				logger.debug("true");
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("false");
			}
		}
		DateTimeFormatter f = DateTimeFormat.forPattern("dd MMMM yyyy");
		DateTime dateTime = f.parseDateTime("15 August 2016");
		// DateTime dateTime = new LocalDate().toDateTime(localTime);
		if (logger.isDebugEnabled()) {
			logger.debug(dateTime.toString());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("isAfterNow:" + dateTime.isAfterNow());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDateFacebook:"
					+ getDateFacebook("2016-05-05T10:18:23+0000"));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("isAfterNow 7:" + new DateTime().plusDays(-7));
		}
		if (logger.isDebugEnabled()) {
			logger.debug(new DateTime().plusDays(-16).toDate() + " --after-->"
					+ new DateTime().plusDays(-7).toDate());
		}
		if (logger.isDebugEnabled()) {
			logger.debug(new DateTime().plusDays(-16).toDate()
					.after(new DateTime().plusDays(-7).toDate())
					+ "");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("after:"
					+ TimeUtils.getCurrentTime().after(
							DateUtils.addDays(TimeUtils.getCurrentTime(), 30)));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("after:"
					+ DateUtils.addDays(TimeUtils.getCurrentTime(), 30).after(
							TimeUtils.getCurrentTime()));
		}
		// getCurrentTime();
		// Date date = TimeUtils
		// .getDateTimeFromLiveTimeStamp("Sunday, November 17, 2013 at 9:50am");
		// if (date != null) {
		// Logs.info(TimeUtils.class, date.toLocaleString());
		// }
		// List<Date> list = new ArrayList<Date>();
		// list.add(TimeUtils
		// .getDateTimeFromLiveTimeStamp("Sunday, November 17, 2013 at
		// 9:50am"));
		// list.add(TimeUtils
		// .getDateTimeFromLiveTimeStamp("Sunday, November 25, 2013 at
		// 9:50am"));
		// list.add(TimeUtils
		// .getDateTimeFromLiveTimeStamp("Sunday, November 13, 2013 at
		// 9:50am"));
		// list.add(TimeUtils
		// .getDateTimeFromLiveTimeStamp("Sunday, November 10, 2013 at
		// 9:50am"));
		// list.add(TimeUtils
		// .getDateTimeFromLiveTimeStamp("Sunday, November 1, 2013 at 9:50am"));
		// Logs.info(TimeUtils.class, "average:" + averageListDate(list));
	}

	public static int toSecond(String timeText) {
		PeriodFormatter pf = new PeriodFormatterBuilder().appendHours()
				.appendSeparator(":").appendMinutes().appendSeparator(":")
				.appendSeconds().toFormatter();

		// Period period = pf.parsePeriod(PeriodFormatter pf = new
		// PeriodFormatterBuilder().
		// appendHours().appendSeparator(":").
		// appendMinutes().appendSeparator(":").
		// appendSeconds().toFormatter();
		if (timeText.contains(":")) {
			int count = timeText.split(":").length;
			if (count == 2) {
				timeText = "00:" + timeText;
			}
		} else {
			timeText = "00:00:" + timeText;
		}
		logger.debug("timeText:" + timeText);
		Period period = pf.parsePeriod(timeText);
		logger.debug(period.toStandardSeconds().getSeconds());// "31:58:00");
		// System.out.println(period.toStandardSeconds().getSeconds());
		return period.toStandardSeconds().getSeconds();
	}

	public static Date plusHours(int hours) {
		return new DateTime().plusHours(hours).toDate();
	}

	public static Date plusDays(int days) {
		return new DateTime().plusDays(days).toDate();
	}

	public static Date plusMonth(int months) {
		return new DateTime().plusMonths(months).toDate();
	}

	public static DateTime getDateTimeInAccessToken(String expirationTime) {
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern(DATE_FORMAT_ACCESS_TOKEN);
		return formatter.parseDateTime(expirationTime);
	}

	public static String getStringFormDateTimeInAccessToken(
			DateTime expirationTime) {
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern(DATE_FORMAT_ACCESS_TOKEN);
		return expirationTime.toString(formatter);
	}

	public static boolean equalNow(DateTime expirationTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String now = sdf.format(DateTime.now().toDate());
		String expiration = sdf.format(expirationTime.toDate());
		// if(logger.isDebugEnabled())
		logger.debug(now);
		// if(logger.isDebugEnabled())
		logger.debug(expiration);
		if (now.equals(expiration))
			return true;
		else
			return false;

	}

	public static String getNow() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	@SuppressWarnings("deprecation")
	public static int getNowHours() {
		Calendar cal = Calendar.getInstance();
		// SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return cal.getTime().getHours();
	}

	@SuppressWarnings("deprecation")
	public static int getNowDate() {
		Calendar cal = Calendar.getInstance();
		// SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return cal.getTime().getDate();
	}

	@SuppressWarnings("deprecation")
	public static int getNowMinutes() {
		Calendar cal = Calendar.getInstance();
		// SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return cal.getTime().getMinutes();
	}

	public static String getNowForFile() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(cal.getTime());
	}

	public static String getFullNowForFile() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("h_mm_ss_dd_MM_yyyy");
		return sdf.format(cal.getTime());
	}

	public static String getCurrentTimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_STAMP);
		return sdf.format(date);
		// java.util.Date date= new java.util.Date();
		// return new TimeStamp(date.getTime()).toString();
	}

	public static String getCurrentTimeStampMacOS() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_STAMP_MACOS);
		return sdf.format(date);
		// java.util.Date date= new java.util.Date();
		// return new TimeStamp(date.getTime()).toString();
	}

	public static boolean checkCurrent(int currentHours, int currentMinutes,
			int crawlHours, int crawlMinutes) {
		// DateTime startTime, endTime;
		// Period p = new Period(startTime, endTime);
		// int hours = p.getHours();
		// int minutes = p.getMinutes();
		if (crawlHours <= 2) {
			crawlHours += crawlHours + 23;
		}
		if (currentHours <= 2)
			currentHours += currentHours + 23;
		int totalCurrentMinutes = currentHours * 60 + currentMinutes;
		int totalcrawlMinutes = crawlHours * 60 + crawlMinutes;
		if (totalCurrentMinutes - totalcrawlMinutes > 60
				|| totalcrawlMinutes - totalCurrentMinutes > 60) {
			return true;
		}
		return false;
	}

	public static long getLongCurrentDate() {
		return System.currentTimeMillis();
	}

	public static Date getCurrentTime() {
		/*
		 * TimeZone timeZone = TimeZone.getTimeZone("UTC"); Calendar calendar =
		 * Calendar.getInstance(timeZone); SimpleDateFormat simpleDateFormat =
		 * new SimpleDateFormat( "EE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		 * simpleDateFormat.setTimeZone(timeZone);
		 * 
		 * Logs.println("Time zone: " + timeZone.getID());
		 * Logs.println("default time zone: " + TimeZone.getDefault().getID());
		 * Logs.println();
		 * 
		 * Logs.println("UTC:     " +
		 * simpleDateFormat.format(calendar.getTime()));
		 * Logs.println("Default: " + calendar.getTime()); return
		 * simpleDateFormat.format(calendar.getTime());
		 */
		return new Date();
	}

	/**
	 * get Datetime for liveTimeStamp facebook Sunday, November 17, 2013
	 * at9:50am
	 * 
	 * @liveTimeStamp Sunday, November 17, 2013 at9:50am
	 */
	public static Date getDateTimeFromLiveTimeStamp(String liveTimeStamp) {
		SimpleDateFormat outgoingFormat = new SimpleDateFormat(
				"EEEE, MMMM dd, yyyy 'at' HH:mm", java.util.Locale.getDefault());
		try {
			Date out = outgoingFormat.parse(liveTimeStamp);
			// Logs.println(outgoingFormat.parse(liveTimeStamp));
			return out;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
		return null;
	}

	// 2016-05-05T10:18:23+0000
	public static Date getDateFacebook(String liveTimeStamp) {
		// String startTime = "2013-12-21T18:30:00+0100";
		SimpleDateFormat incomingFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssZ");
		Date date = null;
		try {
			date = incomingFormat.parse(liveTimeStamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
		return date;
		// SimpleDateFormat outgoingFormat = new
		// SimpleDateFormat(" EEEE, dd MMMM yyyy",
		// java.util.Locale.getDefault());

		// if(logger.isDebugEnabled())
		// logger.debug(outgoingFormat.format(date));
	}

	@SuppressWarnings({ "unused" })
	public static int averageListDate(List<Date> dates_current) {
		List<Date> dates = new ArrayList<Date>();
		dates.add(0, DateTime.now().toDate());
		for (Date date : dates_current) {
			if (date != null) {
				dates.add(date);
			}
		}
		long totalSeconds = 0;
		List<Long> list = new ArrayList<Long>();
		int size = 0;
		for (Date date : dates) {
			if (date != null) {
				list.add(date.getTime() / 1000L);
				size++;
			}
		}
		Collections.sort(list);
		List<Long> listLong = new ArrayList<Long>();
		int index = 1;
		long a;
		for (Long long1 : list) {
			// if (index == 0) {
			// listInteger.add(0);
			// } else {
			if (index < list.size()) {
				a = list.get(index) - list.get(index - 1);
				listLong.add(a);
			}
			// }
			index++;
			// Logs.info(LoginUtils.class,"averageListDate:min:" + long1);
		}
		for (Long item : listLong) {
			// Logs.info(LoginUtils.class,"averageListDate:min:item:" + item);
			totalSeconds += item;
		}
		// for (Date date : dates) {
		// Logs.println("aaa:"+(date.getTime()/ 1000L));
		// totalSeconds += (date.getTime()/ 1000L) -min;
		// Logs.println((date.getTime()/ 1000L) -min);
		// }

		long averageSeconds = totalSeconds / size;
		// Logs.info(TimeUtils.class,
		// "totalSeconds:" + totalSeconds + "\tsize:" + size +
		// "\taverageSeconds:" + averageSeconds);
		// Date averageDate = new Date(averageSeconds * 1000L);
		int days = (int) (averageSeconds / (60 * 60 * 24));
		// Logs.println(averageDate.getDate());
		if (days < 1)
			days = 1;
		return days;
		// return averageDate.getDate();
	}

	public static void sleepInMinutes(int minutes, String message) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("(pleep " + minutes + " minutes) -->message:"
						+ message);
			}
			Thread.sleep(minutes * TimeUtils.MILLISECONDS_TO_MINUTES);
		} catch (InterruptedException e) {
			logger.error("", e);
		}
	}

	public static void sleepInSeconds(int seconds, String message) {
		// if (true)
		// return;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("(pleep " + seconds + " seconds) -->message:"
						+ message);
			}
			Thread.sleep(seconds * TimeUtils.MILLISECONDS_TO_SECONDS);
		} catch (InterruptedException e) {
			logger.error("", e);
		}
	}

	public static void sleepMiliSeconds(int mili_seconds, String message) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("(pleep " + mili_seconds
						+ " mili seconds) -->message:" + message);
			}
			Thread.sleep(mili_seconds);
		} catch (InterruptedException e) {
			logger.error("", e);
		}
	}

	@SuppressWarnings({ "unused" })
	public static int averageListDate2(List<Date> dates_current) {
		List<Date> dates = new ArrayList<Date>();
		dates.add(0, DateTime.now().toDate());
		for (Date date : dates_current) {
			if (date != null) {
				dates.add(date);
			}
		}
		long totalSeconds = 0;
		List<Long> list = new ArrayList<Long>();
		int size = 0;
		for (Date date : dates) {
			if (date != null) {
				list.add(date.getTime() / 1000L);
				size++;
			}
		}
		Collections.sort(list);
		List<Long> listLong = new ArrayList<Long>();
		int index = 1;
		long a;
		for (Long long1 : list) {
			// if (index == 0) {
			// listInteger.add(0);
			// } else {
			if (index < list.size()) {
				a = list.get(index) - list.get(index - 1);
				listLong.add(a);
			}
			// }
			index++;
			// Logs.info(LoginUtils.class,"averageListDate:min:" + long1);
		}
		for (Long item : listLong) {
			// Logs.info(LoginUtils.class,"averageListDate:min:item:" + item);
			totalSeconds += item;
		}
		// for (Date date : dates) {
		// Logs.println("aaa:"+(date.getTime()/ 1000L));
		// totalSeconds += (date.getTime()/ 1000L) -min;
		// Logs.println((date.getTime()/ 1000L) -min);
		// }

		long averageSeconds = totalSeconds / size;
		if (logger.isDebugEnabled())
			logger.debug("totalSeconds:" + totalSeconds + "\tsize:" + size
					+ "\taverageSeconds:" + averageSeconds);
		// Date averageDate = new Date(averageSeconds * 1000L);
		int days = (int) (averageSeconds / (60 * 60 * 24));
		// Logs.println(averageDate.getDate());
		if (days < 1)
			days = 1;
		return days;
		// return averageDate.getDate();
	}

	public static Date fromUnixTimestamp(long timeinfb) {
		java.util.Date time = new java.util.Date((long) timeinfb * 1000);
		SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		// 2018-04-21 01:00:00 GMT+07:00
		jdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		String java_date = jdf.format(time);
		if (logger.isDebugEnabled())
			logger.debug("java_date:" + java_date);
		// SimpleDateFormat formatter = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		// try {
		// return formatter.parse(java_date);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return time;
	}

	public static String formatDateLimit(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");
		return fmt.format(date);
	}
}
