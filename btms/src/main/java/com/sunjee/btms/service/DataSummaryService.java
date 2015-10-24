package com.sunjee.btms.service;

import java.util.Date;

import com.sunjee.btms.bean.DataSummary;

public interface DataSummaryService extends SupportService<DataSummary> {
	
	/**
	 * 汇总之前没有汇总的数据（一般只在启动系统时调用）
	 */
	void addSummaryOnBefore();
	
	/**
	 * 汇总某天的数据
	 * @param date
	 * @param isCover	如果已经有统计记录是否覆盖
	 */
	void initSummaryByDate(Date date, boolean cover);
	
	
	/**
	 * 获取一天的数据记录
	 * @param date	结束时间,包括时分秒
	 */
	DataSummary getSumOfDayByEndDate(Date date);
	
	/**
	 * 始终添加当时的记录一个独立事务
	 * @param date 要记录的日期
	 * @param cover 是否覆盖已经存在的数据记录
	 */
	void addSumOfDayAlways(Date date, boolean cover);
}
