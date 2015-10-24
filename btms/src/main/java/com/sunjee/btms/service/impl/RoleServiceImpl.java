package com.sunjee.btms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.RoleDao;
import com.sunjee.btms.service.RoleService;
import com.sunjee.component.bean.Role;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	private RoleDao roleDao;

	public RoleDao getRoleDao() {
		return roleDao;
	}

	@Resource(name = "roleDao")
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public List<Role> getAllRoles() {
		return this.roleDao.getEntitys(null, null, null);
	}

	@Override
	public DataGrid<Role> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.roleDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public Role add(Role role) {
		return this.roleDao.saveEntity(role);
	}

	@Override
	public void update(Role role) {
		this.roleDao.updateEntity(role);
	}

	@Override
	public List<Role> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.roleDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public Role getById(String id) {
		return this.roleDao.getEntityById(id);
	}

	@Override
	public void delete(Role t) {
		this.roleDao.deletEntity(t);
	}

	@Override
	public void deleteRoles(String[] ids) {
		if(ids == null){
			return;
		}
		for(String id : ids){
			Role r = this.roleDao.getEntityById(id);
			//还未实现
		}
	}
}
