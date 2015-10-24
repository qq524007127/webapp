package com.sunjee.btms.bean;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;

/**
 * 福位实体类
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_bless_seat")
public class BlessSeat extends BaseBean {

	private static final long serialVersionUID = -3536124556799345305L;

	private String bsId;
	private String bsCode; // 福位编号(E01030203)
	private Shelf shelf; // 所在福位架
	private int shelfRow; // 在福位架的所属行
	private int shelfColumn; // 在福位架的所属列
	private Set<BSRecord> bsRecordSet; // 福位对应的福位捐赠记录
	private Level lev; // 福位级别（即：福位对应的价格）
	private Deader deader; // 福位使用者，即：死者
	private float managExpense; // 管理费
	private boolean permit = true; // 是否有效
	private String remark;
	private BSRecord curBr; // 面前对应的有效捐赠记录

	public BlessSeat() {
		super();
	}

	public BlessSeat(String bsId) {
		this.bsId = bsId;
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getBsId() {
		return bsId;
	}

	public void setBsId(String bsId) {
		this.bsId = bsId;
	}

	@Column(length = 15, nullable = false, unique = true)
	public String getBsCode() {
		return bsCode;
	}

	public void setBsCode(String bsCode) {
		this.bsCode = bsCode;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shelf_id", nullable = false)
	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
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

	@JSON(serialize = false)
	@OneToMany(mappedBy = "blessSeat")
	public Set<BSRecord> getBsRecordSet() {
		return bsRecordSet;
	}

	public void setBsRecordSet(Set<BSRecord> bsRecordSet) {
		this.bsRecordSet = bsRecordSet;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "level_id")
	public Level getLev() {
		return lev;
	}

	public void setLev(Level lev) {
		this.lev = lev;
	}

	@OneToOne(mappedBy = "blessSeat", fetch = FetchType.EAGER)
	public Deader getDeader() {
		return deader;
	}

	public void setDeader(Deader deader) {
		this.deader = deader;
	}

	@Column(nullable = false)
	public float getManagExpense() {
		return managExpense;
	}

	public void setManagExpense(float managExpense) {
		this.managExpense = managExpense;
	}

	@Column(nullable = false)
	public boolean isPermit() {
		return permit;
	}

	public void setPermit(boolean permit) {
		this.permit = permit;
	}

	@Column(length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public BSRecord getCurBr() {
		return curBr;
	}

	public void setCurBr(BSRecord curBr) {
		this.curBr = curBr;
	}

	public void createBsCode() {
		this.bsCode = this.shelf.getShelfCode();
		String tmp = String.valueOf(this.shelfRow);
		while (tmp.length() < 2) {
			tmp = "0" + tmp;
		}
		this.bsCode += tmp;
		tmp = String.valueOf(this.shelfColumn);
		while (tmp.length() < 2) {
			tmp = "0" + tmp;
		}
		this.bsCode += tmp;
	}
	
	public static String getBsCodeByShelf(Shelf shelf, int row, int column){
		BlessSeat bs = new BlessSeat();
		bs.setShelf(shelf);
		bs.setShelfRow(row);
		bs.setShelfColumn(column);
		bs.createBsCode();
		return bs.getBsCode();
	}
}
