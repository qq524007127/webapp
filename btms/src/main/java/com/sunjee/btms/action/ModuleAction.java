package com.sunjee.btms.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.common.Message;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.ModuleService;
import com.sunjee.component.bean.Module;

@Controller("moduleAction")
@Scope("prototype")
public class ModuleAction extends BaseAction<Module> implements ModelDriven<Module> {

	private static final long serialVersionUID = 3268235124557471080L;

	private ModuleService moduleService;
	private Module module;
	private List<Module> rootModuleList;
	private String moduleIds;

	public ModuleService getModuleService() {
		return moduleService;
	}

	@Resource(name = "moduleService")
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<Module> getRootModuleList() {
		return rootModuleList;
	}

	public void setRootModuleList(List<Module> rootModuleList) {
		this.rootModuleList = rootModuleList;
	}

	public String getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}

	@Override
	public String execute() throws Exception {
		return success();
	}

	/**
	 * 模块数据表格
	 * 
	 * @return
	 */
	public String grid() {
		Map<String, SortType> sortParams = getSortParams();
		sortParams.put("parentModule", SortType.asc);
		sortParams.put("permit", SortType.desc);
		sortParams.put("moduleSort", SortType.asc);
		this.setDataGrid(moduleService.getDataGrid(getPager(),null,sortParams));
		return SUCCESS;
	}

	/**
	 * 添加模块
	 * 
	 * @return
	 */
	public String add() throws Exception {
		if (StringUtils.isEmpty(module.getParentModule().getModuleId())) {
			module.setParentModule(null);
		}
		moduleService.add(module);
		return success();
	}

	/**
	 * 修改模块
	 * 
	 * @return
	 */
	public String edit() throws Exception {
		if (StringUtils.isEmpty(module.getParentModule().getModuleId())) {
			module.setParentModule(null);
		}
		moduleService.update(module);
		setMessage(new Message());
		return SUCCESS;
	}

	/**
	 * 获取根模块列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String rootModuleList() throws Exception {
		setRootModuleList(this.moduleService.getAllRootModule());
		return SUCCESS;
	}

	/**
	 * 禁用模块
	 * 
	 * @return
	 * @throws Exception
	 */
	public String disable() throws Exception {
		if (moduleIds != null) {
			String ids[] = moduleIds.split(",");
			this.moduleService.updateDisable(ids);
		}
		setMessage(new Message());
		return SUCCESS;
	}

	@Override
	public Module getModel() {
		if (module == null) {
			module = new Module();
		}
		return module;
	}
}
