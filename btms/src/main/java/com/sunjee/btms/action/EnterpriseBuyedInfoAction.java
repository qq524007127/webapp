package com.sunjee.btms.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller("enterpriseBuyedInfoAction")
@Scope("prototype")
public class EnterpriseBuyedInfoAction extends ActionSupport {

	private static final long serialVersionUID = 6030604602758298575L;

	private String enterpriseId;

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}
}
