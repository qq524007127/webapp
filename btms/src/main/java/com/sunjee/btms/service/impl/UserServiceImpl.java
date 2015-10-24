package com.sunjee.btms.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.common.Constant;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.UserDao;
import com.sunjee.btms.exception.AppRuntimeException;
import com.sunjee.btms.service.UserService;
import com.sunjee.component.bean.Module;
import com.sunjee.component.bean.User;
import com.sunjee.util.MD5Util;

@Service("userService")
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	@Resource(name = "userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public DataGrid<User> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.userDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public User add(User user) {
		Map<String, Object> whereParams = new HashMap<>();
		whereParams.put("userCode", user.getUserCode());
		List<User> users = this.userDao.getEntitys(null, whereParams, null);
		if(users != null && users.size() > 0){
			throw new AppRuntimeException("登陆账号'" + user.getUserCode() + "'已存在，请重新换个登陆账号");
		}
		user.setPassword(MD5Util.getMD5(user.getPassword()));
		return this.userDao.saveEntity(user);
	}

	@Override
	public void update(User user) {
		this.userDao.updateEntity(user);
	}

	@Override
	public void resetPassword(String[] ids) {
		if(ids == null || ids.length < 1){
			return;
		}
		for(String userId : ids){
			this.userDao.changePassword(userId, MD5Util.getMD5(Constant.INIT_PASSWORD));
		}
	}

	@Override
	public User getUserByCodeAndPassword(String userCode, String password) {
		Map<String, Object> whereParams = new HashMap<String, Object>();
		whereParams.put("userCode",userCode);
		whereParams.put("password",MD5Util.getMD5(password));
		whereParams.put("permit",true);
		List<User> userList = this.userDao.getEntitys(null,whereParams, null);
		User user = null;
		if(userList != null && userList.size() > 0){
			user = userList.get(0);
		}
		return user;
	}

	@Override
	public List<Module> getModulesOfUser(User user) {
		List<Module> moduleList = this.userDao.getModulesOfUser(user);
		for(Module parse : moduleList){
			parse.setChildList(new ArrayList<Module>(parse.getChildSet()));
			Collections.sort(parse.getChildList(), new Comparator<Module>(){
				@Override
				public int compare(Module m1, Module m2) {
					return m1.getModuleSort() - m2.getModuleSort();
				}
				
			});
		}
		return moduleList;
	}

	@Override
	public List<User> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.userDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public User getById(String id) {
		return this.userDao.getEntityById(id);
	}

	@Override
	public void delete(User t) {
		this.userDao.deletEntity(t);
	}

	@Override
	public void updatePassword(User user, String newPassword) {
		User u = this.userDao.getEntityById(user.getUserId());
		if(!MD5Util.getMD5(user.getPassword()).equals(u.getPassword())){
			throw new AppRuntimeException("密码不正确，请重新输入密码");
		}
		Map<String, Object> values = new HashMap<>();
		values.put("password", MD5Util.getMD5(newPassword));
		Map<String, Object> params = new HashMap<>();
		params.put("userId", user.getUserId());
		this.userDao.updateEntity(values, params);
	}

}
