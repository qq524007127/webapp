package com.sunjee.btms.bean;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.sunjee.btms.common.Sex;
import com.sunjee.component.bean.BaseBean;

/**
 * 会员对应实体类
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_member")
public class Member extends BaseBean {

	private static final long serialVersionUID = -2500659625806259580L;

	private String memberId;
	private String memberName;
	private Sex memberSex;
	private String memberNational; // 民族
	private String memberNatPlace; // 籍贯
	private String memberAddress;
	private Date memberBirthday;
	private String memberIdentNum; // 身份证号
	private String memberUnit; // 工作单位
	private String memberTell;
	private String spareName; // 备用联系人姓名
	private String spareTell; // 备用联系人电话
	private Set<Relation> relation; // 社会关系
	private Set<BSRecord> bsRecordSet; // 捐赠福位记录
	private Set<TabletRecord> tlRecSet; // 捐赠牌位记录
	private MemberCard memberCard; // 会员证
	private String memberPassword; // 会员登陆密码，用于后期会员登陆
	private boolean memberPermit; // 会员是否有效
	private String memberRemark;
	private Saler saler;	//对应销售人员

	public Member() {
		super();
	}

	public Member(String memberId) {
		this.memberId = memberId;
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(nullable = false, length = 50)
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Enumerated(EnumType.STRING)
	public Sex getMemberSex() {
		return memberSex;
	}

	public void setMemberSex(Sex memberSex) {
		this.memberSex = memberSex;
	}

	@Column(length = 50)
	public String getMemberNational() {
		return memberNational;
	}

	public void setMemberNational(String memberNational) {
		this.memberNational = memberNational;
	}

	@Column(length = 100)
	public String getMemberNatPlace() {
		return memberNatPlace;
	}

	public void setMemberNatPlace(String memberNatPlace) {
		this.memberNatPlace = memberNatPlace;
	}

	@Column(length = 100)
	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	@JSON(format = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	public Date getMemberBirthday() {
		return memberBirthday;
	}

	public void setMemberBirthday(Date memberBirthday) {
		this.memberBirthday = memberBirthday;
	}

	@Column(length = 18)
	public String getMemberIdentNum() {
		return memberIdentNum;
	}

	public void setMemberIdentNum(String memberIdentNum) {
		this.memberIdentNum = memberIdentNum;
	}

	@Column(length = 100)
	public String getMemberUnit() {
		return memberUnit;
	}

	public void setMemberUnit(String memberUnit) {
		this.memberUnit = memberUnit;
	}

	@Column(length = 11)
	public String getMemberTell() {
		return memberTell;
	}

	public void setMemberTell(String memberTell) {
		this.memberTell = memberTell;
	}

	@Column(length = 50)
	public String getSpareName() {
		return spareName;
	}

	public void setSpareName(String spareName) {
		this.spareName = spareName;
	}

	@Column(length = 11)
	public String getSpareTell() {
		return spareTell;
	}

	public void setSpareTell(String spareTell) {
		this.spareTell = spareTell;
	}

	@JSON(serialize = false)
	@OneToMany(mappedBy = "mem")
	public Set<Relation> getRelation() {
		return relation;
	}

	public void setRelation(Set<Relation> relation) {
		this.relation = relation;
	}

	@JSON(serialize = false)
	@OneToMany(mappedBy = "mem")
	public Set<BSRecord> getBsRecordSet() {
		return bsRecordSet;
	}

	public void setBsRecordSet(Set<BSRecord> bsRecordSet) {
		this.bsRecordSet = bsRecordSet;
	}

	@JSON(serialize = false)
	@OneToMany(mappedBy = "mem")
	public Set<TabletRecord> getTlRecSet() {
		return tlRecSet;
	}

	public void setTlRecSet(Set<TabletRecord> tlRecSet) {
		this.tlRecSet = tlRecSet;
	}

	@OneToOne(mappedBy = "mem", fetch = FetchType.EAGER)
	public MemberCard getMemberCard() {
		return memberCard;
	}

	public void setMemberCard(MemberCard memberCard) {
		this.memberCard = memberCard;
	}

	@Column(length = 32, nullable = false, name = "password")
	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	@Column(nullable = false, name = "permit")
	public boolean isMemberPermit() {
		return memberPermit;
	}

	public void setMemberPermit(boolean memberPermit) {
		this.memberPermit = memberPermit;
	}

	@Column(length = 500, name = "remark")
	public String getMemberRemark() {
		return memberRemark;
	}

	public void setMemberRemark(String memberRemark) {
		this.memberRemark = memberRemark;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="saler_id")
	public Saler getSaler() {
		return saler;
	}

	public void setSaler(Saler saler) {
		this.saler = saler;
	}
	
}
