package com.sunjee.btms.service;

import java.util.List;

import com.sunjee.btms.bean.Relation;

public interface RelationService extends SupportService<Relation> {

	void delete(List<Relation> relationList);

}
