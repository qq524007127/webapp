package com.sunjee.btms.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.PayRecord;
import com.sunjee.btms.service.PayRecordService;
import com.sunjee.util.PayInfoExcelUtil;

@Controller("payRecordInfoAction")
@Scope("prototype")
public class PayRecordInfoAction extends BaseAction<PayRecord> implements
		ModelDriven<PayRecord> {

	private static final long serialVersionUID = -2508101366318059275L;

	private PayRecordService payRecordService;

	private PayRecord payRecord;
	private String fileName;

	public PayRecordService getPayRecordService() {
		return payRecordService;
	}

	@Resource(name = "payRecordService")
	public void setPayRecordService(PayRecordService payRecordService) {
		this.payRecordService = payRecordService;
	}

	public PayRecord getPayRecord() {
		return payRecord;
	}

	public void setPayRecord(PayRecord payRecord) {
		this.payRecord = payRecord;
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
		this.payRecord = this.payRecordService.getFetchPayRecord(payRecord.getPayRecId());
		return SUCCESS;
	}
	
	public InputStream getPayRecordInfoFile() throws IOException{
		this.payRecord = this.payRecordService.getFetchPayRecord(payRecord.getPayRecId());
		ServletContext context = ServletActionContext.getServletContext();
		PayInfoExcelUtil util = new PayInfoExcelUtil(payRecord,context);
		HSSFWorkbook book = util.getPayinfoExcel();
		ByteArrayInputStream bais = null;
		ByteArrayOutputStream baos = null;
		baos = new ByteArrayOutputStream();
		book.write(baos);
		byte[] buf = baos.toByteArray();
		bais = new ByteArrayInputStream(buf);
		setFileName("缴费清单.xls");
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
	public PayRecord getModel() {
		if(this.payRecord == null){
			this.payRecord = new PayRecord();
		}
		return this.payRecord;
	}

}
