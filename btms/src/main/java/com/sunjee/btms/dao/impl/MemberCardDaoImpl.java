package com.sunjee.btms.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.MemberCard;
import com.sunjee.btms.dao.MemberCardDao;

@Repository("memberCardDao")
public class MemberCardDaoImpl extends SupportDaoImpl<MemberCard> implements
		MemberCardDao {

	private static final long serialVersionUID = 2187371160466690117L;

	@Override
	public String getMaxCardCode() {
		String hql = "select max(cardCode) from MemberCard";
		Object result = createQuery(null, hql, null).uniqueResult();
		if(result == null){
			result = "0";
		}
		return result.toString();
	}

}
