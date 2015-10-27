package com.sunjee.btms.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.bean.Shelf;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.BlessSeatService;

@Controller("blessSeatPlanAction")
@Scope("prototype")
public class BlessSeatPlanAction extends BaseAction<BlessSeat> implements
		ModelDriven<Shelf> {

	private static final long serialVersionUID = -822152506180393914L;

	private BlessSeatService blessSeatService;
	private Shelf shelf;
	private List<BlessSeat> blessSeatList;
	private String bsIds[];
	private String bsId;
	private String bsCode;
	
	public BlessSeatService getBlessSeatService() {
		return blessSeatService;
	}

	@Resource(name = "blessSeatService")
	public void setBlessSeatService(BlessSeatService blessSeatService) {
		this.blessSeatService = blessSeatService;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	public List<BlessSeat> getBlessSeatList() {
		return blessSeatList;
	}

	public void setBlessSeatList(List<BlessSeat> blessSeatList) {
		this.blessSeatList = blessSeatList;
	}

	public String[] getBsIds() {
		return bsIds;
	}

	public void setBsIds(String[] bsIds) {
		this.bsIds = bsIds;
	}

	public String getBsId() {
		return bsId;
	}

	public void setBsId(String bsId) {
		this.bsId = bsId;
	}

	public String getBsCode() {
		return bsCode;
	}

	public void setBsCode(String bsCode) {
		this.bsCode = bsCode;
	}

	@Override
	public String execute() throws Exception {
		Map<String, Object> whereParams = getWhereParams();
		Map<String, SortType> sortParams = getSortParams("shelf.shelfId","shelfColumn");
		whereParams.put("shelf.shelfId", shelf.getShelfId());
		this.blessSeatList = this.blessSeatService.getAllByParams(null, whereParams, sortParams);
		if(blessSeatList == null || blessSeatList.size() < 1){
			this.shelf = null;
		}
		else{
			this.shelf = this.blessSeatList.get(0).getShelf();
		}
		return success();
	}
	
	/**
	 * 禁用一批福位
	 * @return
	 */
	public String disable(){
		if(bsIds != null){
			this.blessSeatService.updatePermit(bsIds, false);
		}
		return success();
	}
	
	/**
	 * 启用一批福位
	 * @return
	 */
	public String enable(){
		if(bsIds != null){
			this.blessSeatService.updatePermit(bsIds, true);
		}
		return success();
	}
	
	public String add(){
		this.blessSeatService.addByShelf(shelf,shelf.getShelfRow(),shelf.getShelfColumn());
		return success();
	}

	/**
	 * 自定义福位编号
	 * @return
	 * @throws Exception
	 */
	public String editCode() throws Exception{
		this.blessSeatService.updateBlessSeatCode(bsId,bsCode);
		return success();
	}
	
	@Override
	public Shelf getModel() {
		if (this.shelf == null) {
			this.shelf = new Shelf();
		}
		return this.shelf;
	}

}
