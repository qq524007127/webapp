package com.sunjee.btms.action;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.Area;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.AreaService;

@Controller("areaAction")
@Scope("prototype")
public class AreaAction extends BaseAction<Area> implements ModelDriven<Area> {

	private static final long serialVersionUID = -2761328364706517215L;

	private AreaService areaService;

	private Area area;

	public AreaService getAreaService() {
		return areaService;
	}

	@Resource(name = "areaService")
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String grid() throws Exception {
		Map<String, SortType> sortParams = this.getSortParams("areaName");
		this.setDataGrid(this.areaService.getDataGrid(getPager(), null, sortParams));
		return success();
	}

	public String add() throws Exception {
		this.areaService.add(area);
		return success();
	}
	
	public String init() throws Exception {
		this.areaService.initArea(area);
		return success();
	}

	public String edit() throws Exception {
		this.areaService.update(area);
		return success();
	}

	@Override
	public Area getModel() {
		if (this.area == null) {
			this.area = new Area();
		}
		return this.area;
	}
}
