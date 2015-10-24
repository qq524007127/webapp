package com.sunjee.btms.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.Area;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.AreaService;

@Controller("areaPlanAction")
@Scope("prototype")
public class AreaPlanAction extends ActionSupport implements ModelDriven<Area> {

	private static final long serialVersionUID = 4432535879373158317L;

	private AreaService areaService;
	
	private Area area;
	private List<Area> areaList;

	public AreaService getAreaService() {
		return areaService;
	}
	@Resource(name="areaService")
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	public List<Area> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}
	@Override
	public String execute() throws Exception {
		areaList();
		return SUCCESS;
	}
	
	public String areaList(){
		Map<String, SortType> sortParams = new HashMap<String, SortType>();
		sortParams.put("areaName", SortType.asc);
		this.areaList = this.areaService.getAllByParams(null, null, sortParams);
		return SUCCESS;
	}

	@Override
	public Area getModel() {
		if(this.area == null){
			this.area = new Area();
		}
		return this.area;
	}

}
