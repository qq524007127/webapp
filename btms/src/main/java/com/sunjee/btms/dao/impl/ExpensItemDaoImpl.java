package com.sunjee.btms.dao.impl;


import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.ExpensItem;
import com.sunjee.btms.dao.ExpensItemDao;

@Repository("expensItemDao")
public class ExpensItemDaoImpl extends SupportDaoImpl<ExpensItem> implements
		ExpensItemDao {

	private static final long serialVersionUID = 1975454052015564096L;


}
