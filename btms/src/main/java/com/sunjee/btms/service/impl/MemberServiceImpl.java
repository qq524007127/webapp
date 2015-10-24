package com.sunjee.btms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.BSRecord;
import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.bean.Member;
import com.sunjee.btms.bean.TabletRecord;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.DonationType;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.BSRecordDao;
import com.sunjee.btms.dao.DeaderDao;
import com.sunjee.btms.dao.MemberCardDao;
import com.sunjee.btms.dao.MemberDao;
import com.sunjee.btms.dao.TabletRecordDao;
import com.sunjee.btms.service.MemberService;
import com.sunjee.util.HqlNoEquals;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	private MemberDao memberDao;
	private BSRecordDao bsRecordDao;
	private TabletRecordDao tabletRecordDao;
	private DeaderDao deaderDao;
	private MemberCardDao memberCardDao;

	public MemberDao getMemberDao() {
		return memberDao;
	}

	@Resource(name = "memberDao")
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
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
	
	@Resource(name="memberCardDao")
	public void setMemberCardDao(MemberCardDao memberCardDao) {
		this.memberCardDao = memberCardDao;
	}

	@Override
	public DataGrid<Member> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.memberDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public Member add(Member t) {
		return this.memberDao.saveEntity(t);
	}

	@Override
	public void update(Member t) {
		this.memberDao.updateEntity(t);
	}

	@Override
	public List<Member> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.memberDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public Member getById(String id) {
		return this.memberDao.getEntityById(id);
	}

	@Override
	public void delete(Member t) {
		this.memberDao.deletEntity(t);
	}

	@Override
	public void updatePermit(Member member, boolean b) {
		Map<String, Object> values = new HashMap<String, Object>();
		Map<String, Object> whereParams = new HashMap<String, Object>();

		/**
		 * 如果设置为无效则删除对应使用者，更新捐赠、租赁记录了即设置会员证为无效
		 */
		if(!b){
			whereParams.clear();
			whereParams.put("mem.memberId", member.getMemberId());
			List<BSRecord> bsrs = this.bsRecordDao.getEntitys(null, whereParams, null);
			//先删除使用捐赠福位的使用者，再将福位捐赠记录设为无效
			for(BSRecord bsr : bsrs){
				//如果已有使用者则先删除与之关联的使用者
				BlessSeat bs = bsr.getBlessSeat();
				if(bs.getDeader() != null){
					this.deaderDao.deletEntity(bs.getDeader());
				}
				
				//如果还未付款则直接删除
				if(!bsr.isPayed()){
					this.bsRecordDao.deletEntity(bsr);
				}
				//如果为捐赠且有效则设置为无效
				else if(bsr.getDonatType().equals(DonationType.buy) && bsr.isPermit()){
					bsr.setPermit(false);
					this.bsRecordDao.updateEntity(bsr);
				}
				//如果为租赁且未过期则设置为过期
				else if(bsr.getDonatType().equals(DonationType.lease) && bsr.getDonatOverdue().after(new Date())){
					bsr.setDonatOverdue(new Date());
					this.bsRecordDao.updateEntity(bsr);
				}
			}
			//将为捐赠的未过期的牌位设置过期时间未现在
			whereParams.clear();
			whereParams.put("mem.memberId", member.getMemberId());
			whereParams.put("tlRecOverdue", new HqlNoEquals(new Date(), HqlNoEquals.MORE));
			List<TabletRecord> tblList = this.tabletRecordDao.getEntitys(null, whereParams, null);
			for(TabletRecord tbl : tblList){
				tbl.setTlRecOverdue(new Date());
				this.tabletRecordDao.updateEntity(tbl);
			}
			
			//更新会员的会员证为无效
			values.clear();
			whereParams.clear();
			values.put("permit", false);
			whereParams.put("mem.memberId", member.getMemberId());
			this.memberCardDao.executeUpate(values, whereParams);
		}
		
		//更新会员有效状态
		values.clear();
		whereParams.clear();
		values.put("memberPermit", b);
		whereParams.put("memberId", member.getMemberId());
		this.memberDao.executeUpate(values, whereParams);
	}
}
