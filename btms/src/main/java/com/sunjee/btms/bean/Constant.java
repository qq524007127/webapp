package com.sunjee.btms.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;

/**
 * 系统参数对应的实体类
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_constant")
public class Constant extends BaseBean {

	private static final long serialVersionUID = 7130000762886620376L;

	private String cId;
	private int key; // 参数key
	private String value; // 参数值
	private int group; // 参数对应的组
	private String groupName; // 组名称
	private String remark;

	public Constant() {
		super();
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36, name = "c_id")
	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	@Column(nullable = false, name = "constant_key")
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	@Column(nullable = false, length = 150)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(nullable = false, name = "constant_group")
	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	@Column(length = 150, name = "group_name")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
