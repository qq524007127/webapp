package com.sunjee.btms.service;

import com.sunjee.btms.bean.Member;

public interface MemberService extends SupportService<Member> {
	
	/**
	 * 更新用户的有效状态
	 * @param member 
	 * @param b
	 */
	void updatePermit(Member member, boolean b);

}
