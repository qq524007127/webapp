package com.sunjee.btms.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sunjee.btms.common.Message;

@Controller("runtimeErrorAction")
@Scope("prototype")
public class RuntimeErrorAction extends BaseAction {

	private static final long serialVersionUID = -4142108412641246153L;

	private Exception exception;

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("出错了");
		Message msg = new Message();
		msg.setSuccess(false);
		msg.setMsg(exception.getMessage());
		setMessage(msg);
		return SUCCESS;
	}
	public String executeRuntimeError() throws Exception {
		//Exception ex = (Exception) ActionContext.getContext().getValueStack().findValue("exception");
		Message msg = new Message();
		msg.setSuccess(false);
		msg.setMsg("操作出错了");
		setMessage(msg);
		return SUCCESS;
	}
}
