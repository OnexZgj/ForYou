package com.it.onex.foryou.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理工具类
 */
public class TimeUtil {

//	public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_PATTERN = "yyyy-MM-dd";

	/**
	 * 按照指定格式取当前时间的时间串
	 * <p>
	 * 
	 * @param pattern
	 * @return
	 */
	public static String format(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	public static String format(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}


	public static String format() {
		return format(DEFAULT_PATTERN);
	}

	public static String format(Date date) {
		return format(date, DEFAULT_PATTERN);
	}

	/**
	 * 将指定时间格式的时间串转换成Date对象
	 * <p>
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static Date parse(String time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将默认格式(yyyy-MM-dd HH:mm:ss)的时间串转换成Date对象
	 * <p>
	 * 
	 * @param time
	 * @return
	 */
	public static Date parse(String time) {
		return parse(time, DEFAULT_PATTERN);
	}

	/**
	 * 计算两个时间的时间间隔，单位毫秒（ms）
	 * <p>
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long calcInterval(Date start, Date end) {
		return (end.getTime() - start.getTime());
	}

	/**
	 * 计算两个时间的时间间隔，单位毫秒（ms），相比较的两个时间串格式为:yyyy-MM-dd HH:mm:ss
	 * <p>
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long calcInterval(String start, String end) {
		return calcInterval(parse(start), parse(end));
	}

	/**
	 * 根据传入的初始日期及天数，计算初始日期加、减对应天数后的日期
	 * 
	 * @param date
	 * @param day
	 * @param pattern
	 * @return
	 */
	public static Date getcalcDateByDay(Date date, int day, String pattern) {
		Calendar cDate = Calendar.getInstance();
		cDate.setTime(date);
		cDate.set(Calendar.DATE, cDate.get(Calendar.DATE) + day);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date newDate = null;
		try {
			newDate = sdf.parse(sdf.format(cDate.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	/**
	 * 计算两个日期间隔 返回小时单位 格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static String calcIntervalAsHours(String start, String end) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startDate = format.parse(start);
			Date endDate = format.parse(end);
			long diff = endDate.getTime() - startDate.getTime();
			float hours = diff / 1000 / 60 / 60.0f;
			return new DecimalFormat("0.00").format(hours);// new
															// DecimalFormat("#.0");
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return "";

	}

}
