package com.sunjee.btms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.Relation;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.RelationDao;
import com.sunjee.btms.service.RelationService;

@Service("relationService")
public class RelationServiceImpl implements RelationService {

	private RelationDao relationDao;
	
	
	public RelationDao getRelationDao() {
		return relationDao;
	}
	
	@Resource(name="relationDao")
	public void setRelationDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}

	@Override
	public DataGrid<Relation> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.relationDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public Relation add(Relation t) {
		return this.relationDao.saveEntity(t);
	}

	@Override
	public void update(Relation t) {
		this.relationDao.updateEntity(t);
	}

	@Override
	public List<Relation> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.relationDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public Relation getById(String id) {
		return this.relationDao.getEntityById(id);
	}

	@Override
	public void delete(Relation t) {
		this.relationDao.deletEntity(t);
	}

	@Override
	public void delete(List<Relation> relationList) {
		if(relationList == null)
			return;
		for(Relation r : relationList){
			this.delete(r);
		}
	}

}
