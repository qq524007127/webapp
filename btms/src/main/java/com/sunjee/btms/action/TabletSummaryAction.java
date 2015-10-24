package com.sunjee.btms.action;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sunjee.btms.bean.DataSummary;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.DataSummaryService;
import com.sunjee.util.DateUtil;
import com.sunjee.util.HqlNoEquals;

@Controller("tabletSummaryAction")
@Scope("prototype")
public class TabletSummaryAction extends BaseAction<DataSummary> {

	private static final long serialVersionUID = 4046080336581276061L;

	private DataSummaryService dataSummaryService;

	private Date startDate;
	private Date endDate;

	public DataSummaryService getDataSummaryService() {
		return dataSummaryService;
	}

	@Resource(name = "dataSummaryService")
	public void setDataSummaryService(DataSummaryService dataSummaryService) {
		this.dataSummaryService = dataSummaryService;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String grid(){
		Map<String, Object> whereParams = getWhereParams();
		if(startDate != null && endDate != null){
			startDate = DateUtil.getStartTimeOfDay(startDate);
			endDate = DateUtil.getEndTimeOfDay(endDate);
			whereParams.put("createDate", new HqlNoEquals(startDate, endDate));
		}
		else if(startDate != null){
			startDate = DateUtil.getStartTimeOfDay(startDate);
			whereParams.put("createDate", new HqlNoEquals(startDate, HqlNoEquals.MORE_EQ));
		}
		else if(endDate != null){
			endDate = DateUtil.getEndTimeOfDay(endDate);
			whereParams.put("createDate", new HqlNoEquals(endDate, HqlNoEquals.LESS_EQ));
		}
		Map<String, SortType> sortParams = getSortParams(SortType.desc,"createDate");
		this.setDataGrid(this.dataSummaryService.getDataGrid(getPager(), whereParams, sortParams));
		return success();
	}
	
}
