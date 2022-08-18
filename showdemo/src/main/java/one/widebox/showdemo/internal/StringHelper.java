package one.widebox.showdemo.internal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

public class StringHelper {

	private static final NumberFormat PERSENTAGE_FORMAT = new DecimalFormat("0.00%");

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

	private static final SimpleDateFormat DAY_OF_WEEK_FORMAT = new SimpleDateFormat("EEEE", Locale.TAIWAN);

	private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private static final SimpleDateFormat DEFAULT_TIMEMATH_FORMAT = new SimpleDateFormat("MM-dd");

	private static final SimpleDateFormat CHINESE_DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");

	public static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static final SimpleDateFormat LOG_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static final SimpleDateFormat DEFAULT_TIME_ONLY_FORMAT = new SimpleDateFormat("HH:mm:ss");

	private static final SimpleDateFormat DEFAULT_YEAR_FORMAT = new SimpleDateFormat("yyyy");

	private static final SimpleDateFormat DEFAULT_DATE_AND_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final SimpleDateFormat REPORT_TIME_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss");

	public static boolean isEmpty(String text) {
		return StringUtils.isEmpty(text);
	}

	public static boolean isBlank(String text) {
		return StringUtils.isBlank(text);
	}

	public static boolean isNotBlank(String text) {
		return !isBlank(text);
	}

	public static boolean isEquals(String s1, String s2) {
		return new EqualsBuilder().append(s1, s2).isEquals();
	}

	public static String replaceNullToBlank(String s) {
		return s == null ? "" : s;
	}

	public static String replace(String s, String candidate, String replacement) {
		return s == null ? "" : s.replaceAll(candidate, replacement);
	}

	public static String formatDayOfWeek(Date date) {
		return date == null ? "" : DAY_OF_WEEK_FORMAT.format(date);
	}

	public static String formatShortDayOfWeek(Date date) {
		return date == null ? "" : DAY_OF_WEEK_FORMAT.format(date).substring(2);
	}

	public static String format(Date date) {
		return date == null ? "" : DEFAULT_DATE_FORMAT.format(date);
	}

	public static String formatMathTime(Date date) {
		return date == null ? "" : DEFAULT_TIMEMATH_FORMAT.format(date);
	}

	public static String formatChinese(Date date) {
		return date == null ? "" : CHINESE_DATE_FORMAT.format(date);
	}

	public static String formatTime(Date date) {
		return date == null ? "" : DEFAULT_TIME_FORMAT.format(date);
	}

	public static String formatLogTime(Date date) {
		return date == null ? "" : LOG_TIME_FORMAT.format(date);
	}

	public static String formatYear(Date date) {
		return date == null ? "" : DEFAULT_YEAR_FORMAT.format(date);
	}

	public static String formatDateAndTime(Date date) {
		return date == null ? "" : DEFAULT_DATE_AND_TIME_FORMAT.format(date);
	}

	public static String formatReportTime(Date date) {
		return date == null ? "" : REPORT_TIME_FORMAT.format(date);
	}

	public static String formatTimeOnly(Date date) {
		return date == null ? "" : DEFAULT_TIME_ONLY_FORMAT.format(date);
	}

	public static Date parseDate(String s) {
		try {
			return isBlank(s) ? null : DEFAULT_DATE_FORMAT.parse(s);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date parseDateAndTime(String s) {
		try {
			return isBlank(s) ? null : DEFAULT_DATE_AND_TIME_FORMAT.parse(s);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date parseDateAndTimeToOthers(Date date, String s) {
		try {
			return isBlank(s) ? null : DEFAULT_DATE_AND_TIME_FORMAT.parse(format(date) + " " + s);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date parseTimeToDateAndTimeOfInit(String s) {
		try {
			return isBlank(s) ? null : DEFAULT_DATE_AND_TIME_FORMAT.parse("01-01-2010 " + s);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date parseDateToDateAndTimeOfInit(String s) {
		try {
			return isBlank(s) ? null : DEFAULT_DATE_AND_TIME_FORMAT.parse(s + " " + "00:00");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String formatPersentage(Number number) {
		DecimalFormat percentageFormat = new DecimalFormat("0%");
		return number == null ? "" : percentageFormat.format(number);
	}

	public static Integer parseInt(String s) {
		return Integer.parseInt(s);
	}

	public static Long parseLong(String s) {
		return Long.parseLong(s);
	}

	public static Double parseDouble(String s) {
		return Double.parseDouble(s);
	}

	public static String toString(Number num) {
		return num == null ? "" : num.toString();
	}

	public static String formatPersentage(Double number) {
		return number == null ? "" : PERSENTAGE_FORMAT.format(number);
	}

	public static String formatNum(BigDecimal number) {
		return number == null ? "" : DECIMAL_FORMAT.format(number);
	}

	public static String toUpperCase(String s) {
		return s == null ? "" : s.toUpperCase();
	}

	public static String toLowerCase(String s) {
		return s == null ? "" : s.toLowerCase();
	}

	public static String trim(String s) {
		return s == null ? "" : s.trim();
	}

	private static SecureRandom secureRandom = new SecureRandom();

	public static String generateRandomDecimalString(int length) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int nextInt = secureRandom.nextInt(10);
			buffer.append(nextInt);
		}
		return buffer.toString();
	}

	// 足夠安全，2^80 = 1208925819614629174706176
	// 返回 16位字符串，每一位是base32數字(0-9,a-v)
	public static String generateRandomString() {
		return new BigInteger(80, secureRandom).toString(32);
	}

	public static String getUploadFileType(String fileName) {
		int index = fileName.lastIndexOf(".");
		return index < 0 ? "" : fileName.substring(index + 1).toLowerCase();
	}
}