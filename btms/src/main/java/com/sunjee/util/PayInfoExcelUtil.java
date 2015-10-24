package com.sunjee.util;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.sunjee.btms.bean.BSRecord;
import com.sunjee.btms.bean.PayDetail;
import com.sunjee.btms.bean.PayRecord;
import com.sunjee.btms.bean.TabletRecord;
import com.sunjee.btms.common.Constant;
import com.sunjee.btms.common.DonationType;
import com.sunjee.component.bean.BaseBean;

public class PayInfoExcelUtil extends BaseBean {

	private static final long serialVersionUID = -8585410259282025881L;
	
	protected PayRecord payRecord;
	protected ServletContext context;
	protected int currentRowIndex = 0;
	
	protected int titleRowHeigth = 30;	//标题行高
	protected int headRowHeith = 25;	//表头行高
	protected int itemRowHeith = 25; 	//项目行高
	//protected int columnWidth = 300 * 25;	//列宽(适应A4纸)
	protected int columnWidth = 6086; //列宽（适应羊杆纸241*140）
	protected String defaultFontName = "宋体";
	protected short defualtFontSize = 11;
	protected short titleFontSize = 12;
	
	protected CellStyle infoStyle;
	protected CellStyle summarySytel;
	protected CellStyle titleStyle;
	protected CellStyle headStyle;
	protected CellStyle itemStyle;
	protected CellStyle signatStyle;	//签名栏样式

	public PayInfoExcelUtil(PayRecord payRecord, ServletContext context) {
		this.payRecord = payRecord;
		this.context = context;
	}

	/**
	 * 生成普通缴费清单Excel文档
	 * @return
	 * @throws IOException 
	 */
	public HSSFWorkbook getPayinfoExcel() throws IOException {
		HSSFWorkbook book = new HSSFWorkbook(context.getResourceAsStream(Constant.PAYINFO_TEMPLATE_NAME));
		initStyleAndFont(book);
		Sheet sheet = book.getSheetAt(0);
		book.setSheetName(0, "缴费清单");
		for(int i = 0; i < 4; i ++){
			sheet.setColumnWidth(i, columnWidth);
		}
		createMemberInfoRow(sheet);
		createBsPayInfo(sheet);
		createTabletPayInfo(sheet);
		createDetailPayInfo(sheet);
		
		createPageFooter(sheet);
		
		return book;
	}
	
	
	
	protected void initStyleAndFont(HSSFWorkbook book) {
		infoStyle = book.createCellStyle();
		infoStyle.setAlignment(CellStyle.ALIGN_CENTER);
		infoStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Font infoFont = book.createFont();
		infoFont.setFontHeightInPoints(defualtFontSize);
		infoFont.setFontName(defaultFontName);
		infoStyle.setFont(infoFont);
		
		summarySytel = book.createCellStyle();
		summarySytel.setAlignment(CellStyle.ALIGN_LEFT);
		summarySytel.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font sumFont = book.createFont();
		//sumFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		sumFont.setFontHeightInPoints(defualtFontSize);
		sumFont.setFontName(defaultFontName);
		summarySytel.setFont(sumFont);
		
		titleStyle = book.createCellStyle();
		titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
		titleStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font titleFont = book.createFont();
		//titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		titleFont.setFontHeightInPoints(titleFontSize);
		titleFont.setFontName(defaultFontName);
		titleStyle.setFont(titleFont);
		
		headStyle = book.createCellStyle();
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font headFont = book.createFont();
		//headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headFont.setFontHeightInPoints(defualtFontSize);
		headFont.setFontName(defaultFontName);
		headStyle.setFont(headFont);
		
		itemStyle = book.createCellStyle();
		itemStyle.setAlignment(CellStyle.ALIGN_CENTER);
		itemStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font itemFont = book.createFont();
		itemFont.setFontHeightInPoints(defualtFontSize);
		itemFont.setFontName(defaultFontName);
		itemStyle.setFont(itemFont);
		
		signatStyle = book.createCellStyle();
		signatStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		signatStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font signatFont = book.createFont();
		//signatFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		signatFont.setFontHeightInPoints(defualtFontSize);
		signatFont.setFontName(defaultFontName);
		signatStyle.setFont(signatFont);
	}
	
