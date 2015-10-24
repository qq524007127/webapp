package com.sunjee.btms.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;

/**
 * 福位预售
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_presell")
public class PreSell extends BaseBean {

	private static final long serialVersionUID = -4047906956293077685L;

	private String psId;
	private float psPrice;
	private int psCount;
	private float totalPrice;
	private PayRecord pRecord;
	private String orderCode; // 订单号
	private Date createDate; // 预定时间
	private Date cashDate; // 补单时间
	private boolean cash; // 是否已兑现
	private float shouldCharge = 0f; // 用于补单时应收金额
	private float realCharge = 0f; // 差价（兑现时定金与真正选定的福位价格的差价）
	private boolean permit = true;
	private String remark;

	public PreSell() {
		super();
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getPsId() {
		return psId;
	}

	public void setPsId(String psId) {
		this.psId = psId;
	}

	@Column(name = "price")
	public float getPsPrice() {
		return psPrice;
	}

	public void setPsPrice(float psPrice) {
		this.psPrice = psPrice;
	}

	@Column(name = "count")
	public int getPsCount() {
		return psCount;
	}

	public void setPsCount(int psCount) {
		this.psCount = psCount;
	}

	@Column(name = "total_price")
	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "record_id", nullable = false)
	public PayRecord getpRecord() {
		return pRecord;
	}

	public void setpRecord(PayRecord pRecord) {
		this.pRecord = pRecord;
	}

	@Column(name = "order_code", length = 50, unique = true, nullable = false, updatable = false)
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, updatable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cash_date")
	public Date getCashDate() {
		return cashDate;
	}

	public void setCashDate(Date cashDate) {
		this.cashDate = cashDate;
	}

	@Column(name = "iscash")
	public boolean isCash() {
		return cash;
	}

	public void setCash(boolean cash) {
		this.cash = cash;
	}

	@Column(name = "should_charge", nullable = false)
	public float getShouldCharge() {
		return shouldCharge;
	}

	public void setShouldCharge(float shouldCharge) {
		this.shouldCharge = shouldCharge;
	}

	@Column(name = "real_charge", nullable = false)
	public float getRealCharge() {
		return realCharge;
	}

	public void setRealCharge(float realCharge) {
		this.realCharge = realCharge;
	}

	@Column(name = "permit")
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
