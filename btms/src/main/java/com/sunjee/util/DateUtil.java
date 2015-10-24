package com.sunjee.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sunjee.btms.exception.AppRuntimeException;

public class DateUtil {

	public final static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static String getCurrentDateTime() {
		Calendar calendar = Calendar.getInstance();
		return getSimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).format(calendar.getTime());
	}
	
	public static String getCurrentDate(){
		Calendar calendar = Calendar.getInstance();
		return getSimpleDateFormat(DEFAULT_DATE_FORMAT).format(calendar.getTime());
	}
	
	private static SimpleDateFormat getSimpleDateFormat(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf;
	}
	
	public static Date getAfterYears(Date date,int amount){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, amount);
		return calendar.getTime();
	}

	public static String parseString(Date date,String format){
		return getSimpleDateFormat(format).format(date);
	}
	
	/**
	 * 返回某天的开始时间 2015-4-8 00:00:00
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date getStartTimeOfDay(Date date){
		String source = getSimpleDateFormat(DEFAULT_DATE_FORMAT).format(date) + " 00:00:00";
		Date val;
		try {
			val = getSimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).parse(source);
		} catch (ParseException e) {
			throw new AppRuntimeException("格式化时间出错", e);
		}
		return val;
	}
	
	/**
	 * 返回某天的结束时间 2015-4-8 23:59:59
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date getEndTimeOfDay(Date date){
		String source = getSimpleDateFormat(DEFAULT_DATE_FORMAT).format(date) + " 23:59:59";
		Date val;
		try {
			val = getSimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).parse(source);
		} catch (ParseException e) {
			throw new AppRuntimeException("格式化时间出错", e);
		}
		return val;
	}
	
	public static Date parseDateTime(String source){
		try {
			return getSimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new AppRuntimeException("格式化时间出错", e);
		}
	}
	
	public static Date parseDate(String source){
		try {
			return getSimpleDateFormat(DEFAULT_DATE_FORMAT).parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new AppRuntimeException("格式化时间出错", e);
		}
	}
}
