package com.sunjee.btms.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunjee.btms.exception.AppRuntimeException;
import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.PayRecord;
import com.sunjee.btms.bean.Saler;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.PayRecordDao;

@Repository("payRecordDao")
public class PayRecorDaoImpl extends SupportDaoImpl<PayRecord> implements
		PayRecordDao {

	private static final long serialVersionUID = -7802060362212508447L;

	@Override
	public void saveOrUpdate(PayRecord payRecord) {
		getSession().saveOrUpdate(payRecord);
	}

	@Override
	public Date getMinDate() {
		String hql = "Select min(payDate) from PayRecord";
		Object result = createQuery(null, hql, null).uniqueResult();
		if(result == null){
			return null;
		}
		return (Date) result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayRecord> getAllByDate(Pager pager, Date start, Date end,
			Map<String, SortType> sorts) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("from ").append(getTableName()).append(" where 1=1");
		if(start != null){
			param.put("start", start);
			hql.append(" and payDate >= :start");
		}
		if(end != null){
			param.put("end", end);
			hql.append(" and payDate <= :end");
		}
		hql.append(" ").append(createSortHql(sorts));
		return createQuery(pager, hql.toString(), param).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayRecord> getAllByDateAndSaler(Pager pager, Date start, Date end,
			Saler saler, Map<String, SortType> sorts) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("from ").append(getTableName()).append(" where 1=1");
		if(start != null){
			param.put("start", start);
			hql.append(" and payDate >= :start");
		}
		if(end != null){
			param.put("end", end);
			hql.append(" and payDate <= :end");
		}
		if(saler != null){
            throw new AppRuntimeException("PayRecordDaoImpl.getAllByDateAndSaler()方法中的Saler只能为空");
			/*param.put("mem_saler", saler);
			param.put("enter_saler", saler);
			hql.append(" and (");
			hql.append(" mem.saler = :mem_saler ");
			hql.append(" or enterprise.saler = :enter_saler");
			hql.append(")");*/
		}
		else {
			hql.append(" and mem.saler is null ").append(" and enterprise.saler is null");
		}
		hql.append(" ").append(createSortHql(sorts));
		return createQuery(pager, hql.toString(), param).list();
	}

}
