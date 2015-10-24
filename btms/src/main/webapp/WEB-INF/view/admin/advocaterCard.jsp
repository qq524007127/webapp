<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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

<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/app.js" charset="UTF-8"></script>
<script type="text/javascript">
	app.init('${pageContext.request.contextPath}');
	app.addScript('advocaterCard.js');
</script>

</head>

<body>
	<table id="advocaterCardGrid"></table>
	<%-- 工具栏 --%>
	<div id="toolbarPanel">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" id="addCardBtn">办理昄依证</a> 
		<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" id="editCardBtn">昄依证信息修改</a> 
		<span style="margin-left:20px;">
			<input id="mainSearchbox" >
			<div id="mainSearchboxMenu">
				<div name="cardCode">昄依证号</div>
				<div name="advName">持有人姓名</div>
			</div>
		</span>
	</div>

	<!-- 办理会员卡窗口 -->
	<div id="addWindow">
		<form id="addForm" method="post" action="${pageContext.request.contextPath }/api/advocaterCard_add.action">
			<table align="center" class="form-container">
				<tr>
					<td class="title"><label>姓名：</label></td>
					<td><input name="advName" class="easyui-validatebox" data-options="required:true,width:200"></td>
				</tr>
				<tr>
					<td class="title"><label>出生日期：</label></td>
					<td><input name="advBirthday" class="easyui-datebox"  data-options="required:true,editable:false"></td>
				</tr>
				<tr>
					<td class="title"><label>备注：</label></td>
					<td ><textarea name="remark" rows="3" cols="30"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="editWindow">
		<form id="editForm" method="post" action="${pageContext.request.contextPath }/api/advocaterCard_edit.action">
			<input name="cardId" type="hidden">
			<table align="center" class="form-container">
				<tr>
					<td class="title"><label>姓名：</label></td>
					<td><input name="advName" class="easyui-validatebox" data-options="required:true,width:200"></td>
				</tr>
				<tr>
					<td class="title"><label>出生日期：</label></td>
					<td><input name="advBirthday" class="easyui-datebox" data-options="required:true,editable:false"></td>
				</tr>
				<tr>
					<td class="title"><label>备注：</label></td>
					<td><textarea name="remark" rows="3" cols="30"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
