package com.sunjee.btms.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.Area;
import com.sunjee.btms.service.ShelfService;

@Controller("initAppAction")
@Scope("prototype")
public class InitAppAction extends BaseAction implements ModelDriven<Area>{

	private static final long serialVersionUID = -4785173734437008811L;

	private ShelfService shelfService;

	private Area area;

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

	@Override
	public String execute() throws Exception {
		return super.execute();
	}
	
	public String initShelfByArea(){
		//this.shelfService.initShelfByArea(area);
		return success();
	}

	@Override
	public Area getModel() {
		if(this.area == null){
			this.area = new Area();
		}
		return this.area;
	}
}
