package com.sunjee.btms.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.TabletRecord;
import com.sunjee.btms.dao.TabletRecordDao;

@Repository("tabletRecordDao")
public class TabletRecordDaoImpl extends SupportDaoImpl<TabletRecord> implements TabletRecordDao{

	private static final long serialVersionUID = 3244255604807802488L;

}
