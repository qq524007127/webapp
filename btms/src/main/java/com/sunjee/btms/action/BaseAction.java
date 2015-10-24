package com.sunjee.btms.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Message;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.component.bean.User;
import com.sunjee.util.HqlLikeType;
import com.sunjee.util.LikeType;

public class BaseAction<T> extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = 4964579154676466502L;

	private DataGrid<T> dataGrid;

	private Message message;
	protected int page;
	protected int rows;
	protected String sort;
	protected String order;
	protected String searchKey;
	protected Map<String,Object> session;
	protected String searchName;
	protected String searchValue;
	protected String msg;

	public DataGrid<T> getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(DataGrid<T> dataGrid) {
		this.dataGrid = dataGrid;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	protected String success() {
		setMessage(new Message());
		return SUCCESS;
	}
	
	protected String success(java.io.Serializable attribute) {
		setMessage(new Message());
		this.message.setAttribute(attribute);
		return SUCCESS;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	protected Map<String, SortType> getSortParams(String... sortKeys) {
		return getSortParams(SortType.asc,sortKeys);
	}
	
	protected Map<String, SortType> getSortParams(SortType mSort, String...sortKeys) {
		Map<String, SortType> sortParams = new LinkedHashMap<>();
		if(sortKeys != null){
			for(String sortKey : sortKeys){
				if(StringUtils.isEmpty(sortKey)){
					continue;
				}
				if(sortKey.equals(sort)){
					continue;
				}
				mSort = mSort == null ? SortType.asc : mSort;
				sortParams.put(sortKey, mSort);
			}
		}
		if (!StringUtils.isEmpty(sort)) {
			SortType sortType = order.trim().toLowerCase().equals(SortType.asc.toString()) ? SortType.asc : SortType.desc;
			sortParams.put(sort, sortType);
		}
		return sortParams;
	}
	
	protected Map<String, Object> getWhereParams(){
		return getWhereParams(null);
	}
	
	@Deprecated
	protected Map<String, Object> getWhereParams(String key){
		return getWhereParams(key, LikeType.allLike);
	}

	@Deprecated
	protected Map<String, Object> getWhereParams(String key,LikeType likeType){
		Map<String, Object> whereParams = new HashMap<>();
		if(!StringUtils.isEmpty(searchKey) && !StringUtils.isEmpty(key)){
			if(likeType == null){
				likeType = LikeType.allLike;
			}
			whereParams.put(key, new HqlLikeType(searchKey.trim(), likeType));
		}
		if(!StringUtils.isEmpty(searchName) && !StringUtils.isEmpty(searchValue)){
			whereParams.put(searchName, new HqlLikeType(searchValue, LikeType.allLike));
		}
		return whereParams;
	}

	protected Pager getPager() {
		return new Pager(page, rows);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	/**
	 * 
	 * @return	当前登陆的用户
	 */
	public User getCurrentUser(){
		return (User) session.get("user");
	}
}
