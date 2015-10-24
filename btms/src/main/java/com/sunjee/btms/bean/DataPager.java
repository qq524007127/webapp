package com.sunjee.btms.bean;

import java.util.Date;

import com.sunjee.component.bean.BaseBean;

/**
 * 数据汇总分页组件
 * @author ShenYunjie
 *
 */
public class DataPager extends BaseBean {

	private static final long serialVersionUID = 1892001809143420189L;

	private int page;
	private int totalRows;
	private Date startDate;
	private Date endDate;

	public DataPager() {
		super();
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
