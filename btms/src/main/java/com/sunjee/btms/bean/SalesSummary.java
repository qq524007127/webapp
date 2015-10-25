package com.sunjee.btms.bean;

import java.util.Date;

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

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.sunjee.component.bean.BaseBean;

/**
 * 按销售人员统计
 *
 * @author ShenYunjie
 */
@Entity
@Table(name = "t_sales_summary")
public class SalesSummary extends BaseBean {

    private static final long serialVersionUID = 5078549385278455405L;

    private String dataId;
    private Date createDate; // 统计时间
    private Saler saler;    //对应销售员

    /**
     * 福位相关
     */
    private int bsLeaseCount; // 福位租赁数量
    private float bsLeaseTotalPrice; // 福位租赁金额
    private int bsBuyCount; // 福位捐赠数量
    private float bsBuyTotalPrice; // 福位捐赠金额

    /**
     * 牌位相关
     */
    private int tblBuyCount; // 牌位捐赠数量
    private float tblTotalPrice; // 牌位捐赠金额

    /**
     * 会员费相关
     */
    private int memberCount;
    private float memberTotalPrice;

    /**
     * 管理费相关
     */
    private int mngRecCount; // 记录数
    private float mngTotalPrice; // 总金额

    /**
     * 其它费用统计相关
     */
    private int itemCount; // 数量
    private float itemTotalPrice; // 金额

    private float total; // 小计


    public SalesSummary() {
        super();
    }

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(length = 36, nullable = false, unique = true, name = "data_id")
    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @JSON(format = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "create_date", updatable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(nullable = false, name = "bs_lease_count")
    public int getBsLeaseCount() {
        return bsLeaseCount;
    }

    public void setBsLeaseCount(int bsLeaseCount) {
        this.bsLeaseCount = bsLeaseCount;
    }

    @Column(nullable = false, name = "bs_lease_totalprice")
    public float getBsLeaseTotalPrice() {
        return bsLeaseTotalPrice;
    }

    public void setBsLeaseTotalPrice(float bsLeaseTotalPrice) {
        this.bsLeaseTotalPrice = bsLeaseTotalPrice;
    }

    @Column(nullable = false, name = "bs_buy_count")
    public int getBsBuyCount() {
        return bsBuyCount;
    }

    public void setBsBuyCount(int bsBuyCount) {
        this.bsBuyCount = bsBuyCount;
    }

    @Column(nullable = false, name = "bs_buy_totalprice")
    public float getBsBuyTotalPrice() {
        return bsBuyTotalPrice;
    }

    public void setBsBuyTotalPrice(float bsBuyTotalPrice) {
        this.bsBuyTotalPrice = bsBuyTotalPrice;
    }


    @Column(nullable = false, name = "blt_buy_count")
    public int getTblBuyCount() {
        return tblBuyCount;
    }

    public void setTblBuyCount(int tblBuyCount) {
        this.tblBuyCount = tblBuyCount;
    }

    @Column(nullable = false, name = "blt_totalpirce")
    public float getTblTotalPrice() {
        return tblTotalPrice;
    }

    public void setTblTotalPrice(float tblTotalPrice) {
        this.tblTotalPrice = tblTotalPrice;
    }


    @Column(nullable = false, name = "member_count")
    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    @Column(nullable = false, name = "mem_totalprice")
    public float getMemberTotalPrice() {
        return memberTotalPrice;
    }

    public void setMemberTotalPrice(float memberTotalPrice) {
        this.memberTotalPrice = memberTotalPrice;
    }

    @Column(nullable = false, name = "mng_rec_count")
    public int getMngRecCount() {
        return mngRecCount;
    }

    public void setMngRecCount(int mngRecCount) {
        this.mngRecCount = mngRecCount;
    }

    @Column(nullable = false, name = "mng_totalprice")
    public float getMngTotalPrice() {
        return mngTotalPrice;
    }

    public void setMngTotalPrice(float mngTotalPrice) {
        this.mngTotalPrice = mngTotalPrice;
    }

    @Column(nullable = false, name = "item_count")
    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    @Column(nullable = false, name = "item_totalprice")
    public float getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(float itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    @Column(nullable = false, name = "total")
    public float getTotal() {
        this.total = this.bsBuyTotalPrice + this.bsLeaseTotalPrice
                + this.tblTotalPrice + this.itemTotalPrice
                + this.memberTotalPrice + this.mngTotalPrice;
        return this.total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "saler_id", nullable = true)
    public Saler getSaler() {
        return saler;
    }

    public void setSaler(Saler saler) {
        this.saler = saler;
    }

}
