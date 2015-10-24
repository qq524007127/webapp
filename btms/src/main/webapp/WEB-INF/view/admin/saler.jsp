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
		app.addScript('saler.js');
	</script>

  </head>
  
  <body>
   <table id="salerGrid"></table>
   <!-- 添加销售员窗口 -->
   <div id="addWindow" style="text-align: center;">
    	<form id="addForm" action="${pageContext.request.contextPath }/api/saler_add.action" method="post">
	    	<table align="center" class="form-container">
	    		<tr>
	    			<td class="title"><label>销售员姓名：</label></td>
	    			<td>
	    				<input name="salerName" class="easyui-validatebox" data-options="required:true">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="title"><label>联系电话：</label></td>			
	    			<td>
	    				<input name="salerPhone" class="easyui-numberbox">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="title"><label>启用：</label></td>
	    			<td>
	    				<input type="checkbox" id="addPermit" name="permit" checked>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
    </div>
    
    <div id="editWindow" style="text-align: center;">
    	<form id="editForm" action="${pageContext.request.contextPath }/api/saler_edit.action" method="post">
	    	<input name="salerId" type="hidden">
	    	<table align="center" class="form-container">
	    		<tr>
	    			<td class="title"><label>销售员姓名：</label></td>
	    			<td>
	    				<input name="salerName" class="easyui-validatebox" data-options="required:true">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="title"><label>联系电话：</label></td>			
	    			<td>
	    				<input name="salerPhone" class="easyui-numberbox">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="title"><label>启用：</label></td>
	    			<td>
	    				<input type="checkbox" id="editPermit" value="true" name="permit">
	    			</td>
	    		</tr>
	    	</table>
	    </form>
    </div>
  </body>
</html>
