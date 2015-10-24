package com.sunjee.btms.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.Enterprise;
import com.sunjee.btms.dao.EnterpriseDao;

@Repository("enterpriseDao")
public class EnterpriseDaoImpl extends SupportDaoImpl<Enterprise> implements EnterpriseDao {

	private static final long serialVersionUID = 5146055921287940771L;

}
