package com.sunjee.btms.bean;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;

/**
 * 牌位实体类
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_tablet")
public class Tablet extends BaseBean {

	private static final long serialVersionUID = -4462364337843805252L;

	private String tabletId;
	private String tabletName;
	private float tabletPrice = 0.0f;
	private Set<TabletRecord> tabletRecSet;
	private Date tabletOverdue; // 到期时间
	private boolean editable; // 捐赠年限是否可编辑
	private boolean permit; // 是否有效
	private String tabletRemark;

	public Tablet() {
		super();
	}

	public Tablet(String tabletId) {
		this.tabletId = tabletId;
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getTabletId() {
		return tabletId;
	}

	public void setTabletId(String tabletId) {
		this.tabletId = tabletId;
	}

	@Column(length = 100, nullable = false, unique = true)
	public String getTabletName() {
		return tabletName;
	}

	public void setTabletName(String tabletName) {
		this.tabletName = tabletName;
	}

	@Column(nullable = false)
	public float getTabletPrice() {
		return tabletPrice;
	}

	public void setTabletPrice(float tabletPrice) {
		this.tabletPrice = tabletPrice;
	}

	@JSON(serialize = false)
	@OneToMany(mappedBy = "tablet")
	public Set<TabletRecord> getTabletRecSet() {
		return tabletRecSet;
	}

	public void setTabletRecSet(Set<TabletRecord> tabletRecSet) {
		this.tabletRecSet = tabletRecSet;
	}

	@JSON(format = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	public void setTabletOverdue(Date tabletOverdue) {
		this.tabletOverdue = tabletOverdue;
	}

	public void setTabletRemark(String tabletRemark) {
		this.tabletRemark = tabletRemark;
	}

	@Column(nullable = false, name = "editable")
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Column(nullable = false, name = "permit")
	public boolean isPermit() {
		return permit;
	}

	public void setPermit(boolean permit) {
		this.permit = permit;
	}

	@Column(length = 500)
	public String getTabletRemark() {
		return tabletRemark;
	}

	public Date getTabletOverdue() {
		return tabletOverdue;
	}
}
