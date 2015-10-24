package com.sunjee.btms.service;

import java.util.List;

import com.sunjee.component.bean.Role;

public interface RoleService extends SupportService<Role>{

	List<Role> getAllRoles();
	
	/**
	 * 根据角色ID数组批量删除角色
	 * @param split
	 */
	void deleteRoles(String[] ids);
}
