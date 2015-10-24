package com.sunjee.btms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.PreSell;
import com.sunjee.btms.bean.PreSellSummary;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.PreSellSummaryDao;
import com.sunjee.btms.exception.AppRuntimeException;
import com.sunjee.btms.service.PreSellService;
import com.sunjee.btms.service.PreSellSummaryService;
import com.sunjee.util.DateUtil;
import com.sunjee.util.HqlNoEquals;

@Service("preSellSummaryService")
public class PreSellSummaryServiceImpl implements PreSellSummaryService {

	private PreSellSummaryDao preSellSummaryDao;
	private PreSellService preSellService;

	public PreSellSummaryDao getPreSellSummaryDao() {
		return preSellSummaryDao;
	}

	@Resource(name = "preSellSummaryDao")
	public void setPreSellSummaryDao(PreSellSummaryDao preSellSummaryDao) {
		this.preSellSummaryDao = preSellSummaryDao;
	}

	public PreSellService getPreSellService() {
		return preSellService;
	}

	@Resource(name="preSellService")
	public void setPreSellService(PreSellService preSellService) {
		this.preSellService = preSellService;
	}

	@Override
	public DataGrid<PreSellSummary> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		this.initSummaryByDate(new Date(), true);
		return this.preSellSummaryDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public PreSellSummary add(PreSellSummary t) {
		return this.preSellSummaryDao.saveEntity(t);
	}

	@Override
	public void update(PreSellSummary t) {
		this.preSellSummaryDao.updateEntity(t);
	}

	@Override
	public List<PreSellSummary> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.preSellSummaryDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public PreSellSummary getById(String id) {
		return this.preSellSummaryDao.getEntityById(id);
	}

	@Override
	public void delete(PreSellSummary t) {
		this.preSellSummaryDao.deletEntity(t);
	}

	@Override
	public void initSumOfBefore() {
		String selectors[] = new String[1];
		selectors[0] = "min(createDate)";
		List<Object> result = this.preSellService.getParam("min(createDate)",null, null, null);
		if (result == null || result.size() < 1) {
			return;
		}
		Date minDate = (Date) result.get(0);
		Date currentDate = new Date();
		while(minDate.before(currentDate)){
			initSummaryByDate(minDate, false);
			minDate = DateUtils.addDays(minDate, 1);
		}
	}

	@Override
	public void initSummaryByDate(Date day, boolean isCover) {
		if(day == null){
			throw new AppRuntimeException("统计日期不能为null");
		}
		
		Date starteDateTime = DateUtil.getStartTimeOfDay(day);
		Date endDateTime = DateUtil.getEndTimeOfDay(day);
		Map<String, Object> whereParams = new HashMap<>();
		whereParams.put("createDate", new HqlNoEquals(starteDateTime, endDateTime));
		List<PreSellSummary> result = this.preSellSummaryDao.getEntitys(null, whereParams, null);
		if(result != null && result.size() > 0){
			if(isCover){
				for(PreSellSummary preSellSummary : result){
					this.preSellSummaryDao.deletEntity(preSellSummary);
				}
			}
			else{
				return;
			}
		}
		
		PreSellSummary preSellSummary = getSummaryByDay(day);
		if(preSellSummary == null){
			return;
		}
		preSellSummary.setCreateDate(day);
		this.preSellSummaryDao.saveEntity(preSellSummary);
	}

	private PreSellSummary getSummaryByDay(Date day) {
		PreSellSummary presellSum = new PreSellSummary();
		
		Date starteDateTime = DateUtil.getStartTimeOfDay(day);
		Date endDateTime = DateUtil.getEndTimeOfDay(day);
		Map<String, Object> whereParams = new HashMap<>();
		
		whereParams.put("createDate", new HqlNoEquals(starteDateTime, endDateTime));
		whereParams.put("permit", true);
		
		List<PreSell> psList = this.preSellService.getAllByParams(null, whereParams, null);
		for (PreSell preSell : psList) {
			presellSum.setPsCount(presellSum.getPsCount() + preSell.getPsCount());
			presellSum.setPsTotal(presellSum.getPsTotal() + preSell.getTotalPrice());
			if(preSell.isCash()){
				presellSum.setCashCount(presellSum.getCashCount() + preSell.getPsCount());
				presellSum.setPsCharge(presellSum.getPsCharge() + preSell.getTotalPrice());
				presellSum.setShouldCharge(presellSum.getShouldCharge() + preSell.getShouldCharge());
				presellSum.setRealCharge(presellSum.getRealCharge() + preSell.getRealCharge());
			}
		}
		presellSum.setTotal(presellSum.getPsTotal() + presellSum.getRealCharge());
		return presellSum;
	}
}
