package com.sunjee.btms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.Level;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.BlessSeatDao;
import com.sunjee.btms.dao.LevelDao;
import com.sunjee.btms.service.LevelServcie;

@Service("levelService")
public class LevelServiceImpl implements LevelServcie {
	
	private LevelDao levelDao;
	private BlessSeatDao blessSeatDao;
	

	public LevelDao getLevelDao() {
		return levelDao;
	}

	@Resource(name="levelDao")
	public void setLevelDao(LevelDao levelDao) {
		this.levelDao = levelDao;
	}


	public BlessSeatDao getBlessSeatDao() {
		return blessSeatDao;
	}

	@Resource(name="blessSeatDao")
	public void setBlessSeatDao(BlessSeatDao blessSeatDao) {
		this.blessSeatDao = blessSeatDao;
	}

	@Override
	public DataGrid<Level> getDataGrid(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.levelDao.getDataGrid(page, whereParams, sortParams);
	}

	@Override
	public Level add(Level level) {
		return this.levelDao.saveEntity(level);
	}

	@Override
	public void update(Level level) {
		this.levelDao.updateEntity(level);
		Map<String, Object> whereParams = new HashMap<String, Object>();
		Map<String, Object> valueParams = new HashMap<String, Object>();
		whereParams.put("lev.levId", level.getLevId());
		valueParams.put("managExpense", level.getMngPrice());
		this.blessSeatDao.executeUpate(valueParams, whereParams);
	}


	@Override
	public List<Level> getAllByParams(Pager page,
			Map<String, Object> whereParams, Map<String, SortType> sortParams) {
		return this.levelDao.getEntitys(page, whereParams, sortParams);
	}

	@Override
	public Level getById(String id) {
		return this.levelDao.getEntityById(id);
	}

	@Override
	public void delete(Level lev) {
		this.levelDao.deletEntity(lev);
	}

}
