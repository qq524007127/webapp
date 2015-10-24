package com.sunjee.btms.common;

import com.sunjee.component.bean.BaseBean;

public class Pager extends BaseBean {

	private static final long serialVersionUID = 8255971003250296705L;

	private int page;
	private int rows;

	public Pager(int page, int rows) {
		super();
		this.page = page;
		this.rows = rows;
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

	public int getFirstIndex() {
		if (page < 1) {
			page = 1;
		}
		return (page - 1) * rows;
	}

}
