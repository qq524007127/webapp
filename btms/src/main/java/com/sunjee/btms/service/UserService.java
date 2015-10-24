package com.sunjee.btms.service;

import java.util.List;

import com.sunjee.component.bean.Module;
import com.sunjee.component.bean.User;

public interface UserService extends SupportService<User>{

	void resetPassword(String[] ids);
	
	User getUserByCodeAndPassword(String userCode, String password);
	
	List<Module> getModulesOfUser(User user);

	/**
	 * 用户修改密码
	 */
	void updatePassword(User user, String newPassword);
}
