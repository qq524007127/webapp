package com.sunjee.btms.action;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.AdvocaterCard;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.AdvocaterCardService;

@Controller("advocaterCardAction")
@Scope("prototype")
public class AdvocaterCardAction extends BaseAction<AdvocaterCard> implements
		ModelDriven<AdvocaterCard> {

	private static final long serialVersionUID = 4551394836738438092L;

	private AdvocaterCardService advocaterCardService;
	
	private AdvocaterCard adCard;
	
	public AdvocaterCardService getAdvocaterCarService() {
		return advocaterCardService;
	}

	@Resource(name="advocaterCardService")
	public void setAdvocaterCardService(AdvocaterCardService advocaterCardService) {
		this.advocaterCardService = advocaterCardService;
	}

	public AdvocaterCard getAdCard() {
		return adCard;
	}

	public void setAdCard(AdvocaterCard adCard) {
		this.adCard = adCard;
	}
	
	public String grid(){
		Map<String, Object> whereParams = getWhereParams();
		Map<String,SortType> sortParams = getSortParams(SortType.desc,"createCardDate");
		setDataGrid(this.advocaterCardService.getDataGrid(getPager(), whereParams, sortParams));
		return success();
	}
	
	public String add(){
		this.advocaterCardService.add(adCard);
		return success();
	}
	
	public String edit(){
		this.advocaterCardService.updateValue(adCard);
		return success();
	}

	@Override
	public AdvocaterCard getModel() {
		if (this.adCard == null) {
			this.adCard = new AdvocaterCard();
		}
		return this.adCard;
	}

}
