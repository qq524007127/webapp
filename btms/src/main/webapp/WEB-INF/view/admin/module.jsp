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
    
    <title>模块管理</title>
    
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
		app.addScript('module.js');
		//app.addStyle('style.css');
	</script>
  </head>
  
  <body>
    <table id="moduleGrid"></table>
    <div id="addWindow">
    	<form id="addForm" action="${pageContext.request.contextPath }/api/module_add.action" method="post">
	    	<table align="center" class="form-container">
	    		<tr>
	    			<td class="title"><label>模块名称：</label></td>
	    			<td>
	    				<input type="text" name="moduleName" class="easyui-validatebox" data-options="required:true" style="width: 200px">
	    			</td>
				</tr>	  
	    		<tr>
	    			<td class="title"><label>页面地址：</label></td>
	    			<td>
	    				<input type="text" name="pageUrl" class="easyui-validatebox" style="width: 200px">
	    			</td>
				</tr>	  
	    		<tr>
	    			<td class="title"><label for="addRootModule">父模块：</label></td>
	    			<td>
	    				<select name="parentModule.moduleId" id="addRootModule">
			    		</select>
	    			</td>
				</tr>	  
	    		<tr>
	    			<td class="title"><label for="addRootModule">序号：</label></td>
	    			<td>
	    				<input type="text" name="moduleSort" class="easyui-numberbox" data-options="required:true,value:0" style="width: 200px">
	    			</td>
				</tr>		  
	    		<tr>
	    			<td class="title"><label for="addPermit">是否无效:</label></td>
	    			<td>
	    				<input type="checkbox" name="permit" id="addPermit" value=false />
	    			</td>
				</tr>	    	  	
	    	</table>
	    </form>
    </div>
    <div id="editWindow">
    	<form id="editForm" action="${pageContext.request.contextPath }/api/module_edit.action" method="post">
	    	<input type="hidden" name="moduleId">
	    	<table align="center" class="form-container">
	    		<tr>
	    			<td class="title"><label>模块名称：</label></td>
	    			<td>
	    				<input type="text" name="moduleName" class="easyui-validatebox" data-options="required:true" style="width: 200px">
	    			</td>
				</tr>	  
	    		<tr>
	    			<td class="title"><label>页面地址：</label></td>
	    			<td>
	    				<input type="text" name="pageUrl" class="easyui-validatebox" style="width: 200px">
	    			</td>
				</tr>	  
	    		<tr>
	    			<td class="title"><label for="editRootModule">父模块：</label></td>
	    			<td>
	    				<select name="parentModule.moduleId" id="editRootModule">
			    		</select>
	    			</td>
				</tr>	  
	    		<tr>
	    			<td class="title"><label for="addRootModule">序号：</label></td>
	    			<td>
	    				<input type="text" name="moduleSort" class="easyui-numberbox" data-options="required:true,value:0" style="width: 200px">
	    			</td>
				</tr>	  
	    		<tr>
	    			<td class="title"><label for="editPermit">是否无效:</label></td>
	    			<td>
	    				<input type="checkbox" name="permit" id="editPermit" value=false />
	    			</td>
				</tr>	    	  	
	    	</table>
	    </form>
    </div>
  </body>
</html>
