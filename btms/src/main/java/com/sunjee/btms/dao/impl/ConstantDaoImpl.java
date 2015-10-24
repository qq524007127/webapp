package com.sunjee.btms.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.Constant;
import com.sunjee.btms.dao.ConstantDao;

@Repository("constantDao")
public class ConstantDaoImpl extends SupportDaoImpl<Constant> implements
		ConstantDao {

	private static final long serialVersionUID = 1414152967677514964L;

}
