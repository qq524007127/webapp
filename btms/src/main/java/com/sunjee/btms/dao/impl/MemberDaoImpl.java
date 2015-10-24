package com.sunjee.btms.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.Member;
import com.sunjee.btms.dao.MemberDao;

@Repository("memberDao")
public class MemberDaoImpl extends SupportDaoImpl<Member> implements MemberDao {

	private static final long serialVersionUID = -3135108007972693228L;

}
