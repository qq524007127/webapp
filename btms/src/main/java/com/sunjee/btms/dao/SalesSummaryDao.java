package com.sunjee.btms.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sunjee.btms.bean.Saler;
import com.sunjee.btms.bean.SalesSummary;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;

public interface SalesSummaryDao extends SupportDao<SalesSummary> {

	/**
	 * 根据开始时间、结束时间以及销售人员查询汇总
	 * 
	 * @param pager
	 * @param start
	 * @param end
	 * @param saler
	 * @param whereParams
	 * @param sortParams
	 * @return
	 */
	List<SalesSummary> getAllByDate(Pager pager, Date start, Date end,
                                    Map<String, Object> whereParams, Map<String, SortType> sortParams);
}
