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
    
    <title>福位管理</title>
    
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
		app.addScript('enterprise.js');
		//app.addStyle('style.css');
	</script>
	
  </head>
  
  <body>
 	<table id="enterpriseGrid" data-options="toolbar:'#toolbarPanel'"></table>
 	<div id="toolbarPanel">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="showAddWindow()">添加企业</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="showEditWindow()">信息修改</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="shwoPayWindow()">捐赠</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="showPreSellWindow()">福位预售</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="exit()">企业退会</a>
		<span style="margin-left:50px;">
			<input id="enterpriseSearchBox">
		</span>
	</div>
	<div id="searchboxMenu">
		<div name="enterName">企业名称</div>
    	<div name="busLisCode">营业执照编码</div>
    	<div name="legalPersonName">法定代表人</div>
	</div>
	
	<!-- 添加企业窗口 -->
  	<div id="addWindow" style="text-align: center;">
    	<form id="addForm" action="${pageContext.request.contextPath }/api/enterprise_add.action" method="post">
    		<table align="center" class="form-container">
    			<tr>
    				<td class="title">企业名称:</td>
    				<td><input class="easyui-validatebox" name="enterName" data-options="required:true"></td>
    				<td class="title">营业执照代码:</td>
    				<td><input class="easyui-validatebox" name="busLisCode" data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title">法定代表人:</td>
    				<td><input class="easyui-validatebox" name="legalPersonName" data-options="required:true"></td>
    				<td class="title">联系电话:</td>
    				<td><input class="easyui-validatebox" name="enterTell" data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title">备用联系人:</td>
    				<td><input class="easyui-validatebox" name="spareName" data-options="required:true"></td>
    				<td class="title">备用联系电话:</td>
    				<td><input class="easyui-validatebox" name="spareTell" data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title">企业地址:</td>
    				<td><input class="easyui-validatebox" name="enterAddress" data-options="required:true"></td>
    				<td class="title">
    					<label for="addEnterPermit">有效:</label>
    				</td>
    				<td><input id="addEnterPermit" type="checkbox" name="enterPermit" value=true checked="checked"></td>
    			</tr>
    			<tr>
    				<td class="title">经办人:</td>
    				<td colspan="3">
    					<input name="saler.salerId" id="addSaler">
    				</td>
    			</tr>
    			<tr>
    				<td class="title">备注:</td>
    				<td colspan="3">
    					<textarea rows="3" cols="60" name="enterRemark" ></textarea>
    				</td>
    			</tr>
    		</table>
	    </form>
    </div>
    
    <!-- 修改企业信息窗口 -->
    <div id="editWindow" style="text-align: center;">
    	<form id="editForm" action="${pageContext.request.contextPath }/api/enterprise_edit.action" method="post">
    		<input type="hidden" name="enterId" >
    		<table align="center" class="form-container">
    			<tr>
    				<td class="title">企业名称:</td>
    				<td><input class="easyui-validatebox" name="enterName" data-options="required:true"></td>
    				<td class="title">营业执照代码:</td>
    				<td><input class="easyui-validatebox" name="busLisCode" data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title">法定代表人:</td>
    				<td><input class="easyui-validatebox" name="legalPersonName" data-options="required:true"></td>
    				<td class="title">联系电话:</td>
    				<td><input class="easyui-validatebox" name="enterTell" data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title">备用联系人:</td>
    				<td><input class="easyui-validatebox" name="spareName" data-options="required:true"></td>
    				<td class="title">备用联系电话:</td>
    				<td><input class="easyui-validatebox" name="spareTell" data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title">企业地址:</td>
    				<td><input class="easyui-validatebox" name="enterAddress" data-options="required:true"></td>
    				<td class="title">
    					<label for="editEnterPermit">有效:</label>
    				</td>
    				<td><input id="editEnterPermit" type="checkbox" name="enterPermit" value=true checked="checked"></td>
    			</tr>
    			<tr>
    				<td class="title">经办人:</td>
    				<td colspan="3">
    					<input name="saler.salerId" id="editSaler">
    				</td>
    			</tr>
    			<tr>
    				<td class="title">备注:</td>
    				<td colspan="3">
    					<textarea rows="3" cols="60" name="enterRemark" ></textarea>
    				</td>
    			</tr>
    		</table>
	    </form>
    </div>
    
	<!-- 企业捐赠窗口 -->
    <div id="enterPayWindow"></div>
    
     <!-- 福位预售窗口 -->
    <div id="preSellWindow"></div>
    
     <!-- 企业已捐赠福位、牌位窗口 -->
    <div id="buyedListWindow"></div>
    
    <!-- 企业缴费记录窗口 -->
    <div id="payedListWindow"></div>
    
  </body>
</html>
