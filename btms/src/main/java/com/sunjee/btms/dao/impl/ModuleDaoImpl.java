package com.sunjee.btms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.ModuleDao;
import com.sunjee.component.bean.Module;
import com.sunjee.util.HqlNullType;

@Repository("moduleDao")
public class ModuleDaoImpl extends SupportDaoImpl<Module> implements ModuleDao {

	private static final long serialVersionUID = -7240927326279582834L;

	@Override
	public Module addModule(Module mod) {
		getSession().persist(mod);
		return mod;
	}

	@Override
	public void updateModule(Module mod) {
		getSession().merge(mod);
	}

	@Override
	public void deleteModule(Module mod) {
		getSession().delete(mod);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Module> getAllRootModule(Map<String, SortType> sortParams) {
		Map<String, Object> whereParams = new HashMap<>();
		whereParams.put("parentModule", HqlNullType.isNull);
		whereParams.put("permit", true);
		return createQuery(null,whereParams, sortParams).list();
	}

	@Override
	public void updatePermitState(String moduleId,boolean permit) {
		String hql = "update Module set permit = :permit where moduleId =:moduleId";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("permit", permit);
		params.put("moduleId", moduleId);
		createQuery(null,hql, params).executeUpdate();
	}
}
