package com.sunjee.btms.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;

/**
 * 预售统计
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_presell_summary")
public class PreSellSummary extends BaseBean {

	private static final long serialVersionUID = 2249500241232668416L;

	private String sumId;
	private Date createDate;

	private int psCount;
	private float psTotal;

	private int cashCount;
	private float shouldCharge;
	private float psCharge;
	private float realCharge;
	
	private float total;

	public PreSellSummary() {
		super();
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "sum_id", length = 36)
	public String getSumId() {
		return sumId;
	}

	public void setSumId(String sumId) {
		this.sumId = sumId;
	}

	@JSON(format = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "ps_count")
	public int getPsCount() {
		return psCount;
	}

	public void setPsCount(int psCount) {
		this.psCount = psCount;
	}

	@Column(name = "ps_total")
	public float getPsTotal() {
		return psTotal;
	}

	public void setPsTotal(float psTotal) {
		this.psTotal = psTotal;
	}

	@Column(name = "cash_count")
	public int getCashCount() {
		return cashCount;
	}

	public void setCashCount(int cashCount) {
		this.cashCount = cashCount;
	}

	@Column(name = "shoule_charge")
	public float getShouldCharge() {
		return shouldCharge;
	}

	public void setShouldCharge(float shouldCharge) {
		this.shouldCharge = shouldCharge;
	}

	@Column(name = "ps_charge")
	public float getPsCharge() {
		return psCharge;
	}

	public void setPsCharge(float psCharge) {
		this.psCharge = psCharge;
	}

	@Column(name = "real_charge")
	public float getRealCharge() {
		return realCharge;
	}

	public void setRealCharge(float realCharge) {
		this.realCharge = realCharge;
	}

	@Column(name="total")
	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
}
