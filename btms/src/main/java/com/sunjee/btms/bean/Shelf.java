package com.sunjee.btms.bean;

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

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;

@Entity
@Table(name = "t_shelf")
public class Shelf extends BaseBean {

	private static final long serialVersionUID = 3181523751841793309L;

	private String shelfId;
	private String shelfCode; // 福位架编号(E0203)
	private Area shelfArea; // 所在区域
	private int shelfRow; // 福位架总行数
	private int shelfColumn; // 福位架总列数
	private int postionRow; // 福位架所在区域行
	private int postionColumn; // 福位架所在区域列
	private boolean permit = true; // 是否有效,默认为有效
	private Set<BlessSeat> bsSet; // 福位架所拥有的福位
	private String remark;

	public Shelf() {
		super();
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getShelfId() {
		return shelfId;
	}

	public void setShelfId(String shelfId) {
		this.shelfId = shelfId;
	}

	@Column(length = 10, nullable = false, unique = true)
	public String getShelfCode() {
		return shelfCode;
	}

	public void setShelfCode(String shelfCode) {
		this.shelfCode = shelfCode;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "area_id")
	public Area getShelfArea() {
		return shelfArea;
	}

	public void setShelfArea(Area shelfArea) {
		this.shelfArea = shelfArea;
	}

	@Column(nullable = false)
	public int getShelfRow() {
		return shelfRow;
	}

	public void setShelfRow(int shelfRow) {
		this.shelfRow = shelfRow;
	}

	@Column(nullable = false)
	public int getShelfColumn() {
		return shelfColumn;
	}

	public void setShelfColumn(int shelfColumn) {
		this.shelfColumn = shelfColumn;
	}

	@Column(nullable = false)
	public int getPostionRow() {
		return postionRow;
	}

	public void setPostionRow(int postionRow) {
		this.postionRow = postionRow;
	}

	@Column(nullable = false)
	public int getPostionColumn() {
		return postionColumn;
	}

	public void setPostionColumn(int postionColumn) {
		this.postionColumn = postionColumn;
	}

	@Column(nullable = false)
	public boolean isPermit() {
		return permit;
	}

	public void setPermit(boolean permit) {
		this.permit = permit;
	}

	@JSON(serialize = false)
	@OneToMany(mappedBy = "shelf",cascade={CascadeType.PERSIST,CascadeType.MERGE})
	public Set<BlessSeat> getBsSet() {
		return bsSet;
	}

	public void setBsSet(Set<BlessSeat> bsSet) {
		this.bsSet = bsSet;
	}

	@Column(length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void createShelfCode() {
		this.shelfCode = this.shelfArea.getAreaName();
		String tmp = String.valueOf(this.postionRow);
		while (tmp.length() < 3) {
			tmp = "0" + tmp;
		}
		this.shelfCode += tmp;
		tmp = String.valueOf(this.postionColumn);
		while (tmp.length() < 3) {
			tmp = "0" + tmp;
		}
		this.shelfCode += tmp;
	}
	
	public static String getShelfCodeByArea(Area area,int areaRow,int areaColumn){
		Shelf shelf = new Shelf();
		shelf.setShelfArea(area);
		shelf.setPostionRow(areaRow);
		shelf.setPostionColumn(areaColumn);
		shelf.createShelfCode();
		return shelf.getShelfCode();
	}
}
