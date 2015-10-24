package com.sunjee.btms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.Saler;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.SalerDao;
import com.sunjee.btms.service.SalerService;

@Service("salerService")
public class SalerServiceImpl implements SalerService {
	
	@Resource(name="salerDao")
	private SalerDao salerDao;

	@Override
	public DataGrid<Saler> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.salerDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public Saler add(Saler t) {
		return this.salerDao.saveEntity(t);
	}

	@Override
	public void update(Saler t) {
		this.salerDao.updateEntity(t);
		
	}

	@Override
	public List<Saler> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.salerDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public Saler getById(String id) {
		return this.salerDao.getEntityById(id);
	}

	@Override
	public void delete(Saler t) {
		this.salerDao.deletEntity(t);
	}

}
