package com.sunjee.btms.service;

import java.util.List;
import java.util.Map;

import com.sunjee.btms.bean.PreSell;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.component.bean.User;

public interface PreSellService extends SupportService<PreSell> {

	/**
	 * 会员预售
	 * @param preSell
	 * @param memberId	会员ID
	 * @param	收费员
	 * @return
	 */
	public PreSell addByMember(PreSell preSell, String memberId, User user);
	/**
	 * 企业预售
	 * @param preSell
	 * @param enterpriseId	企业ID
	 * @param	收费员
	 * @return
	 */
	public PreSell addByEnterprise(PreSell preSell, String enterpriseId, User user);
	
	/**
	 * 批量更新预售纪录的有效性
	 * @param ids
	 * @param permit
	 */
	public void updatePermit(String ids[], boolean permit);
	/**
	 * 批量删除订单
	 * @param ids
	 */
	public void deleteByIds(String[] ids);
	
	/**
	 * 福位预定最终补单
	 * @param psId
	 * @param bsIds
	 * @param currentUser
	 */
	public void executeCash(String psId, String[] bsIds, User currentUser);
	
	public List<Object> getParam(String selector, Pager pager, Map<String, Object> whereParams, Map<String, SortType> sortParams);
}
