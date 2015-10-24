package com.sunjee.btms.dao;

import java.util.List;
import java.util.Map;

import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.component.bean.BaseBean;

public interface SupportDao<T extends BaseBean> extends java.io.Serializable {
	
	String getTableName();
	
	void updateEntity(T t);
	T saveEntity(T t);
	void deletEntity(T t);
	
	T getEntityById(java.io.Serializable id);

	/**
	 * 自动拼接HQL语句并查询
	 * 
	 * @param params
	 *            查询条件
	 * @return
	 */
	List<T> getEntitys(Pager page, Map<String, Object> whereParams, Map<String, SortType> sortParams);
	
	@Deprecated
	List<T> getEntitys(Pager page, String hql, Map<String, Object> whereParams, Map<String, SortType> sortParams);
	
	List<T> getEntitysByHql(Pager page, String hql, Map<String, Object> whereParams);
	
	DataGrid<T> getDataGrid(Pager page, Map<String, Object> whereParams, Map<String, SortType> sortParams);
	DataGrid<T> getDataGridByHql(Pager page, String hql, Map<String, Object> whereParams);
	
	int executeUpate(Map<String, Object> valueParams, Map<String, Object> whereParams);
	
	int updateEntity(Map<String, Object> values, Map<String, Object> whereParams);
	
	int executeDelete(Map<String, Object> whereParams);
	
	List<Object[]> getParams(String[] selectors, Pager pager, Map<String, Object> whereParams, Map<String, SortType> sortParams);
	List<Object> getParam(String selector, Pager pager, Map<String, Object> whereParams, Map<String, SortType> sortParams);
	
	//Object uniqueResult(String hql);
}
