package com.sunjee.btms.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;

/**
 * 昄依证实体类
 * 
 * @author ShenYunjie
 * 
 */
@Entity
@Table(name = "t_adovcater_card")
public class AdvocaterCard extends BaseBean {

	private static final long serialVersionUID = -1875736384577888665L;

	private String cardId; // 昄依证ID
	private String cardCode; // 昄依证编号
	private String advName; // 办证人（持有人）姓名
	private Date advBirthday; // 办证人出生日期
	private Date createCardDate; // 办证日期
	private Deader deader; // 对应的死者
	private String remark;

	public AdvocaterCard() {
		super();
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(length = 36)
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(length = 12, nullable = false, unique = true)
	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	@Column(length = 25, nullable = false, unique = true)
	public String getAdvName() {
		return advName;
	}

	public void setAdvName(String advName) {
		this.advName = advName;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getAdvBirthday() {
		return advBirthday;
	}

	public void setAdvBirthday(Date advBirthday) {
		this.advBirthday = advBirthday;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", updatable = false, nullable = false)
	public Date getCreateCardDate() {
		return createCardDate;
	}

	public void setCreateCardDate(Date createCardDate) {
		this.createCardDate = createCardDate;
	}

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "adCard")
	public Deader getDeader() {
		return deader;
	}

	public void setDeader(Deader deader) {
		this.deader = deader;
	}

	@Column(length = 500, name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
