package com.sunjee.btms.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.bean.Deader;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.DeaderService;

@Controller("deaderAction")
@Scope("prototype")
public class DeaderAction extends BaseAction<Deader> implements
		ModelDriven<Deader> {

	private static final long serialVersionUID = -4838577754667135212L;

	private DeaderService deaderService;

	private Deader deader;
	private DataGrid<BlessSeat> enableUseBlessSeatGrid;	//可添加死者的福位列表
	
	private String blessSeatId;
	private String ids;

	public DeaderService getDeaderService() {
		return deaderService;
	}

	@Resource(name = "deaderService")
	public void setDeaderService(DeaderService deaderService) {
		this.deaderService = deaderService;
	}

	public Deader getDeader() {
		return deader;
	}

	public void setDeader(Deader deader) {
		this.deader = deader;
	}

	public DataGrid<BlessSeat> getEnableUseBlessSeatGrid() {
		return enableUseBlessSeatGrid;
	}

	public void setEnableUseBlessSeatGrid(DataGrid<BlessSeat> enableUseBlessSeatGrid) {
		this.enableUseBlessSeatGrid = enableUseBlessSeatGrid;
	}

	public String getBlessSeatId() {
		return blessSeatId;
	}

	public void setBlessSeatId(String blessSeatId) {
		this.blessSeatId = blessSeatId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String grid() {
		Map<String, Object> whereParams = getWhereParams();
		Map<String, SortType> sortParams = getSortParams();
		setDataGrid(this.deaderService.getDataGrid(getPager(), whereParams, sortParams));
		return success();
	}

	public String enableUseBlessSeatGrid() {
		Map<String, Object> whereParams = getWhereParams();
		Map<String, SortType> sortParams = getSortParams();
		this.enableUseBlessSeatGrid = this.deaderService.getEnableUseBlessSeatGrid(getPager(), whereParams, sortParams);
		return success();
	}
	
	public String add() {
		this.deader.setBlessSeat(new BlessSeat(blessSeatId));
		this.deaderService.add(deader);
		return success();
	}
	
	public String edit() {
		this.deader.setBlessSeat(new BlessSeat(blessSeatId));
		this.deaderService.update(deader);
		return success();
	}
	
	public String remove() {
		if(!StringUtils.isEmpty(ids)){
			this.deaderService.deleteByIds(ids.split(","));
		}
		return success();
	}
	
	@Override
	public Deader getModel() {
		if (this.deader == null) {
			this.deader = new Deader();
		}
		return this.deader;
	}

}
