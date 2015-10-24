package com.sunjee.btms.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;

/**
 * 其它费用项目对应实体类
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_expens_item")
public class ExpensItem extends BaseBean {

	private static final long serialVersionUID = -6775068979135570858L;

	private String itemId;
	private String itemName; // 费用项名称
	private float itemPrice = 0.0f;
	private boolean editAble; // 收缴此费用时是否可编辑缴费年限,默认为可以编辑（即：缴费年限）
	private boolean permit;
	private int costType; // 费用类型：0:表示其它费用；1:表示会员费；2:表示管理费
	private String itemRemark;

	public ExpensItem() {
		super();
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Column(nullable = false, unique = true, length = 50)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(nullable = false)
	public float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}

	@Column(nullable = false)
	public boolean isEditAble() {
		return editAble;
	}

	public void setEditAble(boolean editAble) {
		this.editAble = editAble;
	}

	@Column(nullable = false, name = "permit")
	public boolean isPermit() {
		return permit;
	}

	public void setPermit(boolean permit) {
		this.permit = permit;
	}

	@Column(name = "cost_type", nullable = false)
	public int getCostType() {
		return costType;
	}

	public void setCostType(int costType) {
		this.costType = costType;
	}

	@Column(length = 500)
	public String getItemRemark() {
		return itemRemark;
	}

	public void setItemRemark(String itemRemark) {
		this.itemRemark = itemRemark;
	}

}
