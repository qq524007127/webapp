package com.sunjee.btms.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.service.UserService;
import com.sunjee.component.bean.User;

@Controller("loginAction")
@Scope("prototype")
public class LoginAction extends BaseAction<User> implements ModelDriven<User>,
		ServletRequestAware {

	private static final long serialVersionUID = -4119287606729621361L;

	private HttpSession session;

	private UserService userService;

	private User user;
	private String msg;

	public UserService getUserService() {
		return userService;
	}

	@Resource(name = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String login() {
		User u = this.userService.getUserByCodeAndPassword(user.getUserCode(), user.getPassword());
		if(u != null){
			this.user = u;
			this.session.setAttribute("user", user);
			return SUCCESS;
		}
		setMsg("用户名或密码错误，请重新输入!");
		return LOGIN;
	}

	public String loginOut() {
		this.session.invalidate();
		return SUCCESS;
	}

	@Override
	public User getModel() {
		if (user == null) {
			user = new User();
		}
		return user;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.session = request.getSession();
	}

}
