package com.sunjee.btms.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.PreSellSummary;
import com.sunjee.btms.dao.PreSellSummaryDao;

@Repository("preSellSummaryDao")
public class PreSellSummaryDaoImpl extends SupportDaoImpl<PreSellSummary>
		implements PreSellSummaryDao {

	private static final long serialVersionUID = 5164175335773984570L;

}
