package com.sunjee.btms.service;

import java.util.Date;

import com.sunjee.btms.bean.SalesSummary;

public interface SalesSummaryService extends SupportService<SalesSummary> {
	
	/**
	 * 汇总指定时间‘date’之前的数据
	 * @param date	指定时间
	 */
	public void initSummaryOnBefore(Date date);
	
	/**
	 * 汇总指定当天的数据
	 * @param date	需要汇总数据的日期
	 * @param isCover	是否覆盖之前汇总的数据
	 */
	public void initSummaryByDate(Date date, boolean isCover);
	
	
}
