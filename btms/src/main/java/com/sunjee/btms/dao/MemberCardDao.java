package com.sunjee.btms.dao;

import com.sunjee.btms.bean.MemberCard;

public interface MemberCardDao extends SupportDao<MemberCard> {
	
	/**
	 * 获取会员证最大编号，若未空则返回"1";
	 * @return
	 */
	String getMaxCardCode();
	
}
