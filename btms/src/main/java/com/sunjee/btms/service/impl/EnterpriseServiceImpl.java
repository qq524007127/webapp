package com.sunjee.btms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.BSRecord;
import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.bean.Enterprise;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.DonationType;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.BSRecordDao;
import com.sunjee.btms.dao.DeaderDao;
import com.sunjee.btms.dao.EnterpriseDao;
import com.sunjee.btms.dao.MemberCardDao;
import com.sunjee.btms.dao.TabletRecordDao;
import com.sunjee.btms.service.EnterpriseService;
import com.sunjee.util.HqlNoEquals;

@Service("enterpriseService")
public class EnterpriseServiceImpl implements EnterpriseService {
	
	private EnterpriseDao enterpriseDao;

	private BSRecordDao bsRecordDao;
	private TabletRecordDao tabletRecordDao;
	private DeaderDao deaderDao;
	private MemberCardDao memberCardDao;

	public EnterpriseDao getEnterpriseDao() {
		return enterpriseDao;
	}
	
	@Resource(name="enterpriseDao")
	public void setEnterpriseDao(EnterpriseDao enterpriseDao) {
		this.enterpriseDao = enterpriseDao;
	}

	public BSRecordDao getBsRecordDao() {
		return bsRecordDao;
	}

	@Resource(name = "bsRecordDao")
	public void setBsRecordDao(BSRecordDao bsRecordDao) {
		this.bsRecordDao = bsRecordDao;
	}

	public TabletRecordDao getTabletRecordDao() {
		return tabletRecordDao;
	}

	@Resource(name = "tabletRecordDao")
	public void setTabletRecordDao(TabletRecordDao tabletRecordDao) {
		this.tabletRecordDao = tabletRecordDao;
	}

	public DeaderDao getDeaderDao() {
		return deaderDao;
	}

	@Resource(name = "deaderDao")
	public void setDeaderDao(DeaderDao deaderDao) {
		this.deaderDao = deaderDao;
	}

	public MemberCardDao getMemberCardDao() {
		return memberCardDao;
	}

	@Resource(name = "memberCardDao")
	public void setMemberCardDao(MemberCardDao memberCardDao) {
		this.memberCardDao = memberCardDao;
	}

	@Override
	public DataGrid<Enterprise> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.enterpriseDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public Enterprise add(Enterprise t) {
		return this.enterpriseDao.saveEntity(t);
	}

	@Override
	public void update(Enterprise t) {
		this.enterpriseDao.updateEntity(t);
	}

	@Override
	public List<Enterprise> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.enterpriseDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public Enterprise getById(String id) {
		return this.enterpriseDao.getEntityById(id);
	}

	@Override
	public void delete(Enterprise t) {
		this.enterpriseDao.deletEntity(t);
	}

	@Override
	public void updatePermit(Enterprise enterprise, boolean b) {
		Map<String, Object> values = new HashMap<String, Object>();
		Map<String, Object> whereParams = new HashMap<String, Object>();
		
		whereParams.put("enterprise.enterId", enterprise.getEnterId());
		whereParams.put("donatType", DonationType.buy);
		whereParams.put("permit", true);
		List<BSRecord> bsrs = this.bsRecordDao.getEntitys(null, whereParams, null);
		//先删除使用捐赠福位的使用者，再将福位捐赠记录设为无效
		for(BSRecord bsr : bsrs){
			BlessSeat bs = bsr.getBlessSeat();
			if(bs.getDeader() != null){
				this.deaderDao.deletEntity(bs.getDeader());
			}
			bsr.setPermit(false);
			this.bsRecordDao.updateEntity(bsr);
		}
		
		whereParams.clear();
		whereParams.put("enterprise.enterId", enterprise.getEnterId());
		whereParams.put("donatType", DonationType.lease);
		whereParams.put("donatOverdue", new HqlNoEquals(new Date(), HqlNoEquals.MORE));
		bsrs = this.bsRecordDao.getEntitys(null, whereParams, null);
		//先删除使用租赁福位的使用者，再将福位捐赠记录设为无效
		for(BSRecord bsr : bsrs){
			BlessSeat bs = bsr.getBlessSeat();
			if(bs.getDeader() != null){
				this.deaderDao.deletEntity(bs.getDeader());
			}
			bsr.setDonatOverdue(new Date());
			this.bsRecordDao.updateEntity(bsr);
		}
		
		//更新未到期的牌位为到期
		values.clear();
		whereParams.clear();
		values.put("tlRecOverdue", new Date());
		whereParams.put("enterprise.enterId", enterprise.getEnterId());
		whereParams.put("tlRecOverdue", new HqlNoEquals(new Date(), HqlNoEquals.MORE));
		this.tabletRecordDao.executeUpate(values, whereParams);
		
		//更新会员的会员证为无效
		values.clear();
		whereParams.clear();
		values.put("permit", false);
		whereParams.put("enterprise.enterId", enterprise.getEnterId());
		this.memberCardDao.executeUpate(values, whereParams);
				
		//设置企业为无效
		values.clear();
		whereParams.clear();
		values.put("enterPermit", false);
		whereParams.put("enterId", enterprise.getEnterId());
		this.enterpriseDao.executeUpate(values, whereParams);
	}

}
