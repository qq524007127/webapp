package com.sunjee.btms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.PayDetail;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.PayDetailDao;
import com.sunjee.btms.service.PayDetailService;

@Service("payDetailService")
public class PayDetailServiceImpl implements PayDetailService {

	private PayDetailDao payDetailDao;

	public PayDetailDao getPayDetailDao() {
		return payDetailDao;
	}

	@Resource(name = "payDetailDao")
	public void setPayDetailDao(PayDetailDao payDetailDao) {
		this.payDetailDao = payDetailDao;
	}

	@Override
	public DataGrid<PayDetail> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.payDetailDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public PayDetail add(PayDetail t) {
		return this.payDetailDao.saveEntity(t);
	}

	@Override
	public void update(PayDetail t) {
		this.payDetailDao.updateEntity(t);
	}

	@Override
	public List<PayDetail> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.payDetailDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public PayDetail getById(String id) {
		return this.payDetailDao.getEntityById(id);
	}

	@Override
	public void delete(PayDetail t) {
		this.payDetailDao.deletEntity(t);
	}
}
