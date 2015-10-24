package com.sunjee.btms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.bean.Deader;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.DeaderDao;
import com.sunjee.btms.exception.AppRuntimeException;
import com.sunjee.btms.service.BlessSeatService;
import com.sunjee.btms.service.DeaderService;
import com.sunjee.util.HqlNoEquals;

@Service("deaderService")
public class DeaderServiceImpl implements DeaderService {

	private DeaderDao deaderDao;
	
	private BlessSeatService blessSeatService;

	public DeaderDao getDeaderDao() {
		return deaderDao;
	}

	@Resource(name = "deaderDao")
	public void setDeaderDao(DeaderDao deaderDao) {
		this.deaderDao = deaderDao;
	}

	public BlessSeatService getBlessSeatService() {
		return blessSeatService;
	}
	
	@Resource(name = "blessSeatService")
	public void setBlessSeatService(BlessSeatService blessSeatService) {
		this.blessSeatService = blessSeatService;
	}

	@Override
	public DataGrid<Deader> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.deaderDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public Deader add(Deader t) {
		if(t.getBlessSeat() == null){
			throw new AppRuntimeException("福位不能为空");
		}
		BlessSeat bs = this.blessSeatService.getById(t.getBlessSeat().getBsId());
		if(bs.getDeader() != null){
			throw new AppRuntimeException("福位已被使用，请重新选择");
		}
		return this.deaderDao.saveEntity(t);
	}

	@Override
	public void update(Deader t) {
		if(t.getBlessSeat() == null){
			throw new AppRuntimeException("福位不能为空");
		}

		Map<String, Object> params = new HashMap<>();
		params.put("blessSeat.bsId",t.getBlessSeat().getBsId());
		params.put("deadId", new HqlNoEquals(t.getDeadId()));
		List<Deader> list = this.deaderDao.getEntitys(null, params, null);
		if(list != null && list.size() > 0){
			throw new AppRuntimeException("福位已被使用，请重新选择");
		}
		this.deaderDao.updateEntity(t);
	}

	@Override
	public List<Deader> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.deaderDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public Deader getById(String id) {
		return this.deaderDao.getEntityById(id);
	}

	@Override
	public void delete(Deader t) {
		this.deaderDao.deletEntity(t);
	}

	@Override
	public DataGrid<BlessSeat> getEnableUseBlessSeatGrid(Pager pager,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.blessSeatService.getEnableUseBlessSeatGrid(pager, whereParams, sortParams);
	}

	@Override
	public int deleteByIds(String[] ids) {
		int count = 0;
		if(ids == null){
			return count;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		for(String id : ids){
			param.put("deadId", id);
			count += this.deaderDao.executeDelete(param);
		}
		return count;
	}

}
