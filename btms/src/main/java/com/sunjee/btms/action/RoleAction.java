package com.sunjee.btms.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.service.ModuleService;
import com.sunjee.btms.service.RoleService;
import com.sunjee.component.bean.Module;
import com.sunjee.component.bean.Role;

@Controller("roleAction")
@Scope("prototype")
public class RoleAction extends BaseAction<Role> implements ModelDriven<Role> {

	private static final long serialVersionUID = 6041974902185255145L;

	private RoleService roleService;
	private ModuleService moduleService;
	private Role role;
	private List<Module> moduleList;
	private String moduleIds[];
	private String ids;

	public RoleService getRoleService() {
		return roleService;
	}

	@Resource(name = "roleService")
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public ModuleService getModuleService() {
		return moduleService;
	}

	@Resource(name = "moduleService")
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Module> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<Module> moduleList) {
		this.moduleList = moduleList;
	}

	public String[] getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(String[] moduleIds) {
		this.moduleIds = moduleIds;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	@Override
	public String execute() throws Exception {
		setModuleList(this.moduleService.getEnableModules());
		if (this.moduleList != null) {
			System.out.println("权限数量位：" + moduleList.size());
		}
		return success();
	}

	/**
	 * 获取角色Grid
	 * 
	 * @return
	 * @throws Exception
	 */
	public String grid() throws Exception {
		this.setDataGrid(this.roleService.getDataGrid(getPager(),null,null));
		return success();
	}
	
	/**
	 * 添加角色
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		if(moduleIds != null){
			Set<Module> modSet = new HashSet<>();
			for(String moduleId : moduleIds){
				Module module = new Module(moduleId);
				modSet.add(module);
			}
			role.setModSet(modSet);
		}
		this.roleService.add(role);
		return success();
	}
	
	public String edit() throws Exception {
		if(moduleIds != null){
			Set<Module> modSet = new HashSet<>();
			for(String moduleId : moduleIds){
				Module module = new Module(moduleId);
				modSet.add(module);
			}
			role.setModSet(modSet);
		}
		this.roleService.update(role);
		return success();
	}

	public String deleteRoles(){
		if(!StringUtils.isEmpty(ids)){
			this.roleService.deleteRoles(ids.split(","));
		}
		return success();
	}
	
	@Override
	public Role getModel() {
		if (role == null) {
			role = new Role();
		}
		return role;
	}

}
