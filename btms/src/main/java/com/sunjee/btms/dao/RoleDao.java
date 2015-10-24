package com.sunjee.btms.dao;

import com.sunjee.component.bean.Role;

public interface RoleDao extends SupportDao<Role>{
	/*DataGird<Role> getRoleGrid(Pager page);*/
	void addRole(Role role);
}
