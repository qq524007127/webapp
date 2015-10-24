<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>预售统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta name="renderer" content="webkit">
	<meta content="always" name="referrer">     
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/app.js" charset="UTF-8" ></script>
	<script type="text/javascript">
		app.init('${pageContext.request.contextPath}');
		app.addScript('preSellSummary.js');
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/plugs/excelPrinter.js" charset="UTF-8" ></script>
  </head>

<body>
	<table id="preSellSummaryGrid"></table>
	<div id="girdToolbarPanel">
		<a class="easyui-linkbutton" iconCls="icon-ok" id="downloadBtn" >导出</a>
		<a class="easyui-linkbutton" iconCls="icon-print" id="printBtn">打印</a>
		<span style="margin-left:50px;">
			开始时间：<input name="startDate" id="startDate" type="text" class="easyui-datebox" editable=false >
			&nbsp;&nbsp;结束时间：<input name="endDate" id="endDate" type="text" class="easyui-datebox" editable=false>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>
		</span>
	</div>
</body>
</html>
