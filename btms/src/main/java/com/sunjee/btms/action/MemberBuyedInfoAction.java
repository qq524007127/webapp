package com.sunjee.btms.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller("memberBuyedInfoAction")
@Scope("prototype")
public class MemberBuyedInfoAction extends ActionSupport {

	private static final long serialVersionUID = 6030604602758298575L;
	
	private String memberId;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Override
	public String execute() throws Exception {
		return super.execute();
	}
}
