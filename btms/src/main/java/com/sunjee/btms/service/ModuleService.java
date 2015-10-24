package com.sunjee.btms.service;

import java.util.List;
import com.sunjee.component.bean.Module;

public interface ModuleService extends SupportService<Module> {

	List<Module> getAllRootModule();

	List<Module> getEnableModules();

	void updateDisable(String... moduleIds);

	void updateEnable(String... moduleIds);
}
