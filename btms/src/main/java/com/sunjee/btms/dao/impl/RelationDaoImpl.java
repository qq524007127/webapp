package com.sunjee.btms.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.Relation;
import com.sunjee.btms.dao.RelationDao;

@Repository("relationDao")
public class RelationDaoImpl extends SupportDaoImpl<Relation> implements RelationDao{

	private static final long serialVersionUID = -5121105059410087842L;

}
