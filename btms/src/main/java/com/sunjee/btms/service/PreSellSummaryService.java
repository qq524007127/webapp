package com.sunjee.btms.service;

import java.util.Date;

import com.sunjee.btms.bean.PreSellSummary;

public interface PreSellSummaryService extends SupportService<PreSellSummary> {
	
	/**
	 * 汇总之前的数据
	 * @param summaryDay
	 */
	public void initSumOfBefore();
	
	/**
	 * 汇总某天的数据，如果已经有数据是否重新汇总
	 * @param day
	 * @param isCover	true:删除重新汇总；false:不重新汇总
	 */
	public void initSummaryByDate(Date day, boolean isCover);
}
