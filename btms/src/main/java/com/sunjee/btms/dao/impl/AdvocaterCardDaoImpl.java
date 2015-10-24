package com.sunjee.btms.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.AdvocaterCard;
import com.sunjee.btms.dao.AdvocaterCardDao;

@Repository("advocaterCardDao")
public class AdvocaterCardDaoImpl extends SupportDaoImpl<AdvocaterCard>
		implements AdvocaterCardDao {

	private static final long serialVersionUID = -1403027110356946792L;

	@Override
	public String getMaxCardCode() {
		String hql = "select max(cardCode) from AdvocaterCard";
		Object code = createQuery(null, hql, null).uniqueResult();
		if(code == null){
			code = "0";
		}
		return code.toString();
	}

}
