package com.sunjee.btms.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.PreSell;
import com.sunjee.btms.common.Constant;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.PreSellService;
import com.sunjee.util.PreSellExcelUtil;

@Controller("presellAction")
@Scope("prototype")
public class PreSellAction extends BaseAction<PreSell> implements
		ModelDriven<PreSell> {

	private static final long serialVersionUID = 7261796923756396859L;
	
	/**
	 * 预售单文件名
	 */
	private static final String PRESELL_NAME = "预售缴费清单" + Constant.EXCEL_SUFFIX;
	
	/**
	 * 预售补单文件名
	 */
	private static final String PRESELL_CASH_NAME = "预售补单缴费清单" + Constant.EXCEL_SUFFIX;

	private PreSellService preSellService;

	private PreSell preSell;
	private String memberId;
	private String enterpriseId;
	private String psIds;
	private String bsIds[];
	private String fileName;

	public PreSell getPreSell() {
		return preSell;
	}

	public PreSellService getPreSellService() {
		return preSellService;
	}

	@Resource(name = "preSellService")
	public void setPreSellService(PreSellService preSellService) {
		this.preSellService = preSellService;
	}

	public void setPreSell(PreSell preSell) {
		this.preSell = preSell;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getPsIds() {
		return psIds;
	}

	public void setPsIds(String psIds) {
		this.psIds = psIds;
	}

	public String[] getBsIds() {
		return bsIds;
	}

	public void setBsIds(String[] bsIds) {
		this.bsIds = bsIds;
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

	public String addByMember() throws Exception {
		this.preSellService.addByMember(preSell, memberId, getCurrentUser());
		return success(preSell.getpRecord());
	}

	public String addByEnterprise() throws Exception {
		preSell = this.preSellService.addByEnterprise(preSell, enterpriseId,
				getCurrentUser());
		return success(preSell.getpRecord());
	}

	public String grid() throws Exception {
		Map<String, Object> whereParams = getWhereParams();
		if(!whereParams.containsKey("permit")){
			whereParams.put("permit", true);
		}
		if(!StringUtils.isEmpty(memberId)){
			whereParams.put("pRecord.mem.memberId", memberId);
		}
		else if(!StringUtils.isEmpty(enterpriseId)){
			whereParams.put("pRecord.enterprise.enterId", enterpriseId);
		}
		Map<String, SortType> sortParams = getSortParams("cash");
		setDataGrid(this.preSellService.getDataGrid(getPager(), whereParams,sortParams));
		return success();
	}

	/**
	 * 将预定批量设置为无效
	 * @return
	 */
	public String disable(){
		if(!StringUtils.isEmpty(psIds)){
			String ids[] = psIds.split(",");
			this.preSellService.updatePermit(ids, false);
		}
		return success();
	}
	
	/**
	 * 取消预定
	 * @return
	 */
	public String cancel(){
		if(!StringUtils.isEmpty(psIds)){
			String ids[] = psIds.split(",");
			this.preSellService.deleteByIds(ids);
		}
		return success();
	}
	
	public String presellCash(){
		if(bsIds != null){
			this.preSellService.executeCash(preSell.getPsId(),bsIds,getCurrentUser());
		}
		return success();
	}
	
	/**
	 * 获取预售收费清单
	 * @return
	 * @throws IOException
	 */
	public InputStream getPreSellFile() throws IOException{
		preSell = this.preSellService.getById(preSell.getPsId());
		ServletContext context = ServletActionContext.getServletContext();
		PreSellExcelUtil util = new PreSellExcelUtil(preSell,context);
		HSSFWorkbook book = util.getPreSellExcel();
		ByteArrayInputStream bais = null;
		ByteArrayOutputStream baos = null;
		baos = new ByteArrayOutputStream();
		book.write(baos);
		byte[] buf = baos.toByteArray();
		bais = new ByteArrayInputStream(buf);
		setFileName(PRESELL_NAME);
		try {
			book.write(baos);
			return bais;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(baos != null) baos.close();
			} catch (Exception e2) {
			}
			try {
				if(bais != null) bais.close();
			} catch (Exception e2) {
			}
			try {
				if(book != null) book.close();
			} catch (Exception e2) {
			}
		}
		return null;
	}
	
	/**
	 * 获取预售补单收费清单
	 * @return
	 * @throws IOException
	 */
	public InputStream getPreSellCashFile() throws IOException{
		preSell = this.preSellService.getById(preSell.getPsId());
		ServletContext context = ServletActionContext.getServletContext();
		PreSellExcelUtil util = new PreSellExcelUtil(preSell,context);
		HSSFWorkbook book = util.getPreSellCashExcel();
		ByteArrayInputStream bais = null;
		ByteArrayOutputStream baos = null;
		baos = new ByteArrayOutputStream();
		book.write(baos);
		byte[] buf = baos.toByteArray();
		bais = new ByteArrayInputStream(buf);
		setFileName(PRESELL_CASH_NAME);
		try {
			book.write(baos);
			return bais;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(baos != null) baos.close();
			} catch (Exception e2) {
			}
			try {
				if(bais != null) bais.close();
			} catch (Exception e2) {
			}
			try {
				if(book != null) book.close();
			} catch (Exception e2) {
			}
		}
		return null;
	}
	
	@Override
	public PreSell getModel() {
		if (this.preSell == null) {
			this.preSell = new PreSell();
		}
		return this.preSell;
	}
}
