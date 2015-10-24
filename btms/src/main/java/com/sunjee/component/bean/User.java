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
@Table(name = "t_user")
public class User extends BaseBean {

	private static final long serialVersionUID = -1576404671908915966L;

	private String userId;
	private String userCode;
	private String userName;
	private String password;
	private String mobile; // 手机号码
	private String email; // 邮箱
	private Set<Role> roleSet;
	private boolean permit;
	private String remark;

	public User() {
		super();
	}

	public User(String userCode, String userName) {
		super();
		this.userCode = userCode;
		this.userName = userName;
	}

	public User(String userId, String userCode, String userName,
			String password, boolean permit) {
		super();
		this.userId = userId;
		this.userCode = userCode;
		this.userName = userName;
		this.password = password;
		this.permit = permit;
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(unique=true, length = 16, nullable = false)
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(length = 10, nullable = false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length = 32, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 20, name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(length = 50, name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	public boolean isPermit() {
		return permit;
	}

	public void setPermit(boolean permit) {
		this.permit = permit;
	}

	@Column(name = "remark", length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
