package com.sunjee.btms.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.Area;
import com.sunjee.btms.bean.Shelf;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.ShelfService;

@Controller("shelfPlanAction")
@Scope("prototype")
public class ShelfPlanAction extends BaseAction<Shelf> implements
		ModelDriven<Area> {

	private static final long serialVersionUID = -5457531069935958205L;

	private ShelfService shelfService;

	private Area area;
	private List<Shelf> shelfList;
	private String shelfIds[];

	public ShelfService getShelfService() {
		return shelfService;
	}

	@Resource(name = "shelfService")
	public void setShelfService(ShelfService shelfService) {
		this.shelfService = shelfService;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Shelf> getShelfList() {
		return shelfList;
	}

	public void setShelfList(List<Shelf> shelfList) {
		this.shelfList = shelfList;
	}

	public String[] getShelfIds() {
		return shelfIds;
	}

	public void setShelfIds(String[] shelfIds) {
		this.shelfIds = shelfIds;
	}

	@Override
	public String execute() throws Exception {
		shelfList();
		return SUCCESS;
	}

	public String shelfList() {
		Map<String, Object> whereParams = getWhereParams();
		Map<String, SortType> sortParams = getSortParams("postionRow","postionColumn");
		whereParams.put("shelfArea.areaId", area.getAreaId());
		this.shelfList = this.shelfService.getAllByParams(null, whereParams, sortParams);
		if(shelfList == null || shelfList.size() < 1){
			this.area = null;
		}
		else{
			this.area = this.shelfList.get(0).getShelfArea();
		}
		return success();
	}
	
	/**
	 * 禁用福位架，福位架禁用后对应次福位架的福位也将被禁用
	 * @return
	 */
	public String disable(){
		if(shelfIds != null){
			this.shelfService.updateShelfPermit(shelfIds, false);
		}
		return success();
	}
	
	public String enable(){
		if(shelfIds != null){
			this.shelfService.updateShelfPermit(shelfIds, true);
		}
		return success();
	}
	
	public String add(){
		this.shelfService.addByArea(area,area.getAreaRow(),area.getAreaColumn());
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
