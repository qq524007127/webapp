package com.sunjee.btms.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sunjee.btms.bean.Constant;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.ConstantService;

@Controller("constantAction")
@Scope("prototype")
public class ConstantAction extends BaseAction<Constant> {

	private static final long serialVersionUID = -7753584582805200339L;

	private ConstantService constantService;

	private List<Constant> costTypeList;

	public ConstantService getConstantService() {
		return constantService;
	}

	@Resource(name = "constantService")
	public void setConstantService(ConstantService constantService) {
		this.constantService = constantService;
	}

	public List<Constant> getCostTypeList() {
		return costTypeList;
	}

	public void setCostTypeList(List<Constant> costTypeList) {
		this.costTypeList = costTypeList;
	}

	public String costTypeList() {
		Map<String, Object> whereParams = getWhereParams("value");
		whereParams.put("group",com.sunjee.btms.common.Constant.COST_GROUP);
		Map<String, SortType> sortParams = getSortParams();
		this.costTypeList = this.constantService.getAllByParams(null, whereParams, sortParams);
		return success();
	}
}
