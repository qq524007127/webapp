package com.sunjee.btms.service;

import com.sunjee.btms.bean.MemberCard;

public interface MemberCardService extends SupportService<MemberCard> {

	int updatePermit(MemberCard memberCard, boolean b);

	void deleteAndAdd(MemberCard memberCard);

}
