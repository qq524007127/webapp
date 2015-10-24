package com.sunjee.btms.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.Tablet;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.TabletDao;

@Repository("tabletDao")
public class TabletDaoImpl extends SupportDaoImpl<Tablet> implements TabletDao {

	private static final long serialVersionUID = 2258411968037497171L;

	@Override
	public DataGrid<Tablet> getDataEnableGrid(Pager pager,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		StringBuffer whereValue = new StringBuffer("");
		if(whereParams.containsKey("withoutIds") && whereParams.get("withoutIds") != null){
			whereValue.append("(");
			for(String tlId : (String[]) whereParams.get("withoutIds")){
				whereValue.append("'" + tlId +"',");
			}
			whereValue = new StringBuffer("tl.tabletId not in " + whereValue.substring(0, whereValue.length()-1) + ") and ");
		}
		if(whereParams.containsKey("searchKey") && whereParams.get("searchKey") != null){
			
			whereValue.append("tl.tabletName like '%").append(whereParams.get("searchKey")).append("%' and ");
		}
		
		StringBuffer hql = new StringBuffer("from Tablet tl where ").append(whereValue);
		hql.append("tl.tabletId not in ").append("( select distinct tr.tablet.tabletId from TabletRecord tr where tr.tlRecOverdue > :currentData and (tr.mem is not null or tr.enterprise is not null))");
		hql.append(" and tl.permit = :permit");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("currentData", new Date());
		params.put("permit",true);
		DataGrid<Tablet> dg = new DataGrid<>();
		dg.setTotal(getRecordTotal(hql.toString(), params));
		hql.append(" ").append(createSortHql(sortParams));
		dg.setRows(getEntitysByHql(pager, hql.toString(), params));
		return dg;
	}

}
