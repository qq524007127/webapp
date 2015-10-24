package com.sunjee.btms.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.service.UserService;
import com.sunjee.component.bean.User;

@Controller("userInfoAction")
@Scope("prototype")
public class UserInfoAction extends BaseAction<User> implements
		ModelDriven<User> {

	private static final long serialVersionUID = 3778815717591161252L;

	private UserService userService;

	private User user;
	
	private String newPassword;

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

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String execute() throws Exception {
		User currentUser = (User) session.get("user");
		this.user = this.userService.getById(currentUser.getUserId());
		return super.execute();
	}
	
	public String editPassword(){
		this.userService.updatePassword(user,newPassword);
		return success();
	}
	
	@Override
	public User getModel() {
		if(this.user == null){
			this.user = new User();
		}
		return this.user;
	}

}
