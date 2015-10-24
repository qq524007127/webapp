package com.sunjee.btms.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.BSRecord;
import com.sunjee.btms.bean.Enterprise;
import com.sunjee.btms.bean.Member;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.DonationType;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.BSRecordDao;
import com.sunjee.btms.service.BSRecordService;

@Service("bsRecordService")
public class BSRecordServiceImpl implements BSRecordService {

	private BSRecordDao bsRecordDao;

	public BSRecordDao getBsRecordDao() {
		return bsRecordDao;
	}

	@Resource(name = "bsRecordDao")
	public void setBsRecordDao(BSRecordDao bsRecordDao) {
		this.bsRecordDao = bsRecordDao;
	}

	@Override
	public DataGrid<BSRecord> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.bsRecordDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public BSRecord add(BSRecord t) {
		return this.bsRecordDao.saveEntity(t);
	}

	@Override
	public void update(BSRecord t) {
		this.bsRecordDao.updateEntity(t);
	}

	@Override
	public List<BSRecord> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.bsRecordDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public BSRecord getById(String id) {
		return this.bsRecordDao.getEntityById(id);
	}

	@Override
	public void delete(BSRecord t) {
		this.bsRecordDao.deletEntity(t);
	}

	@Override
	public boolean getIsSelled(Serializable blessSeatId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("blessSeat.bsId", blessSeatId);
		params.put("payed", true);
		List<BSRecord> bsrList = this.bsRecordDao.getEntitys(null, params, null);
		if(bsrList == null || bsrList.size() < 1){
			return false;	//如果没有捐赠过则返回false,表示未捐赠
		}
		for(BSRecord bsr : bsrList){
			if(bsr.getDonatType().equals(DonationType.buy) && bsr.isPermit()){
				return true;
			}
			else if(bsr.getDonatOverdue().after(new Date())){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<BSRecord> getUnPayedRSRecodes(String memberId) {
		return this.bsRecordDao.getUnPayedRSRecodes(memberId);
	}

	@Override
	public int deleteUnPayedByMember(String id, Member member) {
		return this.bsRecordDao.deleteUnPayedByMember(id,member);
	}

	@Override
	public void saveOrUpdate(BSRecord t) {
		this.bsRecordDao.saveOrUpdate(t);
	}

	@Override
	public int deleteUnPayedByEnterprise(String id, Enterprise enterprise) {
		return this.bsRecordDao.deleteUnPayedByEnterprise(id,enterprise);
	}

	@Override
	public int updatePermitState(String[] ids, boolean b) {
		int count = 0;
		if(ids == null){
			return count;
		}
		for(String id : ids){
			Map<String, Object> values = new HashMap<>();
			values.put("permit", false);
			Map<String, Object> whereParams = new HashMap<>();
			whereParams.put("bsRecId", id);
			whereParams.put("payed", true);
			whereParams.put("donatType", DonationType.buy);
			count += this.bsRecordDao.updateEntity(values, whereParams);
		}
		return count;
	}
	
	/**
	 * 删除未支付的捐赠记录
	 * @param ids	需要删除的捐赠记录ID
	 */
	@Override
	public int deleteUnPayedItems(String[] ids) {
		int count = 0;
		if(ids == null){
			return count;
		}
		for(String id : ids){
			Map<String, Object> whereParams = new HashMap<>();
			whereParams.put("bsRecId", id);
			whereParams.put("payed", false);
			count += this.bsRecordDao.executeDelete(whereParams);
		}
		return count;
	}

	@Override
	public int getPermitCount() {
		return this.bsRecordDao.getPermitCount();
	}
}
