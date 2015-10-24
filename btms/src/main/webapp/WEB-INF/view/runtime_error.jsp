<%@page import="com.sunjee.btms.common.Message"%>
<%@page import="com.sunjee.btms.exception.AppRuntimeException"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:set id="error" value="exception"></s:set>
<%
	response.setContentType("text/html");
	AppRuntimeException runtimeErorr = (AppRuntimeException) pageContext.getAttribute("error");
	StringBuffer sb = new StringBuffer("{");
	sb.append("\"success\":" + false + ",");
	sb.append("\"msg\":\"出错了，"+runtimeErorr.getMessage()+"\"}");
	out.println(sb);
	out.flush();
%>
