package com.sunjee.btms.service;

import java.util.Map;

import com.sunjee.btms.bean.Tablet;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;

public interface TabletService extends SupportService<Tablet> {

	DataGrid<Tablet> getEnableDataGrid(Pager pager,
                                       Map<String, Object> whereParams, Map<String, SortType> sortParams);

	/**
	 * 获取牌位剩余数量
	 * @return
	 */
	int getRemainCount();

}
