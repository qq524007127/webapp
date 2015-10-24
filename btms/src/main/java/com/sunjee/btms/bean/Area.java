package com.sunjee.btms.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;

@Entity
@Table(name = "t_area")
public class Area extends BaseBean {

	private static final long serialVersionUID = 4479188539719326734L;

	private String areaId;
	private String areaName;
	private int areaRow;
	private int areaColumn;
	private int shelfRow;
	private int shelfColumn;
	private String coords;
	private String remark;

	public Area() {
	}

	public Area(String areaName) {
		this.areaName = areaName;
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(nullable = false, unique = true, length = 10, name = "name")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(nullable = false, name = "area_row")
	public int getAreaRow() {
		return areaRow;
	}

	public void setAreaRow(int areaRow) {
		this.areaRow = areaRow;
	}

	@Column(nullable = false, name = "area_column")
	public int getAreaColumn() {
		return areaColumn;
	}

	public void setAreaColumn(int areaColumn) {
		this.areaColumn = areaColumn;
	}

	@Column(nullable = false, name = "shelf_row")
	public int getShelfRow() {
		return shelfRow;
	}

	public void setShelfRow(int shelfRow) {
		this.shelfRow = shelfRow;
	}

	@Column(nullable = false, name = "shelf_column")
	public int getShelfColumn() {
		return shelfColumn;
	}

	public void setShelfColumn(int shelfColumn) {
		this.shelfColumn = shelfColumn;
	}
	
	@Column(name="coords",length=100, nullable=false)
	public String getCoords() {
		return coords;
	}

	public void setCoords(String coords) {
		this.coords = coords;
	}

	@Column(length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
