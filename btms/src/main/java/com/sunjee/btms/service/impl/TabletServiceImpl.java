package com.sunjee.btms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.Tablet;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.TabletDao;
import com.sunjee.btms.service.TabletRecordService;
import com.sunjee.btms.service.TabletService;

@Service("tabletService")
public class TabletServiceImpl implements TabletService {

	private TabletDao tabletDao;
	private TabletRecordService tabletRecordService;

	public TabletDao getTabletDao() {
		return tabletDao;
	}

	@Resource(name = "tabletDao")
	public void setTabletDao(TabletDao tabletDao) {
		this.tabletDao = tabletDao;
	}

	public TabletRecordService getTabletRecordService() {
		return tabletRecordService;
	}

	@Resource(name = "tabletRecordService")
	public void setTabletRecordService(TabletRecordService tabletRecordService) {
		this.tabletRecordService = tabletRecordService;
	}

	@Override
	public DataGrid<Tablet> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.tabletDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public Tablet add(Tablet t) {
		return this.tabletDao.saveEntity(t);
	}

	@Override
	public void update(Tablet t) {
		this.tabletDao.updateEntity(t);
	}

	@Override
	public List<Tablet> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.tabletDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public Tablet getById(String id) {
		return this.tabletDao.getEntityById(id);
	}

	@Override
	public void delete(Tablet t) {
		this.tabletDao.deletEntity(t);
	}

	@Override
	public DataGrid<Tablet> getEnableDataGrid(Pager pager,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.tabletDao.getDataEnableGrid(pager, whereParams, sortParams);
	}

	@Override
	public int getRemainCount() {
		String values[] = new String[]{"count(tabletId)"};
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("permit",true);
		List<Object[]> ls = this.tabletDao.getParams(values, null, param, null);
		Object result = ls.get(0);
		int count = Integer.valueOf(result.toString());
		int buyedCount = this.tabletRecordService.getRemainCount();
		return count - buyedCount;
	}

}
