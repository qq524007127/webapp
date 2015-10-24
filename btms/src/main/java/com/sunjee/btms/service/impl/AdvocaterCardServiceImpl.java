package com.sunjee.btms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.AdvocaterCard;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.AdvocaterCardDao;
import com.sunjee.btms.exception.AppRuntimeException;
import com.sunjee.btms.service.AdvocaterCardService;

@Service("advocaterCardService")
public class AdvocaterCardServiceImpl implements AdvocaterCardService {

	private AdvocaterCardDao advocaterCardDao;

	public AdvocaterCardDao getAdvocaterCardDao() {
		return advocaterCardDao;
	}

	@Resource(name = "advocaterCardDao")
	public void setAdvocaterCardDao(AdvocaterCardDao advocaterCardDao) {
		this.advocaterCardDao = advocaterCardDao;
	}

	@Override
	public DataGrid<AdvocaterCard> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.advocaterCardDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public AdvocaterCard add(AdvocaterCard t) {
		t.setCreateCardDate(new Date());
		t.setCardCode(getUniqueCode());
		return this.advocaterCardDao.saveEntity(t);
	}

	@Override
	public void update(AdvocaterCard t) {
		this.advocaterCardDao.updateEntity(t);
	}

	@Override
	public List<AdvocaterCard> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.advocaterCardDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public AdvocaterCard getById(String id) {
		return this.advocaterCardDao.getEntityById(id);
	}

	@Override
	public void delete(AdvocaterCard t) {
		this.advocaterCardDao.deletEntity(t);
	}
	
	@Override
	public String getUniqueCode(){
		String code = "1";
		String tmpCode = this.advocaterCardDao.getMaxCardCode();
		int index = 0;
		try {
			index = Integer.parseInt(tmpCode);
			index ++;
		} catch (Exception e) {
			throw new AppRuntimeException("编号只能为数字");
		}
		code = String.valueOf(index);
		while(code.length() < 6){
			code = "0" + code;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cardCode", code);
		List<AdvocaterCard> adList = this.advocaterCardDao.getEntitys(null, param, null);
		while(adList != null && adList.size() > 0){
			tmpCode = this.advocaterCardDao.getMaxCardCode();
			try {
				index = Integer.parseInt(tmpCode);
				index ++;
			} catch (Exception e) {
				throw new AppRuntimeException("编号只能为数字");
			}
			code = String.valueOf(index);
			while(code.length() < 6){
				code = "0" + code;
			}
		}
		return code;
	}

	@Override
	public int updateValue(AdvocaterCard advCard) {
		Map<String, Object> values = new HashMap<>();
		values.put("advName", advCard.getAdvName());
		values.put("advBirthday", advCard.getAdvBirthday());
		values.put("remark", advCard.getRemark());
		Map<String, Object> whereParams = new HashMap<>();
		whereParams.put("cardId", advCard.getCardId());
		return this.advocaterCardDao.updateEntity(values,whereParams);
	}

}
