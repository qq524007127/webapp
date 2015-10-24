package com.sunjee.btms.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.TabletRecord;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.TabletRecordDao;
import com.sunjee.btms.service.TabletRecordService;
import com.sunjee.util.HqlNoEquals;

@Service("tabletRecordService")
public class TabletRecordServiceImpl implements TabletRecordService {

	private TabletRecordDao tabletRecordDao;
	
	public TabletRecordDao getTabletRecordDao() {
		return tabletRecordDao;
	}
	
	@Resource(name="tabletRecordDao")
	public void setTabletRecordDao(TabletRecordDao tabletRecordDao) {
		this.tabletRecordDao = tabletRecordDao;
	}

	@Override
	public DataGrid<TabletRecord> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.tabletRecordDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public TabletRecord add(TabletRecord t) {
		return this.tabletRecordDao.saveEntity(t);
	}

	@Override
	public void update(TabletRecord t) {
		this.tabletRecordDao.updateEntity(t);
	}

	@Override
	public List<TabletRecord> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.tabletRecordDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public TabletRecord getById(String id) {
		return this.tabletRecordDao.getEntityById(id);
	}

	@Override
	public void delete(TabletRecord t) {
		this.tabletRecordDao.deletEntity(t);
	}

	@Override
	public boolean getIsSelled(Serializable tabletId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tablet.tabletId", tabletId);
		List<TabletRecord> tlrLsit = this.tabletRecordDao.getEntitys(null, params, null);
		if(tlrLsit == null || tlrLsit.size() < 1){
			return false;	//如果没有捐赠过则返回false,表示未捐赠
		}
		for(TabletRecord tlr : tlrLsit){
			if(tlr.getTlRecOverdue().after(new Date())){
				return true;
			}
		}
		return false;
	}

	@Override
	public int getRemainCount() {
		String values[] = new String[]{"count(*)"};
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tlRecOverdue", new HqlNoEquals(new Date(), HqlNoEquals.MORE));
		List<Object[]> ls = this.tabletRecordDao.getParams(values, null, param, null);
		Object result = ls.get(0);
		return Integer.parseInt(result.toString());
	}

}
