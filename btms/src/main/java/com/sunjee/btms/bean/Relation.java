package com.sunjee.btms.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.sunjee.btms.common.Sex;
import com.sunjee.component.bean.BaseBean;

/**
 * 社会关系实体类
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_relation")
public class Relation extends BaseBean {

	private static final long serialVersionUID = -4967144493130409288L;

	private String relId;
	private String relName; // 姓名
	private String relation; // 关系
	private Date relBirthday; // 出身日期
	private Sex relSex;
	private String relAddress;
	private String relTell; // 联系号码
	private int relAge; // 年龄
	private Member mem;	//社会关系对应会员
	private String relRemark;

	public Relation() {
		super();
	}

	public Relation(String relId) {
		this.relId = relId;
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length=36)
	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	@Column(length = 50, nullable = false)
	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	@Column(length = 50, nullable = false)
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	@Temporal(TemporalType.DATE)
	public Date getRelBirthday() {
		return relBirthday;
	}

	public void setRelBirthday(Date relBirthday) {
		this.relBirthday = relBirthday;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	public Sex getRelSex() {
		return relSex;
	}

	public void setRelSex(Sex relSex) {
		this.relSex = relSex;
	}

	@Column(length = 100)
	public String getRelAddress() {
		return relAddress;
	}

	public void setRelAddress(String relAddress) {
		this.relAddress = relAddress;
	}

	@Column(length = 11)
	public String getRelTell() {
		return relTell;
	}

	public void setRelTell(String relTell) {
		this.relTell = relTell;
	}

	@SuppressWarnings("deprecation")
	@Transient
	public int getRelAge() {
		this.relAge = 0;
		if (this.relBirthday != null) {
			this.relAge = new Date().getYear() - this.relBirthday.getYear();
		}
		return this.relAge;
	}

	public void setRelAge(int relAge) {
		this.relAge = relAge;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	public Member getMem() {
		return mem;
	}

	public void setMem(Member mem) {
		this.mem = mem;
	}

	@Column(length = 500)
	public String getRelRemark() {
		return relRemark;
	}

	public void setRelRemark(String relRemark) {
		this.relRemark = relRemark;
	}

}
