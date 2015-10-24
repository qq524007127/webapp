package com.sunjee.btms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.Constant;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.ConstantDao;
import com.sunjee.btms.service.ConstantService;

@Service("constantService")
public class ConstantServiceImpl implements ConstantService {

	private ConstantDao constantDao;

	public ConstantDao getConstantDao() {
		return constantDao;
	}

	@Resource(name = "constantDao")
	public void setConstantDao(ConstantDao constantDao) {
		this.constantDao = constantDao;
	}

	@Override
	public DataGrid<Constant> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.constantDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public Constant add(Constant t) {
		return this.constantDao.saveEntity(t);
	}

	@Override
	public void update(Constant t) {
		this.constantDao.updateEntity(t);
	}

	@Override
	public List<Constant> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.constantDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public Constant getById(String id) {
		return this.constantDao.getEntityById(id);
	}

	@Override
	public void delete(Constant t) {
		this.constantDao.deletEntity(t);
	}

}
