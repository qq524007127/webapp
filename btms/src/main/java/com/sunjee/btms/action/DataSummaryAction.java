package com.sunjee.btms.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sunjee.btms.bean.DataSummary;
import com.sunjee.btms.common.Constant;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.DataSummaryService;
import com.sunjee.util.DateUtil;
import com.sunjee.util.ExcelUtil;
import com.sunjee.util.HqlNoEquals;

@Controller("dataSummaryAction")
@Scope("prototype")
public class DataSummaryAction extends BaseAction<DataSummary> {

	private static final long serialVersionUID = 2613796523828817096L;
	
	private DataSummaryService dataSummaryService;
	
	//数据汇总表名称
	private final static String SUMMARY_FILE_NAME = "数据汇总表" + Constant.EXCEL_SUFFIX;	
	//福位汇总表名称
	private final static String BS_SUMMARY_NAME = "福位汇总表" + Constant.EXCEL_SUFFIX;
	//牌位汇总表名称
	private final static String TABLET_SUMMARY_NAME = "牌位汇总表" + Constant.EXCEL_SUFFIX;
	//其它收费汇总表名称
	private final static String ITEM_SUMMARY_NAME = "其它费用汇总表" + Constant.EXCEL_SUFFIX;

	private Date startDate;
	private Date endDate;
	private String fileName;
	private List<DataSummary> dataSummaryList;

	public DataSummaryService getDataSummaryService() {
		return dataSummaryService;
	}

