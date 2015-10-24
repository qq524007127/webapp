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

@Controller("memberPayRecordAction")
@Scope("prototype")
public class MemberPayRecordAction extends BaseAction<PayRecord> implements
		ModelDriven<PayRecord> {

	private static final long serialVersionUID = 8092972820387055909L;

	private PayRecordService payRecordService;

	private String memberId;

	private PayRecord payRecord;

	public PayRecordService getPayRecordService() {
		return payRecordService;
	}
	
	@Resource(name="payRecordService")
	public void setPayRecordService(PayRecordService payRecordService) {
		this.payRecordService = payRecordService;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public PayRecord getPayRecord() {
		return payRecord;
	}

	public void setPayRecord(PayRecord payRecord) {
		this.payRecord = payRecord;
	}
	
	public String grid(){
		Map<String, Object> whereParams = getWhereParams();
		whereParams.put("type", PayRecord.COMMON_TYPE);
		Map<String, SortType> sortParams = getSortParams(SortType.desc,"payDate");
		if(!StringUtils.isEmpty(memberId)){
			whereParams.put("mem.memberId", memberId);
		}
		setDataGrid(this.payRecordService.getDataGrid(getPager(), whereParams, sortParams));
		return success();
	}

	@Override
	public PayRecord getModel() {
		if (this.payRecord == null) {
			this.payRecord = new PayRecord();
		}
		return this.payRecord;
	}

}
