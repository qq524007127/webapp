package com.sunjee.btms.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller("itemSummaryAction")
@Scope("prototype")
public class ItemSummaryAction extends ActionSupport {

	private static final long serialVersionUID = -334067863743511517L;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

}
