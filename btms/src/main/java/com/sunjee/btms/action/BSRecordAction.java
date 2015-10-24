package com.sunjee.btms.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.BSRecord;
import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.BSRecordService;
import com.sunjee.btms.service.BlessSeatService;

@Controller("bsRecordAction")
@Scope("prototype")
public class BSRecordAction extends BaseAction<BSRecord> implements
		ModelDriven<BSRecord> {

	private static final long serialVersionUID = 3961832535184267214L;

	private BSRecordService bsRecordService;
	private BlessSeatService blessSeatService;

	private DataGrid<BlessSeat> blessSeatGrid;

	private BSRecord bsRecord;
	private String memberId;
	private String enterpriseId;
	private String ids;

	public BSRecordService getBsRecordService() {
		return bsRecordService;
	}

	@Resource(name = "bsRecordService")
	public void setBsRecordService(BSRecordService bsRecordService) {
		this.bsRecordService = bsRecordService;
	}

	public BlessSeatService getBlessSeatService() {
		return blessSeatService;
	}

	@Resource(name = "blessSeatService")
	public void setBlessSeatService(BlessSeatService blessSeatService) {
		this.blessSeatService = blessSeatService;
	}

	public DataGrid<BlessSeat> getBlessSeatGrid() {
		return blessSeatGrid;
	}

	public void setBlessSeatGrid(DataGrid<BlessSeat> blessSeatGrid) {
		this.blessSeatGrid = blessSeatGrid;
	}

	public BSRecord getBsRecord() {
		return bsRecord;
	}

	public void setBsRecord(BSRecord bsRecord) {
		this.bsRecord = bsRecord;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 使用此方法时不能同时传'memberId','enterpriseId'两个参数
	 * @return
	 */
	public String grid() {
		Map<String, Object> whereParams = getWhereParams();
		/**
		 * 如果会员ID不为空则查询会员捐赠
		 */
		if(!StringUtils.isEmpty(memberId)){
			whereParams.put("mem.memberId", memberId);
		}
		/**
		 * 如果企业ID不为空则查询企业捐赠
		 */
		if(!StringUtils.isEmpty(enterpriseId)){
			whereParams.put("enterprise.enterId", enterpriseId);
		}
		
		Map<String, SortType> sortParams = getSortParams("payed");
		sortParams.put("permit",SortType.desc);
		sortParams.put("donatOverdue",SortType.desc);
		setDataGrid(this.bsRecordService.getDataGrid(getPager(), whereParams,sortParams));
		return success();
	}
	
	/*public String blessSeatGrid(){
		Map<String, Object> whereParams = getWhereParams();
		whereParams.put("member", new Member(memberId));
		Map<String, SortType> sortParams = getSortParams();
		this.blessSeatGrid = this.blessSeatService.getDataGridOnMember(new Member(memberId),getPager(),whereParams,sortParams);
		return success();
	}*/
	
	public String removeUnPayedItems(){
		if(!StringUtils.isEmpty(ids)){
			this.bsRecordService.deleteUnPayedItems(ids.split(","));
		}
		return success();
	}
	
	public String exitBuyed(){
		if(!StringUtils.isEmpty(ids)){
			this.bsRecordService.updatePermitState(ids.split(","),false);
		}
		return success();
	}

	@Override
	public BSRecord getModel() {
		return null;
	}

}
