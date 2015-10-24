package com.sunjee.btms.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.sunjee.btms.common.Sex;
import com.sunjee.component.bean.BaseBean;

/**
 * 福位使用者（死者或入住者）实体类
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_deader")
public class Deader extends BaseBean {

	private static final long serialVersionUID = 8503753680223147625L;

	private String deadId;
	private String deadName;
	private Sex desSex;
	private String deadNational; // 民族
	private String deadNatPlace; // 籍贯
	private String deadAdress; // 家庭地址
	private AdvocaterCard advCard; // 死者昄依证
	private Date deadBirthday; // 死者生日
	private String identNum; // 身份证号
	private Date deadedDate; // 往生时间（死亡时间）
	private Date inputDate; // 入住时间（安葬到福位时间）
	private String contactName; // 接洽居士姓名（联系人姓名）
	private String contactTell; // 联系人电话
	private String contactAdress; // 联系人地址
	private BlessSeat blessSeat; // 所在入住福位
	private String deadRemark;
	private AdvocaterCard adCard; // 死者对应的昄依证
	private String advCardCode; // 昄依证编号

	public Deader() {
		super();
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getDeadId() {
		return deadId;
	}

	public void setDeadId(String deadId) {
		this.deadId = deadId;
	}

	@Column(length = 50, nullable = false)
	public String getDeadName() {
		return deadName;
	}

	public void setDeadName(String deadName) {
		this.deadName = deadName;
	}

	@Enumerated(EnumType.STRING)
	public Sex getDesSex() {
		return desSex;
	}

	public void setDesSex(Sex desSex) {
		this.desSex = desSex;
	}

	@Column(length = 50)
	public String getDeadNational() {
		return deadNational;
	}

	public void setDeadNational(String deadNational) {
		this.deadNational = deadNational;
	}

	@Column(length = 50)
	public String getDeadNatPlace() {
		return deadNatPlace;
	}

	public void setDeadNatPlace(String deadNatPlace) {
		this.deadNatPlace = deadNatPlace;
	}

	@Column(length = 100)
	public String getDeadAdress() {
		return deadAdress;
	}

	public void setDeadAdress(String deadAdress) {
		this.deadAdress = deadAdress;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "card_id")
	public AdvocaterCard getAdvCard() {
		return advCard;
	}

	public void setAdvCard(AdvocaterCard advCard) {
		this.advCard = advCard;
	}

	@JSON(format = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	public Date getDeadBirthday() {
		return deadBirthday;
	}

	public void setDeadBirthday(Date deadBirthday) {
		this.deadBirthday = deadBirthday;
	}

	@Column(length = 18, nullable = false, unique = true)
	public String getIdentNum() {
		return identNum;
	}

	public void setIdentNum(String identNum) {
		this.identNum = identNum;
	}

	@JSON(format = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	public Date getDeadedDate() {
		return deadedDate;
	}

	public void setDeadedDate(Date deadedDate) {
		this.deadedDate = deadedDate;
	}

	@JSON(format = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	@Column(nullable = false, length = 50)
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(length = 11)
	public String getContactTell() {
		return contactTell;
	}

	public void setContactTell(String contactTell) {
		this.contactTell = contactTell;
	}

	@Column(length = 150)
	public String getContactAdress() {
		return contactAdress;
	}

	public void setContactAdress(String contactAdress) {
		this.contactAdress = contactAdress;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bs_id", nullable = false)
	public BlessSeat getBlessSeat() {
		return blessSeat;
	}

	public void setBlessSeat(BlessSeat blessSeat) {
		this.blessSeat = blessSeat;
	}

	@Column(length = 500)
	public String getDeadRemark() {
		return deadRemark;
	}

	public void setDeadRemark(String deadRemark) {
		this.deadRemark = deadRemark;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "adcard_id")
	public AdvocaterCard getAdCard() {
		return adCard;
	}

	public void setAdCard(AdvocaterCard adCard) {
		this.adCard = adCard;
	}

	@Column(length = 15, name = "adv_card_code")
	public String getAdvCardCode() {
		return advCardCode;
	}

	public void setAdvCardCode(String advCardCode) {
		this.advCardCode = advCardCode;
	}

}
