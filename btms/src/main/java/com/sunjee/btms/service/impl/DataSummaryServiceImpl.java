package com.sunjee.btms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.BSRecord;
import com.sunjee.btms.bean.DataSummary;
import com.sunjee.btms.bean.PayDetail;
import com.sunjee.btms.bean.PayRecord;
import com.sunjee.btms.bean.TabletRecord;
import com.sunjee.btms.common.Constant;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.DonationType;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.DataSummaryDao;
import com.sunjee.btms.exception.AppRuntimeException;
import com.sunjee.btms.service.BlessSeatService;
import com.sunjee.btms.service.DataSummaryService;
import com.sunjee.btms.service.PayRecordService;
import com.sunjee.btms.service.TabletService;
import com.sunjee.util.DateUtil;
import com.sunjee.util.HqlNoEquals;

@Service("dataSummaryService")
public class DataSummaryServiceImpl implements DataSummaryService {

	private DataSummaryDao dataSummaryDao;
	private PayRecordService payRecordService;
	private BlessSeatService blessSeatService;
	private TabletService tabletService;
	
	public DataSummaryDao getDataSummaryDao() {
		return dataSummaryDao;
	}

	@Resource(name="dataSummaryDao")
	public void setDataSummaryDao(DataSummaryDao dataSummaryDao) {
		this.dataSummaryDao = dataSummaryDao;
	}

	public PayRecordService getPayRecordService() {
		return payRecordService;
	}
	
	@Resource(name="payRecordService")
	public void setPayRecordService(PayRecordService payRecordService) {
		this.payRecordService = payRecordService;
	}

	public BlessSeatService getBlessSeatService() {
		return blessSeatService;
	}
	
	@Resource(name="blessSeatService")
	public void setBlessSeatService(BlessSeatService blessSeatService) {
		this.blessSeatService = blessSeatService;
	}

	public TabletService getTabletService() {
		return tabletService;
	}

	@Resource(name="tabletService")
	public void setTabletService(TabletService tabletService) {
		this.tabletService = tabletService;
	}

	@Override
	public DataGrid<DataSummary> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		addSumOfDayAlways(new Date(), true);
		return this.dataSummaryDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public DataSummary add(DataSummary t) {
		return this.dataSummaryDao.saveEntity(t);
	}

	@Override
	public void update(DataSummary t) {
		this.dataSummaryDao.updateEntity(t);
	}

	@Override
	public List<DataSummary> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.dataSummaryDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public DataSummary getById(String id) {
		return this.dataSummaryDao.getEntityById(id);
	}

	@Override
	public void delete(DataSummary t) {
		this.dataSummaryDao.deletEntity(t);
	}

	@Override
	public void addSummaryOnBefore() {
		Date minDate = this.payRecordService.getMinDate();
		if(minDate == null){
			return;	//如果为空则说明还没有收费记录，则不用统计
		}
		//Date now = DateUtils.addDays(new Date(), -1);
		Date now = new Date();
		while(minDate.before(now)){	
			initSummaryByDate(minDate,false);
			minDate = DateUtils.addDays(minDate, 1);	//一直统计到当前时间
		}
	}

	@Override
	public void initSummaryByDate(Date date,boolean isCover) {
		if(date == null){
			throw new AppRuntimeException("统计日期不能为null");
		}
		Date start = DateUtil.getStartTimeOfDay(date);
		Date end = DateUtil.getEndTimeOfDay(date);
		List<DataSummary> sums = this.dataSummaryDao.getAllByDate(null,start,end,null,null);
		/*
		 * 如果已经有记录
		 */
		if(sums != null && sums.size() > 0){
			/**
			 * 如果覆盖原有记录则删除原有记录否则退出
			 */
			if(isCover){
				for(DataSummary ds : sums){
					this.dataSummaryDao.deletEntity(ds);
				}
			}
			else{
				return;
			}
		}
		add(getSumOfDayByEndDate(date));
	}
	

