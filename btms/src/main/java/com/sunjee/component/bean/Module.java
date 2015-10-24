package com.sunjee.component.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_module")
public class Module extends BaseBean {

	private static final long serialVersionUID = 8203722193282827649L;

	private String moduleId;
	private String moduleName;
	private String pageUrl; // 对应的页面
	private boolean permit = true;
	private Module parentModule;
	private Set<Module> childSet;
	private String remark; // 描述
	private boolean rootModule; // 是否是根菜单
	private int moduleSort;
	private List<Module> childList;

	public Module() {
		super();
	}

	public Module(String moduleId) {
		super();
		this.moduleId = moduleId;
	}

	public Module(String moduleId, String moduleName, int moduleSort) {
		super();
		this.moduleId = moduleId;
		this.moduleName = moduleName;
		this.moduleSort = moduleSort;
	}

	public Module(String moduleName, String pageUrl) {
		super();
		this.moduleName = moduleName;
		this.pageUrl = pageUrl;
	}

	@Id
	@Column(name = "module_id", length = 36)
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Column(length = 50, nullable = false, unique = true)
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(length = 100)
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	@Column(nullable = false)
	public boolean isPermit() {
		return permit;
	}

	public void setPermit(boolean permit) {
		this.permit = permit;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parsent")
	public Module getParentModule() {
		return parentModule;
	}

	public void setParentModule(Module parentModule) {
		this.parentModule = parentModule;
	}

	@JSON(serialize = false)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentModule")
	public Set<Module> getChildSet() {
		return childSet;
	}

	public void setChildSet(Set<Module> childSet) {
		this.childSet = childSet;
	}

	@Column(length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public boolean isRootModule() {
		this.rootModule = this.parentModule == null ? true : false;
		return this.rootModule;
	}

	public void setRootModule(boolean rootModule) {
		this.rootModule = rootModule;
	}

	@Column(name = "mod_sort")
	public int getModuleSort() {
		return moduleSort;
	}

	public void setModuleSort(int moduleSort) {
		this.moduleSort = moduleSort;
	}

	@Transient
	public List<Module> getChildList() {
		return childList;
	}

	public void setChildList(List<Module> childList) {
		this.childList = childList;
	}
	
}
