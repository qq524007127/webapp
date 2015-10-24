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

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.PreSellSummary;
import com.sunjee.btms.common.Constant;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.PreSellSummaryService;
import com.sunjee.util.DateUtil;
import com.sunjee.util.ExcelUtil;
import com.sunjee.util.HqlNoEquals;

@Controller("preSellSummaryAction")
@Scope("prototype")
public class PreSellSummaryAction extends BaseAction<PreSellSummary> implements
		ModelDriven<PreSellSummary> {

	private static final long serialVersionUID = -8740165121915349845L;
	
	//其它收费汇总表名称
	private final static String PRESELL_SUMMARY_NAME = "预售汇总表" + Constant.EXCEL_SUFFIX;

	private PreSellSummaryService preSellSummaryService;

	private PreSellSummary preSellSummary;
	private Date startDate;
	private Date endDate;
	private String fileName;

	public PreSellSummaryService getPreSellSummaryService() {
		return preSellSummaryService;
	}

	@Resource(name = "preSellSummaryService")
	public void setPreSellSummaryService(
			PreSellSummaryService preSellSummaryService) {
		this.preSellSummaryService = preSellSummaryService;
	}

	public PreSellSummary getPreSellSummary() {
		return preSellSummary;
	}

	public void setPreSellSummary(PreSellSummary preSellSummary) {
		this.preSellSummary = preSellSummary;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		if(startDate == null){
			return;
		}
		this.startDate = DateUtil.getStartTimeOfDay(startDate);
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		if(endDate == null){
			return;
		}
		this.endDate = DateUtil.getEndTimeOfDay(endDate);
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
	
	@Override
	public String execute() throws Exception {
		return success();
	}
	
	public String grid() {
		Map<String, Object> whereParams = getWhereParams();
		Map<String, SortType> sortParams = getSortParams(SortType.desc, "createDate");
		if(startDate != null && endDate != null){
			whereParams.put("createDate", new HqlNoEquals(startDate, endDate));
		}
		else if(startDate != null){
			whereParams.put("createDate", new HqlNoEquals(startDate,HqlNoEquals.MORE_EQ));
		}

		else if(endDate != null){
			whereParams.put("createDate", new HqlNoEquals(endDate,HqlNoEquals.LESS_EQ));
		}
		setDataGrid(this.preSellSummaryService.getDataGrid(getPager(), whereParams, sortParams));
		return success();
	}
	
	/**
	 * 导出数据汇总表
	 * @return
	 * @throws Exception 
	 */
	public InputStream getPreSellSummaryFile() throws Exception{
		
		int rowIndex = 2;	//从第三行开始写数据(索引从0开始）
		
		Map<String, Object> whereParams = getWhereParams();
		Map<String, SortType> sortParams = getSortParams(SortType.desc, "createDate");
		if(startDate != null && endDate != null){
			whereParams.put("createDate", new HqlNoEquals(startDate, endDate));
		}
		else if(startDate != null){
			whereParams.put("createDate", new HqlNoEquals(startDate,HqlNoEquals.MORE_EQ));
		}

		else if(endDate != null){
			whereParams.put("createDate", new HqlNoEquals(endDate,HqlNoEquals.LESS_EQ));
		}
		
		List<PreSellSummary> list = this.preSellSummaryService.getAllByParams(null, whereParams, sortParams);
		ServletContext context = ServletActionContext.getServletContext();
		
		setFileName(PRESELL_SUMMARY_NAME);
		ExcelUtil excel = new ExcelUtil(Constant.DEFUALT_DATE_FORMAT);
		String fields[] = new String[]{"createDate","psCount","psTotal","cashCount","shouldCharge","psCharge","realCharge","total"};
		return excel.createExcel(context, Constant.PRESELL_TEMPLATE_NAME, rowIndex, fields, list);
	}

	@Override
	public PreSellSummary getModel() {
		if (this.preSellSummary == null) {
			this.preSellSummary = new PreSellSummary();
		}
		return this.preSellSummary;
	}

}
