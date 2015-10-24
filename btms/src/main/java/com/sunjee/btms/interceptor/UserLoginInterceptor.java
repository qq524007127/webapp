package com.sunjee.btms.interceptor;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sunjee.btms.action.LoginAction;

/**
 * 用户登陆拦截器
 * @author ShenYunjie
 * 
 */
@Controller("userLoginInterceptor")
public class UserLoginInterceptor extends AbstractInterceptor {

	private final static String LOGIN = "login";
	private final static String LOGINED_USER = "user";

	private static final long serialVersionUID = 2417895916470556944L;

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		if (!ServletActionContext.getRequest().isRequestedSessionIdValid()) {
			return LOGIN;
		}
		if (ai.getAction() instanceof LoginAction) {
			return ai.invoke();
		}
		Map<String, Object> session = ai.getInvocationContext().getSession();
		if (session.get(LOGINED_USER) == null) {
			return LOGIN;
		}
		return ai.invoke();
	}

}
