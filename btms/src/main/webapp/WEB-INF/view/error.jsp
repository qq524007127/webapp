<%@page import="com.sunjee.btms.common.Message"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:set id="error" value="exception"></s:set>
<%
	response.setContentType("text/html");
	StringBuffer sb = new StringBuffer("{");
	sb.append("\"success\":" + false + ",");
	sb.append("\"msg\":\"系统出错了，请联系管理员\"}");
	out.println(sb);
	out.flush();
%>
