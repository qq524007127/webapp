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
    
    <title>角色管理</title>
    
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
		app.addScript('initApp.js');
		//app.addStyle('style.css');
	</script>

  </head>
  
  <body>
    <div id="initShelfPanel" class="easyui-panel" title="添加福位架" style="padding:5px;">
    	<form id="initShelfForm" action="${pageContext.request.contextPath }/api/shelf_add.action" method="post">
    		<p>
    			<label>所在区域名称：<input name="shelfArea" class="easyui-validatebox" data-options="required:true,length:1"></label>
    			<label>位于区域行：<input name="postionRow" type="text" class="easyui-numberbox" data-options="min:1,max:99,precision:0,required:true"></label>
    			<label>位于区域列：<input name="postionColumn" type="text" class="easyui-numberbox" data-options="min:1,max:99,precision:0,required:true"></label>
    			<label>总行数：<input name="shelfRow" type="text" class="easyui-numberbox" data-options="min:1,max:99,precision:0,required:true"></label>
    			<label>总列数：<input name="shelfColumn" type="text" class="easyui-numberbox" data-options="min:1,max:99,precision:0,required:true"></label>
    			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitInitShelf()">初始化</a>
    		</p>
    	</form>
    </div>
    <div style="height: 5px;"></div>
    <div id="initShelfByAreaPanel" class="easyui-panel" title="按区域初始化福位架" style="padding:5px;">
    	<form id="initShelfByAreaForm" action="${pageContext.request.contextPath }/api/area_init.action" method="post">
    		<p>
    			<label>区域名称：<input name="areaName" class="easyui-validatebox" data-options="required:true,length:1"></label>
    			<label>区域行数：<input name="areaRow" type="text" class="easyui-numberbox" data-options="min:1,max:1000,precision:0,required:true"></label>
    			<label>区域列数：<input name="areaColumn" type="text" class="easyui-numberbox" data-options="min:1,max:1000,precision:0,required:true"></label>
    			<label>福位架行数：<input name="shelfRow" type="text" class="easyui-numberbox" data-options="min:1,max:1000,precision:0,required:true"></label>
    			<label>福位架列数：<input name="shelfColumn" type="text" class="easyui-numberbox" data-options="min:1,max:1000,precision:0,required:true"></label>
    			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="initShelfByArea()">初始化</a>
    		</p>
    	</form>
    </div>
  </body>
</html>
