package com.sunjee.btms.service;

import com.sunjee.btms.bean.AdvocaterCard;

public interface AdvocaterCardService extends SupportService<AdvocaterCard> {

	public String getUniqueCode();
	
	public int updateValue(AdvocaterCard adv);
}
