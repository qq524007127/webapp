package com.sunjee.btms.service;

import java.util.List;

import com.sunjee.btms.bean.BSRecord;
import com.sunjee.btms.bean.Enterprise;
import com.sunjee.btms.bean.Member;

public interface BSRecordService extends SupportService<BSRecord> {
	/**
	 * 通过福位ID查看此福位是否已被捐赠，如果已捐赠则返回:true,否则返回：false
	 * 
	 * @param bsr
	 * @return 以捐赠：true；未捐赠：false
	 */
	boolean getIsSelled(java.io.Serializable blessSeatId);

	List<BSRecord> getUnPayedRSRecodes(String memberId);

	int deleteUnPayedByMember(String id, Member member);

	void saveOrUpdate(BSRecord t);

	int deleteUnPayedByEnterprise(String id, Enterprise enterprise);

	/**
	 * 更新捐赠记录的有效状态
	 * 
	 * @param split
	 * @param b
	 */
	int updatePermitState(String[] split, boolean b);

	/**
	 * 删除未支付的福位
	 * @param split
	 * @return
	 */
	int deleteUnPayedItems(String[] split);
	
	/**
	 * 后去有效的捐赠和租赁总数（即：捐赠有效和租赁未过期）
	 * @return
	 */
	int getPermitCount();
}