	@Override
	public DataSummary getSumOfDayByEndDate(Date date) {
		Date start = DateUtil.getStartTimeOfDay(date);
		Date endDate = DateUtil.getEndTimeOfDay(date);
		Map<String, Object> param = new HashMap<>();
		param.put("createDate", new HqlNoEquals(start, endDate));
		List<DataSummary> list = this.dataSummaryDao.getEntitys(null, param, null);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		
		/*****======================****/
		param.clear();
		param.put("payDate", new HqlNoEquals(start, endDate));
		param.put("type", PayRecord.COMMON_TYPE);
		List<PayRecord> prs = this.payRecordService.getAllByParams(null, param, null);
		/*****======================****/
		
		//List<PayRecord> prs = this.payRecordService.getAllByDate(null, start, end, null);
		DataSummary ds = new DataSummary();
		ds.setCreateDate(date);
		for(PayRecord pr : prs){
			initBlessSeatData(ds,pr.getBsRecordSet());
			initTabletData(ds,pr.getTlRecordSet());
			initDetailData(ds,pr.getPayDatailSet());
		}
		ds.setBsRemain(this.blessSeatService.getRemainCount());
		ds.setTblRemain(this.tabletService.getRemainCount());
		return ds;	
	}
	
	/**
	 * 初始化其它收费项目（包括：管理费，会员费，租赁费）
	 * @param ds
	 * @param payDatailSet
	 */
	private void initDetailData(DataSummary ds, Set<PayDetail> payDatailSet) {
		if(payDatailSet == null){
			return;
		}
		
		float leaseTotalPrice = 0f;	//租赁费
		
		int mngCount = 0;	//管理费笔数
		float mngTotalPrice = 0f;	//管理费总金额
		
		int memCount = 0;	//会员费收缴笔数
		float memTotalPrice = 0f;	//会员费总金额
		
		int itemCount = 0;	//其它费用收缴笔数
		float itemTotalPrice = 0f;	//其它费用总金额
		for(PayDetail item : payDatailSet){
			switch (item.getCostType()) {
			case Constant.BS_LEASE_COST_TYPE:
				leaseTotalPrice += item.getDetTotalPrice();
				break;
			case Constant.COMMON_COST_TYPE:
				itemCount ++;
				itemTotalPrice += item.getDetTotalPrice();
				break;
			case Constant.MANAGE_COST_TYPE:
				mngCount ++;
				mngTotalPrice += item.getDetTotalPrice();
				break;
			case Constant.MEMBER_COST_TYPE:
				memCount ++;
				memTotalPrice += item.getDetTotalPrice();
				break;

			default:
				break;
			}
		}
		ds.setBsLeaseTotalPrice(leaseTotalPrice + ds.getBsLeaseTotalPrice());
		ds.setMngRecCount(mngCount + ds.getMngRecCount());
		ds.setMngTotalPrice(mngTotalPrice + ds.getMngTotalPrice());
		ds.setMemberCount(memCount + ds.getMemberCount());
		ds.setMemberTotalPrice(memTotalPrice + ds.getMemberTotalPrice());
		ds.setItemCount(itemCount + ds.getItemCount());
		ds.setItemTotalPrice(itemTotalPrice + ds.getItemTotalPrice());
	}

	/**
	 * 初始化牌位统计
	 * @param ds
	 * @param tlRecordSet
	 */
	private void initTabletData(DataSummary ds, Set<TabletRecord> tlRecordSet) {
		if(tlRecordSet == null){
			return;
		}
		int buyCount = 0;
		float buyTotal = 0f;
		for(TabletRecord tbr : tlRecordSet){
			buyCount ++;
			buyTotal += tbr.getTlTotalPrice();
		}
		ds.setTblBuyCount(buyCount + ds.getTblBuyCount());
		ds.setTblTotalPrice(buyTotal + ds.getTblTotalPrice());
	}

	/**
	 * 初始化福位统计
	 * @param ds
	 * @param bsRecordSet
	 */
	private void initBlessSeatData(DataSummary ds, Set<BSRecord> bsRecordSet) {
		if(bsRecordSet == null){
			return;
		}
		int buyCount = 0;	//捐赠数量
		float buyTotalPrice = 0f;	//捐赠金额
		int leaseCount = 0;	//租赁数量
		for(BSRecord bs : bsRecordSet){
			if(bs.getDonatType().equals(DonationType.buy)){
				buyCount ++;
				buyTotalPrice += bs.getBsRecToltalPrice();
			}
			else{
				leaseCount ++;
			}
		}
		ds.setBsBuyCount(buyCount + ds.getBsBuyCount());
		ds.setBsBuyTotalPrice(buyTotalPrice + ds.getBsBuyTotalPrice());
		ds.setBsLeaseCount(leaseCount + ds.getBsLeaseCount());
	}

	@Override
	public void addSumOfDayAlways(Date date, boolean cover) {
		initSummaryByDate(date, cover);
	}

}
