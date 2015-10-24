package com.sunjee.btms.service;

import com.sunjee.btms.bean.Area;

public interface AreaService extends SupportService<Area> {
	/**
	 * 初始化一个区域，初始化时添加一个区域同时添加当前区域的福位架和福位
	 * @param area
	 */
	void initArea(Area area);
	
	/**
	 * 为指定区域添加行
	 * @param area
	 * @param areaRow
	 */
	void addRow(Area area, int areaRow, boolean shelfPermit);

	/**
	 * 为指定区域添加列
	 * @param area
	 * @param areaColumn
	 */
	void addColumn(Area area, int areaColumn, boolean shelfPermit);
}
