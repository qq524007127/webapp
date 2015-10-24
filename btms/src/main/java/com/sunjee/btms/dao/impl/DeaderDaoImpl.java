package com.sunjee.btms.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.Deader;
import com.sunjee.btms.dao.DeaderDao;

@Repository("deaderDao")
public class DeaderDaoImpl extends SupportDaoImpl<Deader> implements DeaderDao {

	private static final long serialVersionUID = -3303023005493258054L;

}
