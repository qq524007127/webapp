package com.sunjee.btms.service;

import java.util.Map;

import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.bean.Deader;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;

public interface DeaderService extends SupportService<Deader> {
	
	/**
	 * 可添加使用者的福位（已捐赠或租赁且未使用）
	 * @param pager
	 * @param whereParams
	 * @param sortParams
	 * @return
	 */
	DataGrid<BlessSeat> getEnableUseBlessSeatGrid(Pager pager,
                                                  Map<String, Object> whereParams, Map<String, SortType> sortParams);

	/**
	 * 通过ID数组批量删除使用者
	 * @param split
	 * @return
	 */
	int deleteByIds(String[] split);

}
