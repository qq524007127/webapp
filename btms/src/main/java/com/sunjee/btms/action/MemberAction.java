package com.sunjee.btms.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.Member;
import com.sunjee.btms.common.Constant;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.MemberService;

@Controller("memberAction")
@Scope("prototype")
public class MemberAction extends BaseAction<Member> implements
		ModelDriven<Member> {

	private static final long serialVersionUID = -6939488060224558663L;

	private MemberService memberService;

	private Member member;

	public MemberService getMemberService() {
		return memberService;
	}

	@Resource(name = "memberService")
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String grid() throws Exception {
		Map<String, Object> whereParams = getWhereParams();
		Map<String, SortType> sortParams = getSortParams();
		setDataGrid(this.memberService.getDataGrid(getPager(), whereParams,
				sortParams));
		return success();
	}

	public String add() throws Exception {
		if (StringUtils.isEmpty(this.member.getMemberPassword())) {
			this.member.setMemberPassword(Constant.INIT_PASSWORD);
		}
		if(member.getSaler() != null && StringUtils.isEmpty(member.getSaler().getSalerId())){
			member.setSaler(null);
		}
		this.memberService.add(member);
		return success();
	}
	
	public String edit() throws Exception {
		if (StringUtils.isEmpty(this.member.getMemberPassword())) {
			this.member.setMemberPassword(Constant.INIT_PASSWORD);
		}
		if(member.getSaler() != null && StringUtils.isEmpty(member.getSaler().getSalerId())){
			member.setSaler(null);
		}
		this.memberService.update(member);
		return success();
	}
	
	public String disable(){
		this.memberService.updatePermit(member,false);
		return success();
	}

	@Override
	public Member getModel() {
		if (this.member == null) {
			this.member = new Member();
		}
		return this.member;
	}
}
