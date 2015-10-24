package com.sunjee.btms.bean;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;
import com.sunjee.component.bean.User;

/**
 * 缴费记录
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_pay_record")
public class PayRecord extends BaseBean {

	private static final long serialVersionUID = -5101428351637694142L;

	public final static int COMMON_TYPE = 0; //普通捐赠
	public final static int PRESELL_TYPE = 1;	//福位预售

	private String payRecId;
	private Date payDate;
	private Member mem;
	private Enterprise enterprise;
	private User payUser;
	private String orderCode; // 流水号
	private Set<PayDetail> payDatailSet; // 支付明细
	private Set<BSRecord> bsRecordSet; // 福位捐赠（租赁）记录
	private Set<TabletRecord> tlRecordSet; // 牌位捐赠记录
	private Set<PreSell> psSet; // 福位预售
	private float totalPrice; // 此次收费收费总和
	private int type = COMMON_TYPE;

	public PayRecord() {
		super();
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getPayRecId() {
		return payRecId;
	}

	public void setPayRecId(String payRecId) {
		this.payRecId = payRecId;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	public Member getMem() {
		return mem;
	}

	public void setMem(Member mem) {
		this.mem = mem;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "enter_id")
	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public User getPayUser() {
		return payUser;
	}

	public void setPayUser(User payUser) {
		this.payUser = payUser;
	}

	@Column(length = 50, name = "order_code", nullable = false, unique = true, updatable = false)
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@JSON(serialize = false)
	@OneToMany(mappedBy = "payRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<PayDetail> getPayDatailSet() {
		return payDatailSet;
	}

	public void setPayDatailSet(Set<PayDetail> payDatailSet) {
		this.payDatailSet = payDatailSet;
	}

	@JSON(serialize = false)
	@OneToMany(mappedBy = "payRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<BSRecord> getBsRecordSet() {
		return bsRecordSet;
	}

	public void setBsRecordSet(Set<BSRecord> bsRecordSet) {
		this.bsRecordSet = bsRecordSet;
	}

	@JSON(serialize = false)
	@OneToMany(mappedBy = "payRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<TabletRecord> getTlRecordSet() {
		return tlRecordSet;
	}

	public void setTlRecordSet(Set<TabletRecord> tlRecordSet) {
		this.tlRecordSet = tlRecordSet;
	}

	@JSON(serialize = false)
	@OneToMany(mappedBy = "pRecord", fetch = FetchType.LAZY)
	public Set<PreSell> getPsSet() {
		return psSet;
	}

	public void setPsSet(Set<PreSell> psSet) {
		this.psSet = psSet;
	}

	@Column(nullable = false, name = "total_price")
	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "type", nullable = false)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
