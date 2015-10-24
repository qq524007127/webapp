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
	app.addScript('memberCard.js');
</script>

</head>

<body>
	<table id="memberCardGrid" data-options="toolbar:'#toolbarPanel'"></table>
	<%-- 工具栏 --%>
	<div id="toolbarPanel">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" id="addMemberCardBtn">办理会员证</a> 
		<a class="easyui-linkbutton" data-options="iconCls:'icon-remove'" id="disableMemberCard">会员证挂失</a> 
		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="reAddMemberCardBtn">会员证补办</a> 
		<span style="margin-left:50px;">
			<input id="memberCardSearchbox" >
			<div id="memberCardSearchboxMenu">
				<div name="cardCode">会员证编号</div>
				<div name="mem.memberName">会员姓名</div>
				<div name="enterprise.enterName">企业名称</div>
			</div>
		</span>
	</div>

	<!-- 办理会员卡窗口 -->
	<div id="addMemberCardWindow">
		<form id="addFrom" action="${pageContext.request.contextPath }/api/memberCard_add.action" method="post">
			<table align="center" class="form-container">
				<input id="memberOrEnterId" type="hidden">
				<tr>
					<td class="title"><label>会员(企业)：</label></td>
					<td><input id="meberOrEnterInput" readonly="readonly"  class="easyui-validatebox" data-options="required:true,width:200"></td>
				</tr>
				<tr>
					<td class="title"><label>备注：</label></td>
					<td><textarea name="remark" rows="3" cols="30"></textarea></td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 选择会员窗口 -->
	<div id="selectkWindow">
		<div class="easyui-tabs" data-options="fit:true,border:false">
			<!-- 会员选择窗口 -->
			<div title="会员列表">
				<table id="memberGrid"></table>
				<div id="memberGridTB">
					<a id="checkMemberBtn" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">选择</a> 
					<input id="memberSearchbox">
					<div id="memberSearchboxMenu">
						<div name="memberName">姓名</div>
						<div name="memberIdentNum">身份证号</div>
						<div name="memberTell">联系电话</div>
					</div>
				</div>
			</div>
			<!-- 企业选择窗口 -->
			<div title="企业列表">
				<table id="enterpriseGrid"></table>
				<div id="enterpriseGridTB">
					<a id="checkEnterpriseBtn" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">选择</a> 
					<input id="enterpriseSearchbox">
					<div id="enterpriseSearchboxMenu">
						<div name="enterName">企业名称</div>
						<div name="busLisCode">营业执照代码</div>
						<div name="legalPersonName">法定代表人</div>
						<div name="enterTell">联系电话</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
