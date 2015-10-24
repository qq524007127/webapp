package com.sunjee.btms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.Member;
import com.sunjee.btms.bean.Relation;
import com.sunjee.btms.service.RelationService;

@Controller("relationAction")
@Scope("prototype")
public class RelationAction extends BaseAction<Relation> implements
		ModelDriven<Relation> {

	private static final long serialVersionUID = -7304004803135770257L;

	private RelationService relationService;
	
	private Relation relation;
	private String memberId;
	private String ids;

	
	public RelationService getRelationService() {
		return relationService;
	}

	@Resource(name="relationService")
	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String grid(){
		Map<String, Object> whereParams = getWhereParams();
		if(!StringUtils.isEmpty(memberId)){
			whereParams.put("mem.memberId", memberId);
		}
		setDataGrid(this.relationService.getDataGrid(getPager(), whereParams, getSortParams()));
		return success();
	}
	
	public String add(){
		Member member = new Member(memberId);
		this.relation.setMem(member);
		this.relationService.add(relation);
		return success();
	}
	
	public String edit(){
		Member member = new Member(memberId);
		this.relation.setMem(member);
		this.relationService.update(relation);
		return success();
	}
	
	public String delete(){
		if(!StringUtils.isEmpty(ids)){
			List<Relation> relationList = new ArrayList<Relation>();
			for(String id : ids.split(",")){
				Relation r = new Relation(id);
				relationList.add(r);
			}
			this.relationService.delete(relationList);
		}
		return success();
	}
	
	@Override
	public Relation getModel() {
		if(this.relation == null){
			this.relation = new Relation();
		}
		return this.relation;
	}

}
