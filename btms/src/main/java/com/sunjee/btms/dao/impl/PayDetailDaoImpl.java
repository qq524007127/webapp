package com.sunjee.btms.dao.impl;


import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.PayDetail;
import com.sunjee.btms.dao.PayDetailDao;

@Repository("payDetailDao")
public class PayDetailDaoImpl extends SupportDaoImpl<PayDetail> implements
		PayDetailDao {

	private static final long serialVersionUID = -680374339848395326L;

}
