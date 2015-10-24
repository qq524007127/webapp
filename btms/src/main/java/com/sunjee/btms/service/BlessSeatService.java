package com.sunjee.btms.service;

import java.util.Map;

import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.bean.Enterprise;
import com.sunjee.btms.bean.Level;
import com.sunjee.btms.bean.Member;
import com.sunjee.btms.bean.Shelf;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;

public interface BlessSeatService extends SupportService<BlessSeat> {
	void addBlessSeat(BlessSeat bs);

	int updateBlessSeatLeve(String bsIds[], Level level);

	/**
	 * 更新福位架下的福位的有效状态
	 * @param shelfId
	 * @param permit
	 */
	void updatePermitByShelfId(String shelfId, boolean permit);

	DataGrid<BlessSeat> getEnableDataGrid(Pager pager,
                                          Map<String, Object> whereParams, Map<String, SortType> sortParams);

	DataGrid<BlessSeat> getSaledGrid(Member member, Pager pager,
                                     String searchKey, Map<String, SortType> sortParams);
	
	DataGrid<BlessSeat> getSaledGrid(Enterprise enterprise, Pager pager,
                                     String searchKey, Map<String, SortType> sortParams);
	/**
	 * 获取会员捐赠或租赁的的福位
	 * @param member
	 * @param pager
	 * @param whereParams
	 * @param sortParams
	 * @return
	 */
	DataGrid<BlessSeat> getDataGridOnMember(Member member, Pager pager,
                                            Map<String, Object> whereParams, Map<String, SortType> sortParams);
	
	/**
	 * 获取可添加使用者的福位（已捐赠或租赁且未使用的福位）
	 * @param pager
	 * @param whereParams
	 * @param sortParams
	 * @return
	 */
	DataGrid<BlessSeat> getEnableUseBlessSeatGrid(Pager pager,
                                                  Map<String, Object> whereParams, Map<String, SortType> sortParams);
	
	/**
	 * 获取福位剩余数量
	 * @return
	 */
	int getRemainCount();

	/**
	 * 更新一批福位的有效性
	 * @param bsIds
	 * @param b
	 * @return 受影响数
	 */
	int updatePermit(String[] bsIds, boolean b);
	
	/**
	 * 禁用福位，禁用福位后与其关联的使用者将被删除，且捐赠、租赁记录失效
	 * @param bsId
	 * @param flag	//是否同时解除福位对应的使用者和捐赠、租赁记录
	 * @return
	 */
	int updateDisable(String bsId, boolean flag);
	
	/**
	 * 启用福位
	 * @param bsId
	 * @return
	 */
	int updateEnable(String bsId);
	
	/**
	 * 通过一个福位架、所在行及所在列添加一个福位，如果已存在则设置为有效，如果新增则新增的福位架的列或行的其他福位不可用（无效）
	 * @param shelf
	 * @param shelfRow
	 * @param shelfColumn
	 * @return
	 */
	BlessSeat addByShelf(Shelf shelf, int shelfRow, int shelfColumn);
	
	BlessSeat getBlessSeatByBSCode(String bsCode);
}
