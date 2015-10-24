package com.sunjee.btms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.BSRecord;
import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.bean.Enterprise;
import com.sunjee.btms.bean.Member;
import com.sunjee.btms.bean.PayRecord;
import com.sunjee.btms.bean.PreSell;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.DonationType;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.BlessSeatDao;
import com.sunjee.btms.dao.PreSellDao;
import com.sunjee.btms.exception.AppRuntimeException;
import com.sunjee.btms.service.BSRecordService;
import com.sunjee.btms.service.PayRecordService;
import com.sunjee.btms.service.PreSellService;
import com.sunjee.component.bean.User;
import com.sunjee.util.CommonUtil;

@Service("preSellService")
public class PreSellServiceImpl implements PreSellService {

	private PreSellDao preSellDao;
	private PayRecordService payRecordService;
	private BSRecordService bsRecordService;
	private BlessSeatDao blessSeatDao;

	public PreSellDao getPreSellDao() {
		return preSellDao;
	}

	@Resource(name = "preSellDao")
	public void setPreSellDao(PreSellDao preSellDao) {
		this.preSellDao = preSellDao;
	}

	public PayRecordService getPayRecordService() {
		return payRecordService;
	}

	@Resource(name = "payRecordService")
	public void setPayRecordService(PayRecordService payRecordService) {
		this.payRecordService = payRecordService;
	}

	public BSRecordService getBsRecordService() {
		return bsRecordService;
	}

	@Resource(name = "bsRecordService")
	public void setBsRecordService(BSRecordService bsRecordService) {
		this.bsRecordService = bsRecordService;
	}

	public BlessSeatDao getBlessSeatDao() {
		return blessSeatDao;
	}

	@Resource(name = "blessSeatDao")
	public void setBlessSeatDao(BlessSeatDao blessSeatDao) {
		this.blessSeatDao = blessSeatDao;
	}

	@Override
	public DataGrid<PreSell> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.preSellDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public PreSell add(PreSell t) {
		t.setCash(false);
		t.setOrderCode(CommonUtil.getUniqueCode());
		t.setPermit(true);
		t.setCreateDate(new Date());
		this.preSellDao.saveEntity(t);
		return t;
	}

	@Override
	public void update(PreSell t) {
		this.preSellDao.updateEntity(t);
	}

	@Override
	public List<PreSell> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.preSellDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public PreSell getById(String id) {
		return this.preSellDao.getEntityById(id);
	}

	@Override
	public void delete(PreSell t) {
		this.preSellDao.deletEntity(t);
	}

	@Override
	public PreSell addByMember(PreSell preSell, String memberId, User user) {
		PayRecord pr = new PayRecord();
		pr.setMem(new Member(memberId));
		pr.setPayDate(new Date());
		pr.setPayUser(user);
		pr.setTotalPrice(preSell.getTotalPrice());
		pr.setType(PayRecord.PRESELL_TYPE);
		pr.setOrderCode(CommonUtil.getUniqueCode());
		this.payRecordService.add(pr);

		preSell.setpRecord(pr);
		add(preSell);
		return preSell;
	}

	@Override
	public PreSell addByEnterprise(PreSell preSell, String enterpriseId,
			User user) {
		PayRecord pr = new PayRecord();
		pr.setEnterprise(new Enterprise(enterpriseId));
		pr.setPayDate(new Date());
		pr.setPayUser(user);
		pr.setTotalPrice(preSell.getTotalPrice());
		pr.setType(PayRecord.PRESELL_TYPE);
		pr.setOrderCode(CommonUtil.getUniqueCode());
		this.payRecordService.add(pr);

		preSell.setpRecord(pr);
		add(preSell);
		return preSell;
	}

	@Override
	public void updatePermit(String[] ids, boolean permit) {
		if (ids == null) {
			return;
		}
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("permit", permit);
		Map<String, Object> whereParams = new HashMap<>();
		for (String id : ids) {
			whereParams.put("psId", id);
			this.preSellDao.executeUpate(values, whereParams);
		}
	}

	@Override
	public void deleteByIds(String[] ids) {
		if (ids == null) {
			return;
		}
		for (String id : ids) {
			PreSell ps = this.preSellDao.getEntityById(id);
			if (!ps.isCash()) {
				delete(ps);
				this.payRecordService.delete(ps.getpRecord());
			}
		}
	}

	@Override
	public void executeCash(String psId, String[] bsIds, User user) {
		if (bsIds == null) {
			return;
		}
		for (String bsId : bsIds) {
			if (this.bsRecordService.getIsSelled(bsId)) {
				throw new AppRuntimeException("您选择的福位已被捐赠，请重新选择。");
			}
		}
		PreSell ps = this.preSellDao.getEntityById(psId);
		PayRecord tmp = ps.getpRecord();

		PayRecord pr = new PayRecord();
		List<BSRecord> bsrList = new ArrayList<>();

		if (tmp.getMem() != null) {
			pr.setMem(tmp.getMem());
		} else if (tmp.getEnterprise() != null) {
			pr.setEnterprise(tmp.getEnterprise());
		}
		pr.setPayDate(new Date());
		pr.setPayUser(user);
		pr.setType(PayRecord.PRESELL_TYPE);
		pr.setOrderCode(CommonUtil.getUniqueCode());
		float total = 0f;
		for (String bsId : bsIds) {
			BlessSeat bs = this.blessSeatDao.getEntityById(bsId);
			total += bs.getLev().getLevPrice();

			if (tmp.getMem() != null) {
				bsrList.add(getBsRecordByMember(bs, tmp.getMem()));
			} else if (tmp.getEnterprise() != null) {
				bsrList.add(getBsRecordByEnterprise(bs, tmp.getEnterprise()));
			}
		}
		pr.setTotalPrice(total);
		this.payRecordService.add(pr);

		ps.setCash(true);
		ps.setpRecord(pr);
		ps.setShouldCharge(total);
		ps.setRealCharge(total - ps.getTotalPrice());
		ps.setCashDate(new Date());
		this.preSellDao.updateEntity(ps);

		for (BSRecord bsr : bsrList) {
			bsr.setPayRecord(pr);
			bsr.setBsRecUser(user);
			this.bsRecordService.add(bsr);
		}
		this.payRecordService.delete(tmp);
	}

	private BSRecord getBsRecordByMember(BlessSeat bs, Member mem) {
		BSRecord bsr = initBSRecord(bs);
		bsr.setMem(mem);
		return bsr;
	}

	private BSRecord getBsRecordByEnterprise(BlessSeat bs, Enterprise enter) {
		BSRecord bsr = initBSRecord(bs);
		bsr.setEnterprise(enter);
		return bsr;
	}

	private BSRecord initBSRecord(BlessSeat bs) {
		BSRecord bsr = new BSRecord();
		bsr.setBlessSeat(new BlessSeat(bs.getBsId()));
		bsr.setPayed(true);
		bsr.setBsRecCreateDate(new Date());
		bsr.setBsRecToltalPrice(bs.getLev().getLevPrice());
		bsr.setDonatType(DonationType.buy);
		bsr.setPermit(true);
		return bsr;
	}

	@Override
	public List<Object> getParam(String selector, Pager pager,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.preSellDao.getParam(selector, pager, whereParams, sortParams);
	}
}
