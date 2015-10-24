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
 * 缴费明细实体类
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_pay_detail")
public class PayDetail extends BaseBean {

	private static final long serialVersionUID = 3599882529798523778L;

	private String detailId;
	private String detailItemName;
	private float itemPrice; // 明细单价
	private int detailLength = 1; // 缴费年限
	private float detTotalPrice; // 缴费总价
	private PayRecord payRecord; // 对应缴费记录
	private int costType; // 费用类型：0:表示其它费用；1:表示会员费；2:表示管理费；
	private Date dueToDate; // 到期时间
	private String foreignId; // 当收费类型为‘1’会员费时保存会员ID，为‘2’管理费时用于保存福位ID，为‘0’时为空

	public PayDetail() {
		super();
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	@Column(nullable = false, length = 200, name = "name")
	public String getDetailItemName() {
		return detailItemName;
	}

	public void setDetailItemName(String detailItemName) {
		this.detailItemName = detailItemName;
	}

	@Column(nullable = false, name = "price")
	public float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}

	@Column(name = "time_length")
	public int getDetailLength() {
		return detailLength;
	}

	public void setDetailLength(int detailLength) {
		this.detailLength = detailLength;
	}

	@Column(nullable = false, name = "total_price")
	public float getDetTotalPrice() {
		return detTotalPrice;
	}

	public void setDetTotalPrice(float detTotalPrice) {
		this.detTotalPrice = detTotalPrice;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pay_id", nullable = false)
	public PayRecord getPayRecord() {
		return payRecord;
	}

	public void setPayRecord(PayRecord payRecord) {
		this.payRecord = payRecord;
	}

	@Column(nullable = false, name = "cost_type")
	public int getCostType() {
		return costType;
	}

	public void setCostType(int costType) {
		this.costType = costType;
	}

	@JSON(format = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "dueto_date")
	public Date getDueToDate() {
		return dueToDate;
	}

	public void setDueToDate(Date dueToDate) {
		this.dueToDate = dueToDate;
	}

	@Column(length = 50, name = "fg_id")
	public String getForeignId() {
		return foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

}
