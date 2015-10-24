package com.sunjee.btms.bean;

import java.util.Date;

import com.sunjee.component.bean.BaseBean;

/**
 * 会员费实体类
 * 
 * @author ShenYunjie
 * 
 */
public class MemberCost extends BaseBean {

	private static final long serialVersionUID = 4946003507700045479L;

	private String costId;
	private BSRecord bsr;
	private Date payCostDate; // 收费时间
	private int longTime; // 缴费那你先
	private float price; // 单价
	private float totalPrice; // 总价
	private Date dueToDate; // 到期时间

	public MemberCost() {
		super();
	}

	public String getCostId() {
		return costId;
	}

	public void setCostId(String costId) {
		this.costId = costId;
	}

	public BSRecord getBsr() {
		return bsr;
	}

	public void setBsr(BSRecord bsr) {
		this.bsr = bsr;
	}

	public Date getPayCostDate() {
		return payCostDate;
	}

	public void setPayCostDate(Date payCostDate) {
		this.payCostDate = payCostDate;
	}

	public int getLongTime() {
		return longTime;
	}

	public void setLongTime(int longTime) {
		this.longTime = longTime;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getDueToDate() {
		return dueToDate;
	}

	public void setDueToDate(Date dueToDate) {
		this.dueToDate = dueToDate;
	}
}
