package com.sunjee.btms.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.PreSell;
import com.sunjee.btms.dao.PreSellDao;

@Repository("preSellDao")
public class PreSellDaoImpl extends SupportDaoImpl<PreSell> implements
		PreSellDao {

	private static final long serialVersionUID = -3551270749190748723L;

}
