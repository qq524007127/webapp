package com.sunjee.btms.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.PayRecord;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.PayRecordService;

@Controller("enterprisePayRecordAction")
@Scope("prototype")
public class EnterprisePayRecordAction extends BaseAction<PayRecord> implements
		ModelDriven<PayRecord> {

	private static final long serialVersionUID = -4948097632332499540L;

	private PayRecordService payRecordService;
	private PayRecord payRecord;
	private String enterpriseId;

	public PayRecordService getPayRecordService() {
		return payRecordService;
	}

	@Resource(name="payRecordService")
	public void setPayRecordService(PayRecordService payRecordService) {
		this.payRecordService = payRecordService;
	}

	public PayRecord getPayRecord() {
		return payRecord;
	}

	public void setPayRecord(PayRecord payRecord) {
		this.payRecord = payRecord;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	public String grid(){
		Map<String, Object> whereParams = getWhereParams();
		Map<String, SortType> sortParams = getSortParams(SortType.desc,"payDate");
		if(!StringUtils.isEmpty(enterpriseId)){
			whereParams.put("enterprise.enterId", enterpriseId);
		}
		whereParams.put("type", PayRecord.COMMON_TYPE);
		setDataGrid(this.payRecordService.getDataGrid(getPager(), whereParams, sortParams));
		return success();
	}

	@Override
	public PayRecord getModel() {
		if (this.payRecord == null) {
			this.payRecord = new PayRecord();
		}
		return payRecord;
	}

}
