package com.sunjee.btms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.BSRecord;
import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.bean.Enterprise;
import com.sunjee.btms.bean.Member;
import com.sunjee.btms.bean.PayDetail;
import com.sunjee.btms.bean.PayRecord;
import com.sunjee.btms.bean.Saler;
import com.sunjee.btms.bean.TabletRecord;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.DonationType;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.PayRecordDao;
import com.sunjee.btms.exception.AppRuntimeException;
import com.sunjee.btms.service.BSRecordService;
import com.sunjee.btms.service.BlessSeatService;
import com.sunjee.btms.service.MemberService;
import com.sunjee.btms.service.PayDetailService;
import com.sunjee.btms.service.PayRecordService;
import com.sunjee.btms.service.TabletRecordService;
import com.sunjee.component.bean.User;
import com.sunjee.util.CommonUtil;
import com.sunjee.util.DateUtil;

@Service("payRecordService")
public class PayRecordServiceImpl implements PayRecordService {

	private PayRecordDao payRecordDao;
	private MemberService memberService;
	private BSRecordService bsRecordService;
	private TabletRecordService tabletRecordService;
	private PayDetailService payDetailService;
	private BlessSeatService blessSeatService;

	public PayRecordDao getPayRecordDao() {
		return payRecordDao;
	}

	@Resource(name = "payRecordDao")
	public void setPayRecordDao(PayRecordDao payRecordDao) {
		this.payRecordDao = payRecordDao;
	}
	
	public MemberService getMemberService() {
		return memberService;
	}
	
	@Resource(name="memberService")
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public BSRecordService getBsRecordService() {
		return bsRecordService;
	}

	@Resource(name="bsRecordService")
	public void setBsRecordService(BSRecordService bsRecordService) {
		this.bsRecordService = bsRecordService;
	}

	public TabletRecordService getTabletRecordService() {
		return tabletRecordService;
	}
	
	@Resource(name="tabletRecordService")
	public void setTabletRecordService(TabletRecordService tabletRecordService) {
		this.tabletRecordService = tabletRecordService;
	}

	public PayDetailService getPayDetailService() {
		return payDetailService;
	}
	
	@Resource(name="payDetailService")
	public void setPayDetailService(PayDetailService payDetailService) {
		this.payDetailService = payDetailService;
	}

	public BlessSeatService getBlessSeatService() {
		return blessSeatService;
	}
	
	@Resource(name="blessSeatService")
	public void setBlessSeatService(BlessSeatService blessSeatService) {
		this.blessSeatService = blessSeatService;
	}

	@Override
	public DataGrid<PayRecord> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.payRecordDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public PayRecord add(PayRecord t) {
		return this.payRecordDao.saveEntity(t);
	}

	@Override
	public void update(PayRecord t) {
		this.payRecordDao.updateEntity(t);
	}

	@Override
	public List<PayRecord> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.payRecordDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public PayRecord getById(String id) {
		return this.payRecordDao.getEntityById(id);
	}

	@Override
	public void delete(PayRecord t) {
		this.payRecordDao.deletEntity(t);
	}

	@Override
	public PayRecord addPayRecord(List<BSRecord> bsRecordList,
			List<TabletRecord> tabletRecord, List<PayDetail> payDetailList,
			Member member, User user) {
		PayRecord payRecord = new PayRecord();
		float total = 0f;
		for(BSRecord t : bsRecordList){
			total += t.getBsRecToltalPrice();
		}
		for(TabletRecord t : tabletRecord){
			total += t.getTlTotalPrice();
		}
		for(PayDetail t : payDetailList){
			total += t.getDetTotalPrice();
		}
		payRecord.setTotalPrice(total);
		payRecord.setMem(member);
		payRecord.setPayUser(user);
		payRecord.setPayDate(new Date());
		payRecord.setOrderCode(CommonUtil.getUniqueCode());
		this.payRecordDao.saveEntity(payRecord);
		for(BSRecord t : bsRecordList){
			BSRecord tmp = this.bsRecordService.getById(t.getBsRecId());
			if(this.bsRecordService.getIsSelled(tmp.getBlessSeat().getBsId())){
				throw new AppRuntimeException("同一个福位同时只能被一个会员捐赠或租赁。");
			}
			tmp.setBsRecCreateDate(new Date());
			tmp.setBsRecToltalPrice(t.getBsRecToltalPrice());
			tmp.setBsRecUser(t.getBsRecUser());
			tmp.setDonatLength(t.getDonatLength());
			tmp.setDonatOverdue(DateUtil.getAfterYears(new Date(), t.getDonatLength()));
			tmp.setPayed(true);
			tmp.setPayRecord(payRecord);
			this.bsRecordService.saveOrUpdate(tmp);
		}
		for(TabletRecord t : tabletRecord){
			if(this.tabletRecordService.getIsSelled(t.getTablet().getTabletId())){
				throw new AppRuntimeException("同一个牌位同时只能被一个会员捐赠。");
			}
			t.setPayRecord(payRecord);
			this.tabletRecordService.add(t);
		}
		for(PayDetail t : payDetailList){
			t.setPayRecord(payRecord);
			this.payDetailService.add(t);
		}
		return payRecord;
	}

