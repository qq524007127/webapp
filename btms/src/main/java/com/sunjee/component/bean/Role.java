package com.sunjee.component.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_role")
public class Role extends BaseBean {

	private static final long serialVersionUID = -5622782670321260891L;

	private String roleId;
	private String roleName;
	private Set<Module> modSet;
	private String remark;

	public Role() {
		super();
	}

	public Role(String roleId) {
		this.roleId = roleId;
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(length = 50, nullable = false, unique = true)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "role_module", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "module_id") })
	public Set<Module> getModSet() {
		return modSet;
	}

	public void setModSet(Set<Module> modSet) {
		this.modSet = modSet;
	}

	@Column(length = 100, name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
