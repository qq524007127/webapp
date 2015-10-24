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
import com.sunjee.btms.bean.Saler;
import com.sunjee.btms.bean.SalesSummary;
import com.sunjee.btms.bean.TabletRecord;
import com.sunjee.btms.common.Constant;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.DonationType;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.DataSummaryDao;
import com.sunjee.btms.dao.SalesSummaryDao;
import com.sunjee.btms.exception.AppRuntimeException;
import com.sunjee.btms.service.PayRecordService;
import com.sunjee.btms.service.SalerService;
import com.sunjee.btms.service.SalesSummaryService;
import com.sunjee.util.DateUtil;
import com.sunjee.util.HqlNoEquals;
import com.sunjee.util.HqlNullType;

@Service("salesSummaryService")
public class SalesSummaryServiceImpl implements SalesSummaryService {
	
	@Resource(name="salesSummaryDao")
	private SalesSummaryDao salesSummaryDao;
	@Resource(name="payRecordService")
	private PayRecordService payRecordService;
	@Resource(name="dataSummaryDao")
	private DataSummaryDao dataSummaryDao;
	@Resource(name="salerService")
	private SalerService salerService;

	@Override
	public DataGrid<SalesSummary> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		initSummaryByDate(new Date(),true);
		return this.salesSummaryDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public SalesSummary add(SalesSummary t) {
		return this.salesSummaryDao.saveEntity(t);
	}

	@Override
	public void update(SalesSummary t) {
		this.salesSummaryDao.updateEntity(t);
	}

	@Override
	public List<SalesSummary> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.salesSummaryDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public SalesSummary getById(String id) {
		return this.salesSummaryDao.getEntityById(id);
	}

	@Override
	public void delete(SalesSummary t) {
		this.salesSummaryDao.deletEntity(t);
	}

	@Override
	public void initSummaryOnBefore(Date date) {
		Date minDate = this.payRecordService.getMinDate();
		if(minDate == null){
			return;	//如果为空则说明还没有收费记录，则不用统计
		}
		while(minDate.before(date)){	
			initSummaryByDate(minDate,false);
			minDate = DateUtils.addDays(minDate, 1);	//一直统计到当前时间
		}
	}

	@Override
	public void initSummaryByDate(Date date, boolean isCover) {
		if(date == null){
			throw new AppRuntimeException("统计日期不能为null");
		}
		Date startDate = DateUtil.getStartTimeOfDay(date);
		Date endDate = DateUtil.getEndTimeOfDay(date);
		List<SalesSummary> sums = this.salesSummaryDao.getAllByDate(null,startDate,endDate,null,null);
		/*
		 * 如果已经有记录
		 */
		if(sums != null && sums.size() > 0){
			if(!isCover){
				return;	//如果不覆盖则退出
			}
			/**
			 * 删除原有记录
			 */
			for(SalesSummary ds : sums){
				this.salesSummaryDao.deletEntity(ds);
			}
		}
		
		SalesSummary _salesSum = getSummaryByDateAndSaler(date,null);
		if(_salesSum != null){
			this.add(_salesSum);
		}
		
		List<Saler> salers = this.salerService.getAllByParams(null, null, null);
		for(Saler saler : salers){
			_salesSum = getSummaryByDateAndSaler(date,saler);
			if(_salesSum == null){
				continue;
			}
			this.add(_salesSum);
		}
	}

	private SalesSummary getSummaryByDateAndSaler(Date date,Saler saler) {
		Date startDate = DateUtil.getStartTimeOfDay(date);
		Date endDate = DateUtil.getEndTimeOfDay(date);
		Map<String,Object> whereParams  = new HashMap<String,Object>();
		whereParams.put("createDate",new HqlNoEquals(startDate,endDate));
		if(saler != null){
			whereParams.put("saler.salerId", saler.getSalerId());
		}
		else {
			whereParams.put("saler", HqlNullType.isNull);
		}
		List<SalesSummary> salesSums = this.salesSummaryDao.getEntitys(null, whereParams, null);
		if(salesSums.size() > 0){
			return salesSums.get(0);
		}
		/*****======================****/
		List<PayRecord> prs = this.payRecordService.getAllByDateAndSaler(null,startDate,endDate,saler,null);
		/*****======================****/
		
		if(prs == null || prs.size() < 1){
			return null;
		}
		
		//List<PayRecord> prs = this.payRecordService.getAllByDate(null, start, end, null);
		SalesSummary ds = new SalesSummary();
		ds.setCreateDate(new Date());
		ds.setSaler(saler);
		for(PayRecord pr : prs){
			initBlessSeatData(ds,pr.getBsRecordSet());
			initTabletData(ds,pr.getTlRecordSet());
			initDetailData(ds,pr.getPayDatailSet());
		}
		/*ds.setBsRemain(this.blessSeatService.getRemainCount());
		ds.setTblRemain(this.tabletService.getRemainCount());*/
		return ds;
	}
	
	/**
	 * 初始化其它收费项目（包括：管理费，会员费，租赁费）
	 * @param ds
	 * @param payDatailSet
	 */
	private void initDetailData(SalesSummary ds, Set<PayDetail> payDatailSet) {
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
	private void initTabletData(SalesSummary ds, Set<TabletRecord> tlRecordSet) {
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
	private void initBlessSeatData(SalesSummary ds, Set<BSRecord> bsRecordSet) {
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
}
