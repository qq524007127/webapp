package com.sunjee.btms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.ModuleDao;
import com.sunjee.btms.service.ModuleService;
import com.sunjee.component.bean.Module;
import com.sunjee.util.HqlNullType;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

	private ModuleDao moduleDao;

	public ModuleDao getModuleDao() {
		return moduleDao;
	}

	@Resource(name = "moduleDao")
	public void setModuleDao(ModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}

	@Override
	public Module add(Module mod) {
		return this.moduleDao.addModule(mod);
	}

	@Override
	public void update(Module mod) {
		this.moduleDao.updateModule(mod);
	}

	public List<Module> getAllByParams(Pager page,Map<String,Object> whereParams, Map<String, SortType> sortParams) {
		return this.moduleDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public DataGrid<Module> getDataGrid(Pager page,Map<String, Object> whereParams,Map<String, SortType> sortParams) {
		return this.moduleDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public List<Module> getAllRootModule() {
		Map<String, SortType> sortParams = new HashMap<>();
		sortParams.put("moduleSort",SortType.asc);
		return this.moduleDao.getAllRootModule(sortParams);
	}

	/**
	 * 禁用模块
	 */
	@Override
	public void updateDisable(String... moduleIds) {
		if (moduleIds == null) {
			return;
		}
		for (String moduleId : moduleIds) {
			this.moduleDao.updatePermitState(moduleId, false);
		}
	}

	/**
	 * 启用模块
	 */
	@Override
	public void updateEnable(String... moduleIds) {
		if (moduleIds == null) {
			return;
		}
		for (String moduleId : moduleIds) {
			this.moduleDao.updatePermitState(moduleId, true);
		}
	}

	@Override
	public List<Module> getEnableModules() {
		Map<String, Object> whereParams = new HashMap<>();
		whereParams.put("parentModule", HqlNullType.isNotNull);
		whereParams.put("permit", true);
		
		Map<String, SortType> sortParams = new HashMap<>();
		sortParams.put("moduleSort", SortType.asc);
		
		return this.moduleDao.getEntitys(null, whereParams, sortParams);
	}

	@Override
	public Module getById(String id) {
		return this.moduleDao.getEntityById(id);
	}

	@Override
	public void delete(Module mod) {
		this.moduleDao.deleteModule(mod);
	}

}
