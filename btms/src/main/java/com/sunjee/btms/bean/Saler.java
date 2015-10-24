package com.sunjee.btms.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;

/**
 * 销售人员
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_saler")
public class Saler extends BaseBean {

	private static final long serialVersionUID = 855130982752550960L;

	private String salerId;
	private String salerName;
	private String salerPhone;
	private boolean permit = false;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name="saler_id",length=36)
	public String getSalerId() {
		return salerId;
	}

	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}

	@Column(name="saler_name",length=20,nullable=false)
	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	@Column(name="saler_phone",length=20)
	public String getSalerPhone() {
		return salerPhone;
	}

	public void setSalerPhone(String salerPhone) {
		this.salerPhone = salerPhone;
	}

	@Column(name="permit",nullable=false)
	public boolean isPermit() {
		return permit;
	}

	public void setPermit(boolean permit) {
		this.permit = permit;
	}
}
