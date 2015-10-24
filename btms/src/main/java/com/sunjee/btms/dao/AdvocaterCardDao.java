package com.sunjee.btms.dao;

import com.sunjee.btms.bean.AdvocaterCard;

public interface AdvocaterCardDao extends SupportDao<AdvocaterCard> {

	String getMaxCardCode();

}
