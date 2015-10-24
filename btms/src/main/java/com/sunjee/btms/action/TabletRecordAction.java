package com.sunjee.btms.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.TabletRecord;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.TabletRecordService;

@Controller("tabletRecordAction")
@Scope("prototype")
public class TabletRecordAction extends BaseAction<TabletRecord> implements
		ModelDriven<TabletRecord> {

	private static final long serialVersionUID = 4187111490954178285L;

	private TabletRecordService tabletRecordService;

	private TabletRecord tabletRecord;
	private String memberId;
	private String enterpriseId;

	public TabletRecordService getTabletRecordService() {
		return tabletRecordService;
	}

	@Resource(name = "tabletRecordService")
	public void setTabletRecordService(TabletRecordService tabletRecordService) {
		this.tabletRecordService = tabletRecordService;
	}

	public TabletRecord getTabletRecord() {
		return tabletRecord;
	}

	public void setTabletRecord(TabletRecord tabletRecord) {
		this.tabletRecord = tabletRecord;
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
		
		Map<String, SortType> sortParams = getSortParams();
		if (!sortParams.containsKey("payed")) {
			// sortParams.put("payed", SortType.asc);
		}
		/*
		 * sortParams.put("permit",SortType.desc);
		 * sortParams.put("donatOverdue",SortType.desc);
		 */
		setDataGrid(this.tabletRecordService.getDataGrid(getPager(),
				whereParams, sortParams));
		return success();
	}

	@Override
	public TabletRecord getModel() {
		if (this.tabletRecord == null) {
			this.tabletRecord = new TabletRecord();
		}
		return this.tabletRecord;
	}

}
