package com.sunjee.btms.action;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.Enterprise;
import com.sunjee.btms.bean.Member;
import com.sunjee.btms.bean.MemberCard;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.EnterpriseService;
import com.sunjee.btms.service.MemberCardService;
import com.sunjee.btms.service.MemberService;

@Controller("memberCardAction")
@Scope("prototype")
public class MemberCardAction extends BaseAction<MemberCard> implements
		ModelDriven<MemberCard> {

	private static final long serialVersionUID = -6494127007303550746L;

	private MemberCardService memberCardService;
	private MemberService memberService;
	private EnterpriseService enterpriseService;
	
	private MemberCard memberCard;
	private String memberId;
	private String enterId;
	
	private DataGrid<Member> memberGrid;
	private DataGrid<Enterprise> enterpriseGrid;
	
	public MemberCardService getMemberCardService() {
		return memberCardService;
	}
	
	@Resource(name="memberCardService")
	public void setMemberCardService(MemberCardService memberCardService) {
		this.memberCardService = memberCardService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	@Resource(name="memberService")
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public EnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	@Resource(name="enterpriseService")
	public void setEnterpriseService(EnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public DataGrid<Member> getMemberGrid() {
		return memberGrid;
	}

	public void setMemberGrid(DataGrid<Member> memberGrid) {
		this.memberGrid = memberGrid;
	}

	public DataGrid<Enterprise> getEnterpriseGrid() {
		return enterpriseGrid;
	}

	public void setEnterpriseGrid(DataGrid<Enterprise> enterpriseGrid) {
		this.enterpriseGrid = enterpriseGrid;
	}

	public MemberCard getMemberCard() {
		return memberCard;
	}

	public void setMemberCard(MemberCard memberCard) {
		this.memberCard = memberCard;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getEnterId() {
		return enterId;
	}

	public void setEnterId(String enterId) {
		this.enterId = enterId;
	}

	public String grid(){
		Map<String, Object> whereParams = getWhereParams();
		//whereParams.put("permit", true);
		Map<String, SortType> sortParams = getSortParams(SortType.desc,"permit");
		setDataGrid(this.memberCardService.getDataGrid(getPager(), whereParams, sortParams));
		return success();
	}
	
	public String memberGrid(){
		Map<String, Object> whereParams = getWhereParams();
		whereParams.put("memberPermit", true);
		this.memberGrid = this.memberService.getDataGrid(getPager(), whereParams, getSortParams());
		return success();
	}
	
	public String enterpriseGrid(){
		Map<String, Object> whereParams = getWhereParams();
		whereParams.put("enterPermit", true);
		this.enterpriseGrid = this.enterpriseService.getDataGrid(getPager(), whereParams, getSortParams());
		return success();
	}
	
	/**
	 * 调用此方法时不能同时提交memberId和enterId
	 * @return
	 */
	public String add(){
		System.out.print(memberCard);
		this.memberCardService.add(memberCard);
		return success();
	}
	
	/**
	 * 会员证挂失
	 * @return
	 */
	public String disable(){
		this.memberCardService.updatePermit(memberCard,false);
		//this.memberCardService.delete(memberCard);
		return success();
	}
	
	/**
	 * 补办会员证（删除以前的数据重新生成一条数据）
	 * @return
	 */
	public String reHandle(){
		this.memberCardService.deleteAndAdd(memberCard);
		return success();
	}
	
	@Override
	public MemberCard getModel() {
		if(this.memberCard == null){
			this.memberCard = new MemberCard();
		}
		return this.memberCard;
	}

}
