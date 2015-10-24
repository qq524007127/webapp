package com.sunjee.util;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.sunjee.btms.bean.PayRecord;
import com.sunjee.btms.bean.PreSell;
import com.sunjee.btms.common.Constant;

public class PreSellExcelUtil extends PayInfoExcelUtil{

	private static final long serialVersionUID = -2003842512302803807L;

	protected PreSell preSell;
	
	private PreSellExcelUtil(PayRecord payRecord, ServletContext context) {
		super(payRecord, context);
	}
	
	public PreSellExcelUtil(PreSell preSell, ServletContext context) {
		this(preSell.getpRecord(), context);
		this.preSell = preSell;
	}
	
	/**
	 * 生成福位预售缴费清单
	 * @return
	 * @throws IOException
	 */
	public HSSFWorkbook getPreSellExcel() throws IOException {
		HSSFWorkbook book = new HSSFWorkbook(context.getResourceAsStream(Constant.PAYINFO_TEMPLATE_NAME));
		initStyleAndFont(book);
		Sheet sheet = book.getSheetAt(0);
		book.setSheetName(0, "预售缴费清单");
		for(int i = 0; i < 4; i ++){
			sheet.setColumnWidth(i, columnWidth);
		}
		createMemberInfoRow(sheet);
		createPreSellInfo(sheet);
		
		createPageFooter(sheet,preSell.getTotalPrice());
		
		return book;
	}
	
	/**
	 * 生成福位预售补单
	 * @return
	 * @throws IOException
	 */
	public HSSFWorkbook getPreSellCashExcel() throws IOException {
		HSSFWorkbook book = new HSSFWorkbook(context.getResourceAsStream(Constant.PAYINFO_TEMPLATE_NAME));
		initStyleAndFont(book);
		Sheet sheet = book.getSheetAt(0);
		book.setSheetName(0, "预售缴费清单");
		for(int i = 0; i < 4; i ++){
			sheet.setColumnWidth(i, columnWidth);
		}
		createMemberInfoRow(sheet);
		createPreSellCashInfo(sheet);
		
		createPageFooter(sheet,preSell.getRealCharge());
		
		return book;
	}
	
	private void createPreSellCashInfo(Sheet sheet) {
		Row row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(titleRowHeigth);
		for(int i = 0; i < 4; i ++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
		}
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 3));
		row.getCell(0).setCellValue("预售补单收费");

		row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(headRowHeith);
		String heads[] = new String[]{"预售单号","预售定金","应收金额","实收金额"};
		for(int i = 0; i < heads.length; i ++){
			Cell cell = row.createCell(i);
			cell.setCellValue(heads[i]);
			cell.setCellStyle(headStyle);
		}
		
		row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(itemRowHeith);
		for(int i = 0; i < 4; i ++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(itemStyle);
			switch (i) {
			case 0:
				cell.setCellValue(preSell.getOrderCode());
				break;
			case 1:
				cell.setCellValue(preSell.getTotalPrice());
				break;
			case 2:
				cell.setCellValue(preSell.getShouldCharge());
				break;
			case 3:
				cell.setCellValue(preSell.getRealCharge());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 初始化预售项目清单
	 * @param sheet
	 */
	private void createPreSellInfo(Sheet sheet){
		Row row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(titleRowHeigth);
		for(int i = 0; i < 4; i ++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
		}
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 3));
		row.getCell(0).setCellValue("预售收费");

		row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(headRowHeith);
		String heads[] = new String[]{"预售单号","预售数量","单价","总价"};
		for(int i = 0; i < heads.length; i ++){
			Cell cell = row.createCell(i);
			cell.setCellValue(heads[i]);
			cell.setCellStyle(headStyle);
		}
		
		row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(itemRowHeith);
		for(int i = 0; i < 4; i ++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(itemStyle);
			switch (i) {
			case 0:
				cell.setCellValue(preSell.getOrderCode());
				break;
			case 1:
				cell.setCellValue(preSell.getPsCount());
				break;
			case 2:
				cell.setCellValue(preSell.getPsPrice());
				break;
			case 3:
				cell.setCellValue(preSell.getTotalPrice());
				break;
			default:
				break;
			}
		}
	}

}
