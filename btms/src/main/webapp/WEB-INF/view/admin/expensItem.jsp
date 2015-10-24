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
    
    <title>其它收费项目管理</title>
    
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
		app.addScript('expensItem.js');
	</script>
	
  </head>
  
  <body>
 	<table id="itemGrid" data-options="toolbar:'#toolbarPanel'"></table>
 	<div id="toolbarPanel">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="doAdd()">添加</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="doEdit()">修改</a>
		<span class="toolbar-searchbox-container">
			<input id="searchbox" />
			<div id="searchboxMenu">
				<div name="itemName">项目名称</div>
			</div>
		</span>
	</div>
	<div id="addWindow">
		<form id="addForm" action="${pageContext.request.contextPath }/api/expensItem_add.action" method="post" style="text-align: center;">
			<table align="center" class="form-container">
				<tr>
					<td class="title"><label>项目名称：</label></td>
					<td>
						<input name="itemName" class="easyui-validatebox" data-options="required:true">
					</td>
					<td class="title"><label>项目价格：</label></td>
					<td>
						<input name="itemPrice" class="easyui-numberbox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="title"><label for="addEditAble">年限是否可编辑：</label></td>
					<td>
						<input id="addEditAble" type="checkbox" value=true name="editAble">
					</td>
					<td class="title"><label for="addPremit">有效：</label></td>
					<td>
						<input id="addPremit" type="checkbox" value=true name="permit">
					</td>
				</tr>
				<tr>
					<td class="title"><label>费用类型：</label></td>
					<td colspan="3">
						<select name="costType"></select>
					</td>
				</tr>
				<tr>
					<td class="title"><label>备注：</label></td>
					<td colspan="3">
						<textarea rows="3" cols="45" name="itemRemark"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="editWindow">
		<form id="editForm" action="${pageContext.request.contextPath }/api/expensItem_edit.action" method="post" style="text-align: center;">
			<input type="hidden" name="itemId">
			<table align="center" class="form-container">
				<tr>
					<td class="title"><label>项目名称：</label></td>
					<td>
						<input name="itemName" class="easyui-validatebox" data-options="required:true">
					</td>
					<td class="title"><label>项目价格：</label></td>
					<td>
						<input name="itemPrice" class="easyui-numberbox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="title"><label for="editEditAble">年限是否可编辑：</label></td>
					<td>
						<input id="editEditAble" type="checkbox" value=true name="editAble">
					</td>
					<td class="title"><label for="editPremit">有效：</label></td>
					<td>
						<input id="editPremit" type="checkbox" value=true name="permit">
					</td>
				</tr>
				<tr>
					<td class="title"><label>费用类型：</label></td>
					<td colspan="3">
						<select name="costType"></select>
					</td>
				</tr>
				<tr>
					<td class="title"><label>备注：</label></td>
					<td colspan="3">
						<textarea rows="3" cols="45" name="itemRemark"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
  </body>
</html>
