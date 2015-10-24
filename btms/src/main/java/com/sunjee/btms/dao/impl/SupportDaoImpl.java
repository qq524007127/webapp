package com.sunjee.btms.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.SupportDao;
import com.sunjee.btms.exception.AppRuntimeException;
import com.sunjee.component.bean.BaseBean;
import com.sunjee.util.GenericTypeUtil;
import com.sunjee.util.HqlUtil;

public class SupportDaoImpl<T extends BaseBean> implements SupportDao<T>{

	private static final long serialVersionUID = -1856809819767706244L;

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}


	/**
	 * 获取满足查询条件的数据总条数
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public float getRecordTotal(String hql, Map<String, Object> params) {
		Query query = getSession().createQuery("select count(*) " + hql);
		initQueryParams(query, params);
		return Float.valueOf(query.uniqueResult().toString());
	}
	
	/**
	 * 查询满足条件的数据总条数
	 * @param whereParams
	 * @return
	 */
	public float getRecordTotal(Map<String,Object> whereParams){
		StringBuffer hql = new StringBuffer("select count(*) ").append(createQueryHql(whereParams));
		Query query = createQuery(null,hql.toString(), whereParams);
		return Float.valueOf(query.uniqueResult().toString());
	}


	@SuppressWarnings("unchecked")
	@Override
	public DataGrid<T> getDataGrid(Pager page,Map<String,Object> whereParams,Map<String, SortType> sortParams) {
		DataGrid<T> dg = new DataGrid<>();
		
		dg.setTotal(getRecordTotal(whereParams));

		String hql = createQueryHql(whereParams, sortParams);
		Query query = createQuery(page,hql, whereParams);
		dg.setRows(query.list());
		return dg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataGrid<T> getDataGridByHql(Pager page, String hql, Map<String, Object> whereParams) {
		DataGrid<T> dg = new DataGrid<>();
		dg.setTotal(getRecordTotal(hql,whereParams));
		
		Query query = createQuery(page, hql, whereParams);
		dg.setRows(query.list());
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntitys(Pager page,Map<String, Object> whereParams,Map<String,SortType> sortParams) {
		return createQuery(page,whereParams,sortParams).list();
	}
	
	/**
	 * 组装hql语句
	 * @param whereParams	{"id":"1001","name":"testName"}
	 * @return	from Entiry where 1=1 and id=:id and name=:name
	 */
	public String createQueryHql(Map<String, Object> whereParams){
		return createQueryHql(whereParams,null);
	}
	
	/**
	 * 组装hql语句,生成的where子句为且关系，即：and
	 * @param whereParams	{"id":"1001","name":"testName"}
	 * @param sortParams	排序的字段（{"id":"asc","name":"desc"}）
	 * @return	from Entiry where 1=1 and id=:id and name=:name order by id asc,name desc
	 */
	public String createQueryHql(Map<String, Object> whereParams,Map<String, SortType> sortParams) {
		StringBuffer hql = new StringBuffer("from ");
		hql.append(getTableName());
		hql.append(" ").append(createWhereHql(whereParams, true));
		hql.append(" ").append(createSortHql(sortParams));
		System.out.println(hql);
		return hql.toString();
	}
	
	/**
	 * 创建一个查询对象
	 * @param hql
	 * @param whereParams
	 * @return
	 */
	public Query createQuery(Pager page,Map<String, Object> whereParams,Map<String,SortType> sortParams) {
		String hql = createQueryHql(whereParams,sortParams);
		return createQuery(page,hql,whereParams);
	}
	
	/**
	 * 创建一个查询对象
	 * @param hql
	 * @param params
	 * @return
	 */
	public Query createQuery(Pager page,String hql, Map<String, Object> whereParams) {
		Query query = getSession().createQuery(hql);
		initQueryParams(query, whereParams);
		if(page != null){
			query.setFirstResult(page.getFirstIndex());
			query.setMaxResults(page.getRows());
		}
		return query;
	}
	
	/**
	 * 初始化查询参数
	 * 
	 * @param query
	 * @param whereParams
	 */
	public void initQueryParams(Query query, Map<String, Object> whereParams) {
		HqlUtil.initQueryParams(query, whereParams);
	}
	
	@Override
	public final String getTableName() {
		return GenericTypeUtil.getGenerParamType(this.getClass()).getSimpleName();
	}

	@Override
	public int executeUpate(Map<String, Object> valueParams,
			Map<String, Object> whereParams) {
		return updateEntity(valueParams,whereParams);
	}

	@Override
	public int updateEntity(Map<String, Object> values, Map<String, Object> whereParams) {
		String hql = createUpdateHql(values,whereParams);
		Query query = createQuery(null,hql,values);
		initQueryParams(query, whereParams);
		return query.executeUpdate();
	}

	@Override
	public void updateEntity(T t) {
		getSession().update(t);
	}

	@Override
	public T saveEntity(T t) {
		getSession().save(t);
		return t;
	}

	@Override
	public List<T> getEntitys(Pager page, String hql,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		
		return null;
	}
	
	/**
	 * 自动组装update语句
	 * @param values
	 * @return update Xxx set xx=:xx,yy=:yy where xx=:xx and zz is null类型
	 */
	public String createUpdateHql(Map<String, Object> values,Map<String, Object> whereParams){
		StringBuffer hql = new StringBuffer("update ").append(getTableName()).append(" set ");
		if (values == null || values.size() < 1){
			throw new AppRuntimeException("更新字段不能为空");
		}
		
		for(String key : values.keySet()){
			if(StringUtils.isEmpty(key))
				throw new AppRuntimeException("更新字段不能为空");
			hql.append(key).append("=:").append(key).append(",");
		}
		hql = new StringBuffer(hql.substring(0, hql.length()-1));
		hql.append(" ").append(createWhereHql(whereParams, true));
		return hql.toString();
	}
	/**
	 * 根据所传参数自动拼接where子句，当isWhere为false时，返回：”and xxx = :xxx“,为true时返回：”where xxx = :xxx“
	 * @param curHql
	 * @param isWhere是否生成where关键字
	 * @return
	 */
	public String createWhereHql(Map<String, Object> whereParams,boolean isWhere){
		return HqlUtil.createWhereHql(whereParams,isWhere);
	}
	
	/**
	 * 根据所传参数自动拼接order by子句，返回格式为：”order by xxx asc,xx desc“
	 * @param sortParams
	 * @return
	 */
	public String createSortHql(Map<String, SortType> sortParams){
		return HqlUtil.createSortHql(sortParams);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntitysByHql(Pager page, String hql,
			Map<String, Object> whereParams) {
		return createQuery(page,hql,whereParams).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getEntityById(Serializable id) {
		return (T) getSession().get(GenericTypeUtil.getGenerParamType(this.getClass()), id);
	}

	@Override
	public void deletEntity(T t) {
		getSession().delete(t);
	}

	@Override
	public int executeDelete(Map<String, Object> whereParams) {
		StringBuffer hql = new StringBuffer("delete ").append(getTableName()).append(" ");
		hql.append(createWhereHql(whereParams, true));
		return createQuery(null, hql.toString(), whereParams).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getParams(String[] selectors, Pager pager,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		if(selectors == null || selectors.length < 1){
			throw new AppRuntimeException("查询出错，查询选择值不能为空");
		}
		StringBuffer hql = new StringBuffer("select ");
		for(String selector : selectors){
			hql.append(selector).append(",");
		}
		if(hql.toString().endsWith(",")){
			hql = new StringBuffer(hql.substring(0, hql.length() - 1));
		}
		hql.append(" from ").append(getTableName()).append(" ");
		hql.append(createWhereHql(whereParams, true));
		hql.append(" ").append(createSortHql(sortParams));
		return createQuery(pager, hql.toString(), whereParams).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getParam(String selector, Pager pager,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		if(StringUtils.isEmpty(selector)){
			throw new AppRuntimeException("查询出错，查询选择值不能为空");
		}
		StringBuffer hql = new StringBuffer("select ").append(selector);
		hql.append(" from ").append(getTableName()).append(" ");
		hql.append(createWhereHql(whereParams, true));
		hql.append(" ").append(createSortHql(sortParams));
		return createQuery(pager, hql.toString(), whereParams).list();
	}
}
