package com.sunjee.btms.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.DataSummary;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.DataSummaryDao;

@Repository("dataSummaryDao")
public class DataSummaryDaoImpl extends SupportDaoImpl<DataSummary> implements
		DataSummaryDao {

	private static final long serialVersionUID = 8563373298673137990L;

	@SuppressWarnings("unchecked")
	@Override
	public List<DataSummary> getAllByDate(Pager pager, Date start, Date end, Map<String, Object> whereParams,
			Map<String, SortType> sortParams) {
		if(whereParams == null){
			whereParams = new HashMap<String, Object>();
		}
		StringBuffer hql = new StringBuffer("from ").append(getTableName()).append(" where 1=1");
		if(start != null){
			whereParams.put("start", start);
			hql.append(" and createDate >= :start");
		}
		if(end != null){
			whereParams.put("end", end);
			hql.append(" and createDate <= :end");
		}
		hql.append(" ").append(createSortHql(sortParams));
		return createQuery(pager, hql.toString(), whereParams).list();
	}

}
