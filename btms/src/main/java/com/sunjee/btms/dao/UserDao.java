package com.sunjee.btms.dao;

import java.util.List;

import com.sunjee.component.bean.Module;
import com.sunjee.component.bean.User;

public interface UserDao extends SupportDao<User> {

	void changePassword(String userId, String newPassword);

	List<Module> getModulesOfUser(User user);

}