	/**
	 * 生成其它收费清单
	 * @param sheet
	 */
	private void createDetailPayInfo(Sheet sheet) {
		if(payRecord.getPayDatailSet() == null || payRecord.getPayDatailSet().size() < 1){
			return;
		}
		Row row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(titleRowHeigth);
		for(int i = 0; i < 4; i ++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
		}
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 3));
		row.getCell(0).setCellValue("其它费用");

		row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(headRowHeith);
		String heads[] = new String[]{"收费项目名称","捐赠时长（年限）","单价","总价"};
		for(int i = 0; i < heads.length; i ++){
			Cell cell = row.createCell(i);
			cell.setCellValue(heads[i]);
			cell.setCellStyle(headStyle);
		}
		
		for(PayDetail pd : payRecord.getPayDatailSet()){
			row = sheet.createRow(currentRowIndex ++);
			row.setHeightInPoints(itemRowHeith);
			for(int i = 0; i < 4; i ++){
				Cell cell = row.createCell(i);
				cell.setCellStyle(itemStyle);
				switch (i) {
				case 0:
					cell.setCellValue(pd.getDetailItemName());
					break;
				case 1:
					cell.setCellValue(pd.getDetailLength());
					break;
				case 2:
					cell.setCellValue(pd.getItemPrice());
					break;
				case 3:
					cell.setCellValue(pd.getDetTotalPrice());
					break;

				default:
					break;
				}
			}
		}
	}

	/**
	 * 生成牌位收费清单
	 * @param sheet
	 */
	private void createTabletPayInfo(Sheet sheet) {
		if(payRecord.getTlRecordSet() == null || payRecord.getTlRecordSet().size() < 1){
			return;
		}
		Row row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(titleRowHeigth);
		for(int i = 0; i < 4; i ++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
		}
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 3));
		row.getCell(0).setCellValue("牌位捐赠费");

		row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(headRowHeith);
		String heads[] = new String[]{"牌位名称","捐赠时长（年限）","单价","总价"};
		for(int i = 0; i < heads.length; i ++){
			Cell cell = row.createCell(i);
			cell.setCellValue(heads[i]);
			cell.setCellStyle(headStyle);
		}
		
		for(TabletRecord tblr : payRecord.getTlRecordSet()){
			row = sheet.createRow(currentRowIndex ++);
			row.setHeightInPoints(itemRowHeith);
			for(int i = 0; i < 4; i ++){
				Cell cell = row.createCell(i);
				cell.setCellStyle(itemStyle);
				switch (i) {
				case 0:
					cell.setCellValue(tblr.getTablet().getTabletName());
					break;
				case 1:
					cell.setCellValue(tblr.getTlRecLength());
					break;
				case 2:
					cell.setCellValue(tblr.getTablet().getTabletPrice());
					break;
				case 3:
					cell.setCellValue(tblr.getTlTotalPrice());
					break;

				default:
					break;
				}
			}
		}
	}

	/**
	 * 生成福位捐赠清单
	 * @param sheet
	 */
	private void createBsPayInfo(Sheet sheet) {
		if(payRecord.getBsRecordSet() == null || payRecord.getBsRecordSet().size() < 1){
			return;
		}
		Row row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(titleRowHeigth);
		for(int i = 0; i < 4; i ++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
		}
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 3));
		row.getCell(0).setCellValue("福位捐赠（租赁）费");

		row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(headRowHeith);
		String heads[] = new String[]{"福位编号","捐赠方式","租赁时长（年限）","金额"};
		for(int i = 0; i < heads.length; i ++){
			Cell cell = row.createCell(i);
			cell.setCellValue(heads[i]);
			cell.setCellStyle(headStyle);
		}
		
		for(BSRecord bsr : payRecord.getBsRecordSet()){
			row = sheet.createRow(currentRowIndex ++);
			row.setHeightInPoints(itemRowHeith);
			for(int i = 0; i < 4; i ++){
				Cell cell = row.createCell(i);
				cell.setCellStyle(itemStyle);
				switch (i) {
				case 0:
					cell.setCellValue(bsr.getBlessSeat().getBsCode());
					break;
				case 1:
					String type = bsr.getDonatType().equals(DonationType.buy) ? "捐赠" : "租赁";
					cell.setCellValue(type);
					break;
				case 2:
					String text = bsr.getDonatType().equals(DonationType.buy) ? "/" : String.valueOf(bsr.getDonatLength());
					cell.setCellValue(text);
					break;
				case 3:
					String total = bsr.getDonatType().equals(DonationType.buy) ? String.valueOf(bsr.getBlessSeat().getLev().getLevPrice()) : "/";
					cell.setCellValue(total);
					break;

				default:
					break;
				}
			}
		}
	}

	/**
	 * 收费清单头部会员（企业）信息、收费日期和收费员
	 * @param sheet
	 */
	protected void createMemberInfoRow(Sheet sheet){
		Row infoRow = sheet.createRow(currentRowIndex ++);
		infoRow.setHeightInPoints(30);
		for(int i = 0; i < 4; i ++){
			Cell cell = infoRow.createCell(i);
			cell.setCellStyle(infoStyle);
			switch (i) {
			case 0:
				String name = null;
				if(payRecord.getMem() != null){
					name = payRecord.getMem().getMemberName();
				}
				else{
					name = payRecord.getEnterprise().getEnterName();
				}
				cell.setCellValue("名称：" + name);
				break;
			case 1:
				String cardCode = "";
				if(payRecord.getMem() != null && payRecord.getMem().getMemberCard() != null){
					cardCode = payRecord.getMem().getMemberCard().getCardCode();
				}
				else if(payRecord.getEnterprise() != null && payRecord.getEnterprise().getCard() != null){
					cardCode = payRecord.getEnterprise().getCard().getCardCode();
				}
				cell.setCellValue("会员证：" + cardCode);
				break;
			case 2:
				String payDate = DateUtil.parseString(payRecord.getPayDate(), "yyyy-MM-dd HH:mm:ss");
				cell.setCellValue("日期：" + payDate);
				break;
			case 3:
				cell.setCellValue("收费员：" + payRecord.getPayUser().getUserName());
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 生成页脚,默认总价为收费纪录总价
	 * @param sheet
	 */
	protected void createPageFooter(Sheet sheet) {
		createPageFooter(sheet,this.payRecord.getTotalPrice());
	}
	
	/**
	 * 生成页脚，自定义总价
	 * @param sheet
	 */
	protected void createPageFooter(Sheet sheet,float totalPrice){
		Row row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(headRowHeith);
		for(int i = 0; i < 4; i ++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(summarySytel);
		}
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),row.getRowNum(),0,3));
		row.getCell(0).setCellValue("合计:" + totalPrice);
		
		row = sheet.createRow(currentRowIndex ++);
		row.setHeightInPoints(headRowHeith);
		for(int i = 0; i < 4; i ++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(signatStyle);
		}
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),row.getRowNum(),0,2));
		row.getCell(0).setCellValue("签名:");
		Cell cell = row.getCell(row.getLastCellNum() - 1);
		CellStyle underLineStyle = sheet.getWorkbook().createCellStyle();
		underLineStyle.setBorderBottom(CellStyle.BORDER_THIN);
		underLineStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cell.setCellStyle(underLineStyle);
	}
}