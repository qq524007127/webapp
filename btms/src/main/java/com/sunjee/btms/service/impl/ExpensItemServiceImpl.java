package com.sunjee.btms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.ExpensItem;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.ExpensItemDao;
import com.sunjee.btms.service.ExpensItemService;

@Service("expensItemService")
public class ExpensItemServiceImpl implements ExpensItemService {

	private ExpensItemDao expensItemDao;

	public ExpensItemDao getExpensItemDao() {
		return expensItemDao;
	}

	@Resource(name = "expensItemDao")
	public void setExpensItemDao(ExpensItemDao expensItemDao) {
		this.expensItemDao = expensItemDao;
	}

	@Override
	public DataGrid<ExpensItem> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.expensItemDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public ExpensItem add(ExpensItem t) {
		return this.expensItemDao.saveEntity(t);
	}

	@Override
	public void update(ExpensItem t) {
		this.expensItemDao.updateEntity(t);
	}

	@Override
	public List<ExpensItem> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.expensItemDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public ExpensItem getById(String id) {
		return this.expensItemDao.getEntityById(id);
	}

	@Override
	public void delete(ExpensItem t) {
		this.expensItemDao.deletEntity(t);
	}

}