	@Resource(name = "dataSummaryService")
	public void setDataSummaryService(DataSummaryService dataSummaryService) {
		this.dataSummaryService = dataSummaryService;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<DataSummary> getDataSummaryList() {
		return dataSummaryList;
	}

	public void setDataSummaryList(List<DataSummary> dataSummaryList) {
		this.dataSummaryList = dataSummaryList;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		try {
			//将文件名称转换为Struts2的编码格式(struts2的编码格式为:"ISO8859-1")
			this.fileName = new String(fileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			this.fileName = UUID.randomUUID().toString();
		}
	}
	
	public String grid(){
		Map<String, Object> whereParams = getWhereParams();
		if(startDate != null && endDate != null){
			startDate = DateUtil.getStartTimeOfDay(startDate);
			endDate = DateUtil.getEndTimeOfDay(endDate);
			whereParams.put("createDate", new HqlNoEquals(startDate, endDate));
		}
		else if(startDate != null){
			startDate = DateUtil.getStartTimeOfDay(startDate);
			whereParams.put("createDate", new HqlNoEquals(startDate, HqlNoEquals.MORE_EQ));
		}
		else if(endDate != null){
			endDate = DateUtil.getEndTimeOfDay(endDate);
			whereParams.put("createDate", new HqlNoEquals(endDate, HqlNoEquals.LESS_EQ));
		}
		Map<String, SortType> sortParams = getSortParams(SortType.desc, "createDate");
		this.setDataGrid(this.dataSummaryService.getDataGrid(getPager(), whereParams, sortParams));
		return success();
	}
	
	/**
	 * 导出数据汇总表
	 * @return
	 * @throws Exception 
	 */
	public InputStream getSummaryFile() throws Exception{
		
		int rowIndex = 2;	//从第三行开始写数据(索引从0开始）
		
		List<DataSummary> list = getDataSummaryListByDate();
		ServletContext context = ServletActionContext.getServletContext();
		
		setFileName(SUMMARY_FILE_NAME);
		ExcelUtil excel = new ExcelUtil(Constant.DEFUALT_DATE_FORMAT);
		String fields[] = new String[]{"createDate","bsLeaseCount","bsLeaseTotalPrice","bsBuyCount","bsBuyTotalPrice","bsRemain",
				"tblBuyCount","tblTotalPrice","tblRemain","memberCount","memberTotalPrice","mngRecCount","mngTotalPrice","itemCount",
				"itemTotalPrice","total"};
		return excel.createExcel(context, Constant.SUMMARY_TEMPLATE_NAME, rowIndex, fields, list);
	}
	
	/**
	 * 导出福位汇总表
	 * @return
	 * @throws Exception 
	 */
	public InputStream getBsSummaryFile() throws Exception{
		
		int rowIndex = 1;	//从第二行开始写数据(索引从0开始）
		
		List<DataSummary> list = getDataSummaryListByDate();
		ServletContext context = ServletActionContext.getServletContext();
		
		setFileName(BS_SUMMARY_NAME);
		ExcelUtil excel = new ExcelUtil(Constant.DEFUALT_DATE_FORMAT);
		String fields[] = new String[]{"createDate","bsLeaseCount","bsLeaseTotalPrice","bsBuyCount","bsBuyTotalPrice","bsRemain"};
		return excel.createExcel(context, Constant.BLESSSEAT_SUMMARY_TEMPLATE_NAME, rowIndex, fields, list);
	}
	
	
	/**
	 * 导出牌位汇总表
	 * @return
	 * @throws Exception 
	 */
	public InputStream getTabletSummaryFile() throws Exception{
		
		int rowIndex = 1;	//从第三行开始写数据(索引从0开始）
		
		List<DataSummary> list = getDataSummaryListByDate();
		ServletContext context = ServletActionContext.getServletContext();
		
		setFileName(TABLET_SUMMARY_NAME);
		ExcelUtil excel = new ExcelUtil(Constant.DEFUALT_DATE_FORMAT);
		String fields[] = new String[]{"createDate","tblBuyCount","tblTotalPrice","tblRemain"};
		return excel.createExcel(context, Constant.TABLET_SUMMARY_TEMPLATE_NAME, rowIndex, fields, list);
	}
	
	/**
	 * 导出其它收费汇总表
	 * @return
	 * @throws Exception 
	 */
	public InputStream getItemSummaryFile() throws Exception{
		
		int rowIndex = 2;	//从第三行开始写数据(索引从0开始）
		
		List<DataSummary> list = getDataSummaryListByDate();
		ServletContext context = ServletActionContext.getServletContext();
		
		setFileName(ITEM_SUMMARY_NAME);
		ExcelUtil excel = new ExcelUtil(Constant.DEFUALT_DATE_FORMAT);
		String fields[] = new String[]{"createDate","memberCount","memberTotalPrice","mngRecCount","mngTotalPrice","itemCount","itemTotalPrice"};
		return excel.createExcel(context, Constant.ITEM_SUMMARY_TEMPLATE_NAME, rowIndex, fields, list);
	}
	
	/**
	 * 打印汇总数据
	 * @return
	 */
	public String previewSummary(){
		
		startDate = startDate == null ? new Date() : startDate;
		endDate = endDate == null ? new Date() : endDate;
		
		this.dataSummaryList = getDataSummaryListByDate();
		return SUCCESS;
	}
	
	/**
	 * 通过开始时间和结束时间获取汇总数据
	 * @return
	 */
	private List<DataSummary> getDataSummaryListByDate(){
		Map<String, Object> whereParams = getWhereParams();
		if(startDate != null && endDate != null){
			startDate = DateUtil.getStartTimeOfDay(startDate);
			endDate = DateUtil.getEndTimeOfDay(endDate);
			whereParams.put("createDate", new HqlNoEquals(startDate, endDate));
		}
		else if(startDate != null){
			startDate = DateUtil.getStartTimeOfDay(startDate);
			whereParams.put("createDate", new HqlNoEquals(startDate, HqlNoEquals.MORE_EQ));
		}
		else if(endDate != null){
			endDate = DateUtil.getEndTimeOfDay(endDate);
			whereParams.put("createDate", new HqlNoEquals(endDate, HqlNoEquals.LESS_EQ));
		}
		Map<String, SortType> sortParams = getSortParams(SortType.desc, "createDate");
		List<DataSummary> list = this.dataSummaryService.getAllByParams(null, whereParams, sortParams);
		return list;
	}
}
