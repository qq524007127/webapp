<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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

<script type="text/javascript" src="${pageContext.request.contextPath }/js/app.js" charset="UTF-8"></script>
<script type="text/javascript">
	app.init('${pageContext.request.contextPath}');
	app.addScript('presell.js');
</script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/plugs/excelPrinter.js" charset="UTF-8" ></script>
<style type="text/css">
	#prsellList {
		margin: 0 auto;
		padding: 10px 5px;
		list-style: none;
		text-align: center;
	}
	
	#prsellList li {
		margin: 5px auto;
		padding: 5px 0;
	}
	
	#prsellList li input{
		width:240px;
	}
</style>
</head>

<body>
	<table id="presellGrid"></table>
	<div id="toolbarPanel">
		<a class="easyui-linkbutton" iconCls="icon-add" id="presellBtn">预定</a>
		<a class="easyui-linkbutton" iconCls="icon-ok" id="cashBtn">补单</a>
		<a class="easyui-linkbutton" iconCls="icon-print" id="printBtn">打印预售单</a>
		<a class="easyui-linkbutton" iconCls="icon-print" id="printCashBtn">打印补单</a>
		<a class="easyui-linkbutton" iconCls="icon-cancel" id="cancelBtn">取消预定</a>
		<span style="margin-left:50px;">
			<input id="presellGridSearchBox">
		</span>
	</div>
	<div id="searchboxMenu">
		<div name="orderCode">订单号</div>
	</div>
	<div id="presellWindow" class="easyui-dialog" width="400" height="200"
		title="会员捐赠" data-options="iconCls:'icon-add',closed:true,modal:true,buttons:[{
											text:'确认',
											iconCls:'icon-ok',
											handler:submitForm
										}]">
		<form id="addForm" method="post">
			<input name="memberId" id="memberId" type="hidden"
				value="${memberId }">
			<input name="enterpriseId" id="enterpriseId" type="hidden"
				value="${enterpriseId }">
			<table align="center" class="form-container">
				<tr>
					<td class="title"><label>数量：</label></td>
					<td><input name="psCount" type="text" class="easyui-numberbox" id="psCount" value="1" data-options="min:1,precision:0,onChange:sumTotalPrice"></td>
					<td class="title"><label>单价：</label></td>
					<td><input type="text" name="psPrice" class="easyui-numberbox" id="psPrice" 
						value="9000" data-options="min:1,onChange:sumTotalPrice"></td>
				</tr>
				<tr>
					<td class="title"><label>总价：</label></td>
					<td colspan="3"><input name="totalPrice" type="text" id="totalPrice"
						class="easyui-numberbox" value="9000" readonly="readonly"
						data-options="min:1,precision:0"></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="cashDialog">
		<form id="cashForm" method="post" action="${pageContext.request.contextPath }/api/presell_presellCash.action">
			<input type="hidden" name="psId">
			<ul id="prsellList">
			</ul>
		</form>
	</div>
	
	<div id="blessSeatGridWin" class="easyui-dialog" width="650" height="350"
		title="选择福位" iconCls="icoc-ok" modal=true closed=true>
		<table id="bsGrid"></table>
		<div id="bsgridToolbarPanel">
			<a class="easyui-linkbutton" id="checkBsBtn" data-options="iconCls:'icon-ok'">选择</a>
			<span style="margin-left:50px;">
				<input id="bsgridSearchbox"> 
			</span>
		</div>
		<div id="bsgridsearchboxMenu">
			<div name="searchKey">福位编号</div>
		</div>
	</div>
	
</body>
</html>
