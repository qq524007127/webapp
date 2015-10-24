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
		app.addScript('user.js');
		//app.addStyle('style.css');
	</script>

  </head>
  
  <body>
   <table id="userGrid"></table>
   <div	id="toolbarPanel">
   		<a class="easyui-linkbutton" iconCls="icon-add" onclick="addUser()">添加</a>
   		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="editUser()">修改</a>
   		<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="resetPassword()">重置密码</a>
   		<span class="toolbar-searchbox-container">
   			<input id="searchBox" >
   		</span>
   </div>
   <div id="searchboxMenu">
   		<div name="userCode">登陆账号</div>
   		<div name="userName">用户姓名</div>
   </div>
   <div id="addWindow" style="text-align: center;">
    	<form id="addForm" action="${pageContext.request.contextPath }/api/user_add.action" method="post">
			<table align="center" class="form-container">
				<tr>
					<td class="title"><label>用户名称：</label></td>
					<td>
						<input id="addUserName" name="userName" class="easyui-validatebox" data-options="required:true">
					</td>
					<td class="title"><label>登陆账号：</label></td>
					<td>
						<input id="addUserCode" name="userCode" class="easyui-validatebox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="title"><label>是否有效：</label></td>
					<td>
						<input type="checkbox" id="addPermit" name="permit" value=true checked="true">
					</td>
					<td class="title"><label>手机号码：</label></td>
					<td>
						<input type="text" name="mobile" class="easyui-numberbox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="title"><label>电子邮箱：</label></td>
					<td>
						<input id="addUserCode" name="email" class="easyui-validatebox">
					</td>
					<td class="title"><label>密码：</label></td>
					<td>
						<input name="password" class="easyui-validatebox" data-options="required:true" type="password" value="123456">
					</td>
				</tr>
				<tr>
					<td class="title"><label>备注：</label></td>
					<td colspan="3">
						<textarea rows="3" cols="45" name="remark"></textarea>
					</td>
				</tr>
				<tr>
					<td class="title"><label>角色：</label></td>
					<td colspan="3">
						<s:iterator value="%{roleList }" var="role">
			    			<input type="checkbox" name="roleIds" value="${role.roleId }" id="add${role.roleId }">
			    			<label for="add${role.roleId }">${role.roleName }</label>
			    		</s:iterator>
					</td>
				</tr>
			</table>	    	
	    </form>
    </div>
    <div id="editWindow" style="text-align: center;">
    	<form id="editForm" action="${pageContext.request.contextPath }/api/user_edit.action" method="post">
	    	<input type="hidden" name="userId">
	    	<input type="hidden" name="password">
	    	<table align="center" class="form-container">
				<tr>
					<td class="title"><label>用户名称：</label></td>
					<td>
						<input id="addUserName" name="userName" class="easyui-validatebox" data-options="required:true">
					</td>
					<td class="title"><label>登陆账号：</label></td>
					<td>
						<input id="addUserCode" readonly="readonly" name="userCode" class="easyui-validatebox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="title"><label>是否有效</label></td>
					<td>
						<input type="checkbox" id="addPermit" name="permit" value=true checked="true">
					</td>
					<td class="title"><label>手机号码：</label></td>
					<td>
						<input type="text" name="mobile" class="easyui-numberbox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="title"><label>电子邮箱：</label></td>
					<td colspan="3">
						<input id="addUserCode" name="email" class="easyui-validatebox">
					</td>
				</tr>
				<tr>
					<td class="title"><label>备注：</label></td>
					<td colspan="3">
						<textarea rows="3" cols="45" name="remark"></textarea>
					</td>
				</tr>
				<tr>
					<td class="title"><label>角色：</label></td>
					<td colspan="3">
						<s:iterator value="%{roleList }" var="role">
			    			<input type="checkbox" name="roleIds" value="${role.roleId }" id="edit${role.roleId }">
			    			<label for="edit${role.roleId }">${role.roleName }</label>
			    		</s:iterator>
					</td>
				</tr>
			</table>	    	
	    </form>
    </div>
  </body>
</html>
