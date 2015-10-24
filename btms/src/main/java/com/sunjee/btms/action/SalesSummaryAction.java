package com.sunjee.btms.action;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.SalesSummary;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.SalesSummaryService;
import com.sunjee.util.DateUtil;
import com.sunjee.util.HqlNoEquals;

@Controller("salesSummaryAction")
@Scope("prototype")
public class SalesSummaryAction extends BaseAction<SalesSummary> implements
		ModelDriven<SalesSummary> {

	private static final long serialVersionUID = 181860791928887857L;

	@Resource(name = "salesSummaryService")
	private SalesSummaryService salesSummaryService;
	private SalesSummary salesSummary;
	private Date startDate;
	private Date endDate;

	public SalesSummaryService getSalesSummaryService() {
		return salesSummaryService;
	}

	public void setSalesSummaryService(SalesSummaryService salesSummaryService) {
		this.salesSummaryService = salesSummaryService;
	}

	public SalesSummary getSalesSummary() {
		return salesSummary;
	}

	public void setSalesSummary(SalesSummary salesSummary) {
		this.salesSummary = salesSummary;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		if(this.startDate != null){
			this.startDate = DateUtil.getStartTimeOfDay(startDate);
		}
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		if(this.endDate != null){
			this.endDate = DateUtil.getEndTimeOfDay(endDate);
		}
		this.endDate = endDate;
	}

	public String grid()throws Exception {
		Map<String,SortType> sortParams = getSortParams();
		Map<String,Object> whereParams = getWhereParams();
		if(!sortParams.containsKey("createDate")){
			sortParams.put("createDate", SortType.desc);
		}
		initStartAndEndValue(whereParams);
		this.setDataGrid(this.salesSummaryService.getDataGrid(getPager(), whereParams, sortParams));
		return success();
	}

	@Override
	public SalesSummary getModel() {
		if (this.salesSummary == null) {
			this.salesSummary = new SalesSummary();
		}
		return this.salesSummary;
	}
	
	private void initStartAndEndValue(Map<String, Object> whereParams){
		if(startDate != null && endDate != null){
			/*startDate = DateUtil.getStartTimeOfDay(startDate);
			endDate = DateUtil.getEndTimeOfDay(endDate);*/
			whereParams.put("createDate", new HqlNoEquals(startDate, endDate));
		}
		else if(startDate != null){
			//startDate = DateUtil.getStartTimeOfDay(startDate);
			whereParams.put("createDate", new HqlNoEquals(startDate, HqlNoEquals.MORE_EQ));
		}
		else if(endDate != null){
			//endDate = DateUtil.getEndTimeOfDay(endDate);
			whereParams.put("createDate", new HqlNoEquals(endDate, HqlNoEquals.LESS_EQ));
		}
	}
}
