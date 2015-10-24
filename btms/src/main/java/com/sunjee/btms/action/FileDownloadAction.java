package com.sunjee.btms.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller("fileDownLoadAction")
@Scope("prototype")
public class FileDownloadAction extends ActionSupport {

	private static final long serialVersionUID = 8689413848272482183L;

	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		try {
			//this.fileName = new String(fileName.getBytes("ISO8859-1"), "UTF-8");
			this.fileName = new String(fileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			this.fileName = UUID.randomUUID().toString();
		}
	}
	
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public InputStream getSummaryFile(){
		setFileName("中文.xls");
		ServletContext servletContext = ServletActionContext.getServletContext();
		try{
			return servletContext.getResourceAsStream("/resource/template/summarytemplate.xls");
		}catch(Exception e){
			return null;
		}
	}
}
