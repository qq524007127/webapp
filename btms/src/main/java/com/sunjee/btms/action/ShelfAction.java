package com.sunjee.btms.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.Shelf;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.ShelfService;

@Controller("shelfAction")
@Scope("prototype")
public class ShelfAction extends BaseAction<Shelf> implements ModelDriven<Shelf> {

	private static final long serialVersionUID = 1421404850588374579L;

	private ShelfService shelfService;

	private Shelf shelf;
	private String shelfIds;

	public ShelfService getShelfService() {
		return shelfService;
	}

	@Resource(name = "shelfService")
	public void setShelfService(ShelfService shelfService) {
		this.shelfService = shelfService;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	public String getShelfIds() {
		return shelfIds;
	}

	public void setShelfIds(String shelfIds) {
		this.shelfIds = shelfIds;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String grid() throws Exception {
		Map<String, SortType> sortParams = getSortParams();
		Map<String, Object> whereParams = getWhereParams();
		whereParams.put("permit", true);
		this.setDataGrid(this.shelfService.getDataGrid(getPager(),whereParams, sortParams));
		return SUCCESS;
	}
	
	public String add() throws Exception {
		shelf.createShelfCode();
		this.shelfService.add(shelf);
		success();
		return SUCCESS;
	}
	
	public String edit() throws Exception {
		shelf.createShelfCode();
		this.shelfService.update(shelf);
		success();
		return SUCCESS;
	}
	
	public String disable() throws Exception {
		if(!StringUtils.isEmpty(shelfIds)){
			this.shelfService.updateShelfPermit(shelfIds.split(","),false);
		}
		return success();
	}
	
	public String enable() throws Exception {
		if(!StringUtils.isEmpty(shelfIds)){
			this.shelfService.updateShelfPermit(shelfIds.split(","),true);
		}
		return success();
	}

	@Override
	public Shelf getModel() {
		if (this.shelf == null) {
			shelf = new Shelf();
		}
		return shelf;
	}

}
