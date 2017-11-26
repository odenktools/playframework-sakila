package utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import java.util.Locale;
import java.util.TimeZone;
import org.joda.time.format.ISODateTimeFormat;

public class DateUtils {

	/**
	 * Get Date NOW
	 *
	 * @param pattern  String date pattern (default : yyyy-MM-dd HH:mm:ss)
	 * @return {@link String}
	 */
	public static String getDate(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		if (!StringUtils.isEmpty(pattern)) {
			sdf.applyPattern(pattern);
		} else {
			sdf.applyPattern("yyyy-MM-dd");
		}
		return sdf.format(new Date());
	}

	/**
	 * Convert String To Date
	 *
     * <code>
	 * java.util.Date now = utils.DateUtils.convertStringToDate("31-12-2017", "dd-MM-yyyy HH:mm:ss");
	 * </code>
	 *
	 * @param date String date
	 * @param pattern  date pattern (default : yyyy-MM-dd HH:mm:ss)
	 * @return {@link Date}
	 */
	public static Date convertStringToDate(String date, String pattern) {
		DateFormat sdf = null;
		if (pattern == null) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			sdf = new SimpleDateFormat(pattern);
		}
		try {
			ParsePosition pos = new ParsePosition(0);
			return sdf.parse(date, pos);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Convert Date To String
	 *
     * <code>
	 * Date now = new Date();
	 * String now = utils.DateUtils.date2Str(now, "dd-MM-yyyy HH:mm:ss");
	 * </code>
	 *
	 * @param date Date
	 * @param pattern String date pattern (default : yyyy-MM-dd HH:mm:ss)
	 * @return {@link String}
	 */
	public static String date2Str(Date date, String pattern) {
		if (date == null) { return null; }
		SimpleDateFormat sdf = new SimpleDateFormat();
		if (!StringUtils.isEmpty(pattern)) {
			sdf.applyPattern(pattern);
		} else {
			sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		}
		return sdf.format(date);
	}

    /**
     * Return an ISO 8601 combined date and time string for current date/time
     * <code>
	 * String now = utils.DateUtils.getISO8601StringForDate();
	 *</code>
     * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
     */
    public static String getISO8601StringForDate() {
        DateTime now = new DateTime();
        return getISO8601StringForDate(now);
    }

    /**
     * Return an ISO 8601 combined date and time string for current date/time
     * <code>
	 * String now = utils.DateUtils.getISO8601StringForDate();
	 *</code>
     * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
     */
    public static String getISO8601StringForDate(String idTimeZone) {
        DateTime now = new DateTime();
        return getISO8601StringForDate(idTimeZone, now);
    }
	
    /**
     * Return an ISO 8601 combined date and time string for specified date/time
     * yyyy-MM-ddTHH:mm:ss.SSSTZD
     * @param date Date
     * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
     */
    private static String getISO8601StringForDate(String idTimeZone, DateTime date) {
        DateTimeZone jakartaTimeZone = DateTimeZone.forID(idTimeZone);
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime().withZone(jakartaTimeZone);

        DateTime dateTime = fmt.parseDateTime( date.toString() );
        String iso8601String = dateTime.toString();
        return fmt.print(date);
    }
	
    /**
     * Return an ISO 8601 combined date and time string for specified date/time
     * yyyy-MM-ddTHH:mm:ss.SSSTZD
     * @param date Date
     * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
     */
    private static String getISO8601StringForDate(DateTime date) {
        DateTimeZone jakartaTimeZone = DateTimeZone.forID("Asia/Jakarta");
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime().withZone(jakartaTimeZone);

        DateTime dateTime = fmt.parseDateTime( date.toString() );
        String iso8601String = dateTime.toString();
        return fmt.print(date);
    }
}
