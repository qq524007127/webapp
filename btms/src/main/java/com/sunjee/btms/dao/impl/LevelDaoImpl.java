package com.sunjee.btms.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.Level;
import com.sunjee.btms.dao.LevelDao;

@Repository("levelDao")
public class LevelDaoImpl extends SupportDaoImpl<Level> implements LevelDao {

	private static final long serialVersionUID = 3700383838695123145L;

}
