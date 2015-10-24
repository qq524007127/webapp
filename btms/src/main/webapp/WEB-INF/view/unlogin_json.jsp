<%@page import="com.sunjee.btms.common.Message"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	response.setContentType("text/html");
	StringBuffer sb = new StringBuffer("{");
	sb.append("\"success\":" + false + ",");
	sb.append("\"msg\":\"你还未登陆或登陆已过期，请登陆后重试\"}");
	out.println(sb);
	out.flush();
%>
