package com.sunjee.btms.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.Saler;
import com.sunjee.btms.bean.SalesSummary;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.SalesSummaryDao;

@Repository("salesSummaryDao")
public class SalesSummaryDaoImpl extends SupportDaoImpl<SalesSummary> implements
		SalesSummaryDao {

	private static final long serialVersionUID = -8089168277718980632L;

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesSummary> getAllByDate(Pager pager, Date start, Date end,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		if (whereParams == null) {
			whereParams = new HashMap<String, Object>();
		}
		StringBuffer hql = new StringBuffer("from ").append(getTableName())
				.append(" where 1=1");
		if (start != null) {
			whereParams.put("start", start);
			hql.append(" and createDate >= :start");
		}
		if (end != null) {
			whereParams.put("end", end);
			hql.append(" and createDate <= :end");
		}
		hql.append(" ").append(createSortHql(sortParams));
		return createQuery(pager, hql.toString(), whereParams).list();
	}

}
