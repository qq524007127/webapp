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
		app.addScript('tablet.js');
		app.addStyle('style.css');
	</script>
	
  </head>
  
  <body>
 	<table id="tabletGrid" data-options="toolbar:'#toolbarPanel'"></table>
 	<div id="tabletGridTB">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="doAddTablet()">添加</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="doEditTablet()">修改</a>
		<span style="margin-left:50px;">
			<input id="searchbox" />
			<div id="searchboxMenu">
				<div name="tabletName">牌位名称</div>
			</div>
		</span>
	</div>
	<div id="addWindow">
		<form id="addForm" action="${pageContext.request.contextPath }/api/tablet_add.action" method="post" style="text-align: center;">
			<table align="center" class="form-container">
				<tr>
					<td class="title"><label>牌位名称：</label></td>
					<td>
						<input name="tabletName" class="easyui-validatebox" data-options="required:true">
					</td>
					<td class="title"><label>牌位价格：</label></td>
					<td>
						<input name="tabletPrice" class="easyui-numberbox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="title"><label for="addTabletEditable">是否可编辑：</label></td>
					<td>
						<input id="addTabletEditable" type="checkbox" value=true name="editable">
					</td>
					<td class="title"><label for="addTabletPermit">有效：</label></td>
					<td>
						<input id="addTabletPermit" type="checkbox" value=true name="permit">
					</td>
				</tr>
				<tr>
					<td class="title"><label>备注：</label></td>
					<td colspan="3">
						<textarea rows="3" cols="45" name="tabletRemark"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="editWindow">
		<form id="editForm" action="${pageContext.request.contextPath }/api/tablet_edit.action" method="post" style="text-align: center;">
			<table align="center" class="form-container">
				<tr>
					<td class="title"><label>牌位名称：</label></td>
					<td>
						<input type="hidden" name="tabletId">
						<input name="tabletName" class="easyui-validatebox" data-options="required:true">
					</td>
					<td class="title"><label>牌位价格：</label></td>
					<td>
						<input name="tabletPrice" class="easyui-numberbox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="title"><label for="editTabletEditable">是否可编辑：</label></td>
					<td>
						<input id="editTabletEditable" type="checkbox" value=true name="editable">
					</td>
					<td class="title"><label for="editTabletPermit">有效：</label></td>
					<td>
						<input id="editTabletPermit" type="checkbox" value=true name="permit">
					</td>
				</tr>
				<tr>
					<td class="title"><label>备注：</label></td>
					<td colspan="3">
						<textarea rows="3" cols="45" name="tabletRemark"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
  </body>
</html>
