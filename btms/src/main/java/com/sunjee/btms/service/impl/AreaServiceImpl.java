package com.sunjee.btms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.Area;
import com.sunjee.btms.bean.Shelf;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.AreaDao;
import com.sunjee.btms.exception.AppRuntimeException;
import com.sunjee.btms.service.AreaService;
import com.sunjee.btms.service.ShelfService;

@Service("areaService")
public class AreaServiceImpl implements AreaService {

	private AreaDao areaDao;

	private ShelfService shelfService;

	public AreaDao getAreaDao() {
		return areaDao;
	}

	@Resource(name = "areaDao")
	public void setAreaDao(AreaDao areaDao) {
		this.areaDao = areaDao;
	}

	public ShelfService getShelfService() {
		return shelfService;
	}

	@Resource(name = "shelfService")
	public void setShelfService(ShelfService shelfService) {
		this.shelfService = shelfService;
	}

	@Override
	public DataGrid<Area> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return areaDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public Area add(Area area) {
		Map<String, Object> whereParams = new HashMap<>();
		whereParams.put("areaName", area.getAreaName());
		List<Area> areas = this.areaDao.getEntitys(null, whereParams, null);
		if(areas != null && areas.size() > 0){
			throw new AppRuntimeException("区域名称不能重复");
		}
		initArea(area);
		return area;
	}

	@Override
	public void initArea(Area area) {
		this.areaDao.saveEntity(area);
		for (int i = 0; i < area.getAreaRow(); i++) {
			for (int j = 0; j < area.getAreaColumn(); j++) {
				Shelf shelf = new Shelf();
				shelf.setShelfRow(area.getShelfRow());
				shelf.setShelfColumn(area.getShelfColumn());
				shelf.setPostionRow(i + 1);
				shelf.setPostionColumn(j + 1);
				shelf.setShelfArea(area);
				shelf.createShelfCode();
				shelf.setPermit(true);
				this.shelfService.initShelf(shelf);
			}
		}
	}

	@Override
	public void update(Area area) {
		this.areaDao.updateEntity(area);
	}

	@Override
	public List<Area> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.areaDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public Area getById(String id) {
		return this.areaDao.getEntityById(id);
	}

	@Override
	public void delete(Area area) {
		this.areaDao.deletEntity(area);
	}

	@Override
	public void addRow(Area area, int areaRow, boolean shelfPermit) {
		area = this.areaDao.getEntityById(area.getAreaId());
		if(area.getAreaRow() >= areaRow){
			return;
		}
		if((areaRow - area.getAreaRow()) > 1){
			throw new AppRuntimeException("不能跨行添加福位架");
		}
		area.setAreaRow(areaRow);
		this.areaDao.updateEntity(area);
		for (int j = 0; j < area.getAreaColumn(); j++) {
			Shelf shelf = new Shelf();
			shelf.setShelfRow(area.getShelfRow());
			shelf.setShelfColumn(area.getShelfColumn());
			shelf.setPostionRow(areaRow);
			shelf.setPostionColumn(j + 1);
			shelf.setShelfArea(area);
			shelf.createShelfCode();
			shelf.setPermit(shelfPermit);
			Map<String, Object> whereParams = new HashMap<String, Object>();
			whereParams.put("shelfCode", shelf.getShelfCode());
			if(this.shelfService.getAllByParams(null, whereParams, null).size() > 0){
				continue;	//如果已存在则跳出不用添加
			}
			this.shelfService.add(shelf);
		}
	}

	@Override
	public void addColumn(Area area, int areaColumn, boolean shelfPermit) {
		area = this.areaDao.getEntityById(area.getAreaId());
		if(area.getAreaColumn() >= areaColumn){
			return;
		}
		if((areaColumn - area.getAreaColumn()) > 1){
			throw new AppRuntimeException("不能跨列添加福位架");
		}
		area.setAreaColumn(areaColumn);
		this.areaDao.updateEntity(area);
		for (int j = 0; j < area.getAreaRow(); j++) {
			Shelf shelf = new Shelf();
			shelf.setShelfRow(area.getShelfRow());
			shelf.setShelfColumn(area.getShelfColumn());
			shelf.setPostionRow(j + 1);
			shelf.setPostionColumn(areaColumn);
			shelf.setShelfArea(area);
			shelf.createShelfCode();
			shelf.setPermit(shelfPermit);
			Map<String, Object> whereParams = new HashMap<String, Object>();
			whereParams.put("shelfCode", shelf.getShelfCode());
			if(this.shelfService.getAllByParams(null, whereParams, null).size() > 0){
				continue;	//如果已存在则跳出不用添加
			}
			this.shelfService.add(shelf);
		}
	}

}
