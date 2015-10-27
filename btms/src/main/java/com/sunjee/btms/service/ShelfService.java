package com.sunjee.btms.service;

import java.util.List;

import com.sunjee.btms.bean.Area;
import com.sunjee.btms.bean.Shelf;

public interface ShelfService extends SupportService<Shelf>{
	
	List<Area> getAreaList();
	/**
	 * 初始化福位架，初始化是添加福位架同时添加此福位架的福位
	 * @param shelf
	 */
	void initShelf(Shelf shelf);
	
	void updateShelfPermit(String[] split, boolean b);
	/**
	 * 福位架添加一行
	 * @param shelf 福位架
	 * @param shelfRow	行索引（从1开始）
	 * @param b	（新增行的福位是否有效）true:有效；false:无效
	 */
	Shelf addRow(Shelf shelf, int shelfRow, boolean b);
	/**
	 * 福位架添加一列
	 * @param shelf 福位架
	 * @param shelfRow	列索引（从1开始）
	 * @param b	（新增行的福位是否有效）true:有效；false:无效
	 */
	Shelf addColumn(Shelf shelf, int shelfColumn, boolean b);
	
	/**
	 * 在区域的指定行和列添加福位架
	 * @param area
	 * @param areaRow
	 * @param areaColumn
	 */
	Shelf addByArea(Area area, int areaRow, int areaColumn);

	/**
	 * 更新福位架的编号
	 * @param shelfId	福位架ID
	 * @param shelfCode		需要更改的编号架编号
	 */
	void updateShelfCode(String shelfId,String shelfCode);
}
