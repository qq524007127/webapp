package com.sunjee.btms.dao.impl;


import org.springframework.stereotype.Repository;

import com.sunjee.btms.bean.Shelf;
import com.sunjee.btms.dao.ShelfDao;

@Repository("shelfDao")
public class ShelfDaoImpl extends SupportDaoImpl<Shelf> implements ShelfDao {

	private static final long serialVersionUID = -2474570399674141680L;

}
