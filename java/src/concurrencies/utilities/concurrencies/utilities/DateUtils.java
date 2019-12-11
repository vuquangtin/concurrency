package concurrencies.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

public class DateUtils {
	static Logger logger = Logger.getLogger(DateUtils.class.getName());

	public static Date addYears(int years) {
		return addYears(TimeUtils.getCurrentTime(), years);
	}

	public static Date addYears(Date date, int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	public static Date addHours(Date date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hours);
		return cal.getTime();
	}

	/**
	 * Lấy ngày sau cùng của danh sách ngày so với ngày sau cùng đã được lưu
	 * trước đó
	 * 
	 * @param listDatePost
	 * @param currentMaxDate
	 * @return
	 */
	public static Date getAfter(List<Date> listDatePost, Date currentAfter) {
		if (currentAfter == null) {
			currentAfter = listDatePost.get(0);
		}
		for (Date date : listDatePost) {
			if (date != null) {
				if (date.after(currentAfter)) {
					currentAfter = date;
				}
			}
		}
		return currentAfter;
	}

	public static boolean isAfterNow(Date date) {
		return isAfterNow(date, null);
	}

	private static boolean isAfterNow(Date date, Date now) {
		if (now == null) {
			now = DateTime.now().toDate();
		}
		if (now != null) {
			if (date.after(now)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isBeforeNow(Date date) {
		return isBeforeNow(date, null);
	}

	private static boolean isBeforeNow(Date date, Date now) {
		if (now == null) {
			now = DateTime.now().toDate();
		}
		if (now != null) {
			if (date.before(now)) {
				return true;
			}
		}
		return false;
	}

	public static long getDifferenceDays(Date d1, Date d2) {
		long diff = d1.getTime() - d2.getTime();
		diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		if (diff < 0)
			diff = -diff;
		return diff;
	}

	/**
	 * Lấy ngày đầu tiên của danh sách ngày
	 * 
	 * @param listDatePost
	 * @param currentMaxDate
	 * @return
	 */
	public static Date getBefore(List<Date> listDatePost) {
		Date currentBefore = DateTime.now().toDate();
		for (Date date : listDatePost) {
			if (date != null) {
				if (date.before(currentBefore)) {
					currentBefore = date;
				}
			}
		}
		return currentBefore;
	}

	public static int getDayOfMonth() {
		return DateTime.now().getDayOfMonth();
	}

	public static void main(String[] args) {
		Date date = DateTime.now().toDate();
		if (logger.isDebugEnabled())
			logger.debug(date.toString() + " add 3 hours:" + DateUtils.addHours(date, -3));
		Date date2 = DateUtils.addDays(date, -3);
		if (date.after(date2)) {
			if (logger.isDebugEnabled())
				logger.debug(date.toString() + " sau:" + date2.toString());
		} else {
			if (logger.isDebugEnabled())
				logger.debug(date.toString() + " truoc:" + date2.toString());
		}
		List<Date> listDatePost = new ArrayList<Date>();
		listDatePost.add(DateUtils.addDays(date, -1));
		listDatePost.add(DateUtils.addDays(date, 1));
		listDatePost.add(DateUtils.addDays(date, -4));
		listDatePost.add(DateUtils.addDays(date, -7));
		if (logger.isDebugEnabled())
			logger.debug(getBefore(listDatePost) + "");
		if (logger.isDebugEnabled())
			logger.debug("qq:" + getDifferenceDays(listDatePost.get(0), listDatePost.get(1)));
		if (logger.isDebugEnabled())
			logger.debug(DateUtils.addDays(date, 1) + ":" + DateUtils.addDays(date, -11));
		if (logger.isDebugEnabled())
			logger.debug(isAfterNow(DateUtils.addDays(date, 1), DateUtils.addDays(date, -11)) + "");
		if (logger.isDebugEnabled())
			logger.debug(isBeforeNow(DateUtils.addDays(date, 1), DateUtils.addDays(date, -11)) + "");
		if (logger.isDebugEnabled())
			logger.debug(DateUtils.addDays(date, 60) + ":" + DateTime.now().toDate());
		if (logger.isDebugEnabled())
			logger.debug(isAfterNow(DateUtils.addDays(date, 60), DateTime.now().toDate()) + "");
	}
}
