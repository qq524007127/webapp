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
    
    <title>福位级别管理</title>
    
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
		app.addScript('memberPayRecord.js');
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/plugs/excelPrinter.js" charset="UTF-8" ></script>
  </head>
  
  <body>
   <table id="payRecordGrid"></table>
   <!-- <a target="_blank" href="http://www.baidu.com">百度</a> -->
  </body>
  <script type="text/javascript">
  	memberPay.init('${memberId}');
  </script>
</html>
