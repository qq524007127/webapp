package com.sunjee.btms.service;

import com.sunjee.btms.bean.Enterprise;

public interface EnterpriseService extends SupportService<Enterprise> {

	/**
	 * 设置企业的有效状态
	 * @param enterprise
	 * @param b
	 */
	void updatePermit(Enterprise enterprise, boolean b);

}
