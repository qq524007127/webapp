package com.sunjee.btms.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;

/**
 * 福位级别（即：不同级别对应不同的价格，不同福位对应不同的级别就对应不同的价格）
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_level")
public class Level extends BaseBean {

	private static final long serialVersionUID = 5760024769252181081L;

	private String levId;
	private String levName;
	private float levPrice;
	private float mngPrice;
	private String remark;

	public Level() {
		super();
	}

	public Level(String levId) {
		this.levId = levId;
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getLevId() {
		return levId;
	}

	public void setLevId(String levId) {
		this.levId = levId;
	}

	@Column(length = 100, nullable = false, unique = true)
	public String getLevName() {
		return levName;
	}

	public void setLevName(String levName) {
		this.levName = levName;
	}

	@Column(nullable = false)
	public float getLevPrice() {
		return levPrice;
	}

	public void setLevPrice(float levPrice) {
		this.levPrice = levPrice;
	}

	@Column(name = "mng_price")
	public float getMngPrice() {
		return mngPrice;
	}

	public void setMngPrice(float mngPrice) {
		this.mngPrice = mngPrice;
	}

	@Column(length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
