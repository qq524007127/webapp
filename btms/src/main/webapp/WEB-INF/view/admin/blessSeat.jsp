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
		app.addScript('blessSeat.js');
		app.addStyle('style.css');
	</script>
	
  </head>
  
  <body>
 	<table id="blessSeatGrid" data-options="toolbar:'#toolbarPanel'"></table>
 	<div id="toolbarPanel">
 		<%-- <form id="searchForm" action="${pageContext.request.contextPath }/api/blessSeat_grid.action" method="post"> --%>
			<!-- <a class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a> -->
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="setBleassSeatLevel()">设置级别</a>
			<!-- <a class="easyui-linkbutton" data-options="iconCls:'icon-no'">禁用</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'">启用</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart'">导出</a> -->
			<span class="toolbar-searchbox-container">
				<%-- <label>区域：<select id="areaCombobox" class="easyui-combo" data-options="width:'100px'"></select></label> 
				<label>级别：<select id="levelCombobox" class="easyui-combo" data-options="width:'100px'"></select></label> 
				<label>级别状态：
					<select id="leveledCombobox" name="levedState" class="easyui-combobox" data-options="width:80,panelHeight:80,editable:false">
						<option value="0">=全部=</option>
						<option value="1">已设置</option>
						<option value="2">未设置</option>
					</select>
				</label>
				<label>是否捐赠： 
					<select id="saledCombobox" class="easyui-combobox" data-options="width:80,panelHeight:80,editable:false">
						<option value="">=全部=</option>
						<option value="1">已捐赠</option>
						<option value="0">未捐赠</option>
					</select>
				</label> 
				<label>是否使用：
					<select id="usedCombobox" class="easyui-combobox" data-options="width:80,panelHeight:80,editable:false">
						<option value="">=全部=</option>
						<option value="1">已使用</option>
						<option value="0">未使用</option>
					</select>
				</label> --%>
				<input id="searchBox" /> 
				<!-- <a id="clearSearchBtn" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
				<a id="clearSearchBtn" class="easyui-linkbutton" iconCls="icon-cancel" onclick="clearSearch()">清空查询条件</a> -->
			</span>
		<!-- </form> -->
	</div>
	<div id="searchBoxMenu">
		<div name="bsCode">福位编号</div>
		<div name="lev.levName">级别名称</div>
	</div>
	
	<div id="setLevelWindow">
		<form id="setLevelForm" action="${pageContext.request.contextPath }/api/blessSeat_updateBSLevel.action" method="post" style="text-align: center;">
			<input name="ids" id="ids" type="hidden">
			<table align="center" class="form-container">
				<tr>
					<td class="title"><label>选择级别：</label></td>
					<td>
						<select name="levelId"></select>
					</td>
				</tr>
			</table>
		</form>
	</div>
  </body>
</html>
