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
    
    <title>个人信息维护</title>
    
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
		app.addScript('userInfo.js');
	</script>
	<style type="text/css">
		.main-container {
			padding: 10px;
			margin: 5px auto;
			text-align: center;
		}
		.change-psw-container {
			width: 420px;
			height: 220px;
			margin: 5 auto;
			margin-top: 20px;
		}
		.change-info-container {
			width: 550px;
			height: 250px;
			margin: 5 auto;
		}
	</style>
  </head>
  
	<body>
	  	<div class="easyui-panel" title="个人信息维护" fit=true style="padding: 5px;">
	  		<%-- <div class="main-container">
	  			<div class="change-info-container">
	  				<div title="基本信息维护" class="easyui-panel" iconCls="icon-edit">
	  					<form id="editForm" action="${pageContext.request.contextPath }/api/user_edit.action" method="post">
					    	<input type="hidden" name="userId">
					    	<input type="hidden" name="password">
					    	<table align="center" class="form-container">
								<tr>
									<td class="title"><label>用户名称：</label></td>
									<td>
										<input id="addUserName" name="userName" class="easyui-validatebox" data-options="required:true">
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
				    				<td colspan="4" style="text-align: right;background-color: #dddddd; padding: 1px 10px">
				    					<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="submitInfoForm">修改</a>
				    				</td>
				    			</tr>
							</table>	    	
					    </form>
	  				</div>
	  			</div>
	  		</div> --%>
	  		<div class="main-container">
	  			<div class="change-psw-container">
		  			<div class="easyui-panel" fit=false title="密码修改" iconCls="icon-edit" style="height: 260px;">
				    	<form id="changePSWForm" action="${pageContext.request.contextPath }/api/userInfo_editPassword.action" method="post">
				    		<table align="center" class="form-container">
				    			<tr>
				    				<td class="title"><label for="addUserName">旧密码：</label></td>
				    				<td>
				    					<input name="userId" type="hidden" value="${user.userId }">
				    					<input name="password" class="easyui-validatebox" data-options="required:true" type="password">
				    				</td>
				    			</tr>
				    			<tr>
				    				<td class="title"><label for="addUserName">新密码：</label></td>
				    				<td>
				    					<input name="newPassword" id="newPassword" class="easyui-validatebox" data-options="required:true" type="password">
				    				</td>
				    			</tr>
				    			<tr>
				    				<td class="title"><label for="addUserName">重复新密码：</label></td>
				    				<td>
				    					<input  id="rePassword" type="password" >
				    				</td>
				    			</tr>
				    			<tr>
				    				<td colspan="2" style="text-align: right;background-color: #dddddd; padding: 1px 15px">
				    					<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="submitForm">修改</a>
				    				</td>
				    			</tr>
				    		</table>
					    </form>
					</div>
	  			</div>
	  		</div>
	  	</div>
	</body>
</html>
