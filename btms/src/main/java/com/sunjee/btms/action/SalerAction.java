package com.sunjee.btms.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.Saler;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.SalerService;

@Controller("salerAction")
@Scope("prototype")
public class SalerAction extends BaseAction<Saler> implements
		ModelDriven<Saler> {

	private static final long serialVersionUID = 942065927277892938L;

	private Saler saler;
	private List<Saler> allSalerList;
	@Resource(name = "salerService")
	private SalerService salerService;

	public Saler getSaler() {
		return saler;
	}

	public void setSaler(Saler saler) {
		this.saler = saler;
	}

	public List<Saler> getAllSalerList() {
		return allSalerList;
	}

	public void setAllSalerList(List<Saler> allSalerList) {
		this.allSalerList = allSalerList;
	}

	@Override
	public String execute() throws Exception {
		return success();
	}
	
	public String grid() throws Exception{
		Map<String,SortType> sortParams = this.getSortParams(SortType.desc, "permit");
		this.setDataGrid(this.salerService.getDataGrid(getPager(), getWhereParams(), sortParams));
		return success();
	}
	
	public String add() throws Exception{
		this.salerService.add(saler);
		return success();
	}
	
	public String edit() throws Exception{
		this.salerService.update(saler);
		return success();
	}
	
	public String allSalerList(){
		Map<String,Object> whereParams = getWhereParams();
		whereParams.put("permit", true);
		setAllSalerList(this.salerService.getAllByParams(null, whereParams, null));
		return success();
	}

	@Override
	public Saler getModel() {
		if (this.saler == null) {
			this.saler = new Saler();
		}
		return this.saler;
	}

}
