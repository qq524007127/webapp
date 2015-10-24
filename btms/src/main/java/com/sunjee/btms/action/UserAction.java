package com.sunjee.btms.action;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.common.Constant;
import com.sunjee.btms.common.Message;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.RoleService;
import com.sunjee.btms.service.UserService;
import com.sunjee.component.bean.Role;
import com.sunjee.component.bean.User;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends BaseAction<User> implements ModelDriven<User> {

	private static final long serialVersionUID = -4119287606729621361L;

	private UserService userService;
	private RoleService roleService;

	private User user;
	private List<Role> roleList;
	private String roleIds[];
	private String userIds;

	public UserService getUserService() {
		return userService;
	}

	@Resource(name = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	@Resource(name = "roleService")
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	@Override
	public String execute() throws Exception {
		this.roleList = this.roleService.getAllRoles();
		return super.execute();
	}

	public String grid() throws Exception {
		setMessage(new Message());
		Map<String, Object> whereParams = getWhereParams();
		Map<String, SortType> sortParams = getSortParams(SortType.desc, "permit");
		this.setDataGrid(this.userService.getDataGrid(getPager(), whereParams,sortParams));
		return SUCCESS;
	}

	public String add() throws Exception {
		if (roleIds != null) {
			Set<Role> roleSet = new HashSet<>();
			for (String roleId : roleIds) {
				Role role = new Role(roleId);
				roleSet.add(role);
			}
			this.user.setRoleSet(roleSet);
		}
		if(StringUtils.isEmpty(user.getPassword())){
			user.setPassword(Constant.INIT_PASSWORD);
		}
		this.userService.add(user);
		setMessage(new Message());
		return SUCCESS;
	}

	public String edit() throws Exception {
		if (roleIds != null) {
			Set<Role> roleSet = new HashSet<>();
			for (String roleId : roleIds) {
				Role role = new Role(roleId);
				roleSet.add(role);
			}
			this.user.setRoleSet(roleSet);
		}
		this.userService.update(user);
		setMessage(new Message());
		return SUCCESS;
	}

	public String resetPassword() throws Exception {
		if(userIds != null){
			String ids[] = userIds.split(",");
			this.userService.resetPassword(ids);
		}
		setMessage(new Message());
		return SUCCESS;
	}

	@Override
	public User getModel() {
		if (this.user == null) {
			user = new User();
		}
		return user;
	}

}
