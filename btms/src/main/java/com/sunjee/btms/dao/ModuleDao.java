package com.sunjee.btms.dao;

import java.util.List;
import java.util.Map;

import com.sunjee.btms.common.SortType;
import com.sunjee.component.bean.Module;

public interface ModuleDao extends SupportDao<Module> {
	Module addModule(Module mod);

	void updateModule(Module mod);

	void deleteModule(Module mod);

	List<Module> getAllRootModule(Map<String, SortType> sortParams);

	void updatePermitState(String moduleId, boolean permit);
}