	@Override
	public PayRecord addPayRecord(List<BSRecord> bsRecordList,
			List<TabletRecord> tabletRecord, List<PayDetail> payDetailList,
			Enterprise enterprise, User user) {
		PayRecord payRecord = new PayRecord();
		float total = 0f;
		for(BSRecord t : bsRecordList){
			total += t.getBsRecToltalPrice();
		}
		for(TabletRecord t : tabletRecord){
			total += t.getTlTotalPrice();
		}
		for(PayDetail t : payDetailList){
			total += t.getDetTotalPrice();
		}
		payRecord.setTotalPrice(total);
		payRecord.setEnterprise(enterprise);
		payRecord.setPayUser(user);
		payRecord.setPayDate(new Date());
		this.payRecordDao.saveEntity(payRecord);
		for(BSRecord t : bsRecordList){
			BSRecord tmp = this.bsRecordService.getById(t.getBsRecId());
			if(this.bsRecordService.getIsSelled(tmp.getBlessSeat().getBsId())){
				throw new AppRuntimeException("同一个福位同时只能被一个会员捐赠或租赁。");
			}
			tmp.setBsRecCreateDate(new Date());
			tmp.setBsRecToltalPrice(t.getBsRecToltalPrice());
			tmp.setBsRecUser(t.getBsRecUser());
			tmp.setDonatLength(t.getDonatLength());
			tmp.setDonatOverdue(DateUtil.getAfterYears(new Date(), t.getDonatLength()));
			tmp.setPayed(true);
			tmp.setPayRecord(payRecord);
			this.bsRecordService.saveOrUpdate(tmp);
		}
		for(TabletRecord t : tabletRecord){
			if(this.tabletRecordService.getIsSelled(t.getTablet().getTabletId())){
				throw new AppRuntimeException("同一个牌位同时只能被一个会员捐赠。");
			}
			t.setPayRecord(payRecord);
			this.tabletRecordService.add(t);
		}
		for(PayDetail t : payDetailList){
			t.setPayRecord(payRecord);
			this.payDetailService.add(t);
		}
		return payRecord;
	}

	@Override
	public void addBSRToShopBusOnMember(String[] blessSeatIds, Member member, User user, DonationType buyType) {
		if(blessSeatIds == null){
			return;
		}
		for(String id : blessSeatIds){
			if(this.bsRecordService.getIsSelled(id)){
				continue;
			}
			BlessSeat bs = this.blessSeatService.getById(id);
			BSRecord bsr = new BSRecord();
			bsr.setBlessSeat(bs);
			bsr.setBsRecCreateDate(new Date());
			bsr.setBsRecToltalPrice(bs.getLev().getLevPrice());
			bsr.setBsRecUser(user);
			bsr.setDonatLength(1);
			bsr.setDonatOverdue(DateUtil.getAfterYears(new Date(), 1));
			bsr.setMem(member);
			bsr.setPayed(false);
			bsr.setPayRecord(null);
			bsr.setPermit(true);
			bsr.setDonatType(buyType);
			this.bsRecordService.add(bsr);
		}
	}

	@Override
	public List<BSRecord> getUnPayedRSRecodes(String memberId) {
		return this.bsRecordService.getUnPayedRSRecodes(memberId);
	}

	@Override
	public void addBSRToShopBusOnEnterprise(String[] blessSeatIds,
			Enterprise enterprise, User user, DonationType buyType) {
		if(blessSeatIds == null){
			return;
		}
		for(String id : blessSeatIds){
			if(this.bsRecordService.getIsSelled(id)){
				continue;
			}
			BlessSeat bs = this.blessSeatService.getById(id);
			BSRecord bsr = new BSRecord();
			bsr.setBlessSeat(bs);
			bsr.setBsRecCreateDate(new Date());
			bsr.setBsRecToltalPrice(bs.getLev().getLevPrice());
			bsr.setBsRecUser(user);
			bsr.setDonatLength(1);
			bsr.setDonatOverdue(DateUtil.getAfterYears(new Date(), 1));
			bsr.setEnterprise(enterprise);
			bsr.setPayed(false);
			bsr.setPayRecord(null);
			bsr.setPermit(true);
			bsr.setDonatType(buyType);
			this.bsRecordService.add(bsr);
		}
	}

	@Override
	public Date getMinDate() {
		return this.payRecordDao.getMinDate();
	}

	@Override
	public List<PayRecord> getAllByDate(Pager pager, Date start, Date end, Map<String, SortType> sorts) {
		return this.payRecordDao.getAllByDate(pager,start,end,sorts);
	}

	@Override
	public PayRecord getFetchPayRecord(String payRecId) {
		PayRecord result = this.payRecordDao.getEntityById(payRecId);
		if(result == null){
			return null;
		}
		for(BSRecord bs : result.getBsRecordSet()){
			bs.getBlessSeat();
		}
		for(TabletRecord tbr : result.getTlRecordSet()){
			tbr.getTablet();
		}
		for(PayDetail pd : result.getPayDatailSet()){
			pd.getDetailId();
		}
		return result;
	}

	@Override
	public List<PayRecord> getAllByDateAndSaler(Pager pager, Date start,
			Date end, Saler saler, Map<String, SortType> sorts) {
		return this.payRecordDao.getAllByDateAndSaler(pager,start,end,saler,sorts);
	}


}
