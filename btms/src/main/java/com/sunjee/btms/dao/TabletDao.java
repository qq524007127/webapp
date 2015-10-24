package com.sunjee.btms.dao;

import java.util.Map;

import com.sunjee.btms.bean.Tablet;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;

public interface TabletDao extends SupportDao<Tablet> {

	DataGrid<Tablet> getDataEnableGrid(Pager pager,
                                       Map<String, Object> whereParams, Map<String, SortType> sortParams);

}
