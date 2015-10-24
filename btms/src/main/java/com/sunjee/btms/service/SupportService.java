package com.sunjee.btms.service;

import java.util.List;
import java.util.Map;

import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;

public interface SupportService<T> {

	DataGrid<T> getDataGrid(Pager page, Map<String, Object> whereParams,
                            Map<String, SortType> sortParams);

	T add(T t);

	void update(T t);

	List<T> getAllByParams(Pager page, Map<String, Object> whereParams,
                           Map<String, SortType> sortParams);

	T getById(String id);
	
	void delete(T t);
}
