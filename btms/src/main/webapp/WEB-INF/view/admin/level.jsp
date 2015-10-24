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
		app.addScript('level.js');
	</script>

  </head>
  
  <body>
   <table id="levelGrid"></table>
   <div id="levelGridToolbar">
   	<a></a>
   </div>
   <div id="addWindow" style="text-align: center;">
    	<form id="addForm" action="${pageContext.request.contextPath }/api/level_add.action" method="post">
	    	<table align="center" class="form-container">
	    		<tr>
	    			<td class="title"><label>级别名称：</label></td>
	    			<td>
	    				<input id="addLevName" name="levName" class="easyui-validatebox" data-options="required:true">
	    			</td>
	    			<td class="title"><label>价格：</label></td>
	    			<td>
	    				<input name="levPrice" id="addLevPice" type="text" class="easyui-numberbox" data-options="min:1,precision:2,required:true">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="title"><label>管理费：</label></td>
	    			<td colspan="3">
	    				<input name="mngPrice" id="addMngPrice" type="text" class="easyui-numberbox" data-options="min:1,precision:2,required:true">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="title"><label>备注：</label></td>
	    			<td colspan="3">
	    				<textarea id="addRemark" name="remark" rows="3" cols="45"></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
    </div>
    <div id="editWindow" style="text-align: center;">
    	<form id="editForm" action="${pageContext.request.contextPath }/api/level_edit.action" method="post">
    		<input name="levId" type="hidden">
	    	<table align="center" class="form-container">
	    		<tr>
	    			<td class="title"><label>级别名称：</label></td>
	    			<td>
	    				<input id="addLevName" name="levName" class="easyui-validatebox" data-options="required:true">
	    			</td>
	    			<td class="title"><label>价格：</label></td>
	    			<td>
	    				<input name="levPrice" id="addLevPice" type="text" class="easyui-numberbox" data-options="min:1,precision:2,required:true">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="title"><label>管理费：</label></td>
	    			<td colspan="3">
	    				<input name="mngPrice" id="addMngPrice" type="text" class="easyui-numberbox" data-options="min:1,precision:2,required:true">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="title"><label>备注：</label></td>
	    			<td colspan="3">
	    				<textarea id="addRemark" name="remark" rows="3" cols="45"></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
    </div>
  </body>
</html>
