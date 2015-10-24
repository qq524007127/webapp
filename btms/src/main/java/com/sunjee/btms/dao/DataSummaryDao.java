package com.sunjee.btms.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sunjee.btms.bean.DataSummary;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;

public interface DataSummaryDao extends SupportDao<DataSummary> {

	List<DataSummary> getAllByDate(Pager pager, Date start, Date end, Map<String, Object> whereParams,
                                   Map<String, SortType> sortParams);

}
