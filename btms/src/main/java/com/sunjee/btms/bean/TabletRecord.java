package com.sunjee.btms.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;
import com.sunjee.component.bean.User;

/**
 * 牌位捐赠记录表
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_tablet_record")
public class TabletRecord extends BaseBean {

	private static final long serialVersionUID = 3814005178353532669L;

	private String tlRecId;
	private Date tlRecCreateDate;
	private Member mem;
	private Enterprise enterprise;
	private Tablet tablet; // 对应的牌位
	private int tlRecLength;
	private Date tlRecOverdue;
	private float tlTotalPrice;
	private User tlRecUser; // 销售员
	private PayRecord payRecord; // 对应缴费记录
	private Map<String, Object> state;

	public TabletRecord() {
		super();
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getTlRecId() {
		return tlRecId;
	}

	public void setTlRecId(String tlRecId) {
		this.tlRecId = tlRecId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false, name = "create_date")
	public Date getTlRecCreateDate() {
		return tlRecCreateDate;
	}

	public void setTlRecCreateDate(Date tlRecCreateDate) {
		this.tlRecCreateDate = tlRecCreateDate;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	public Member getMem() {
		return mem;
	}

	public void setMem(Member mem) {
		this.mem = mem;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "enter_id")
	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tablet_id", nullable = false)
	public Tablet getTablet() {
		return tablet;
	}

	public void setTablet(Tablet tablet) {
		this.tablet = tablet;
	}

	@Column(nullable = false, name = "length")
	public int getTlRecLength() {
		return tlRecLength;
	}

	public void setTlRecLength(int tlRecLength) {
		this.tlRecLength = tlRecLength;
	}

	@Temporal(TemporalType.DATE)
	@JSON(format = "yyyy-MM-dd")
	@Column(nullable = false, name = "overdue")
	public Date getTlRecOverdue() {
		return tlRecOverdue;
	}

	public void setTlRecOverdue(Date tlRecOverdue) {
		this.tlRecOverdue = tlRecOverdue;
	}

	@Column(nullable = false, name = "total_price")
	public float getTlTotalPrice() {
		return tlTotalPrice;
	}

	public void setTlTotalPrice(float tlTotalPrice) {
		this.tlTotalPrice = tlTotalPrice;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public User getTlRecUser() {
		return tlRecUser;
	}

	public void setTlRecUser(User tlRecUser) {
		this.tlRecUser = tlRecUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pay_id", nullable = false)
	public PayRecord getPayRecord() {
		return payRecord;
	}

	public void setPayRecord(PayRecord payRecord) {
		this.payRecord = payRecord;
	}

	@Transient
	public Map<String, Object> getState() {
		if(tlRecOverdue == null){
			return null;
		}
		state = new HashMap<String, Object>();
		Date now = new Date();
		String text = tlRecOverdue.after(now) ? "捐赠中":"捐赠已到期";
		state.put("flag", tlRecOverdue.after(now));
		state.put("text", text);
		return state;
	}

	public void setState(Map<String, Object> state) {
		this.state = state;
	}

}
