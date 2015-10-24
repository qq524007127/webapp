<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>海会塔管理系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta name="renderer" content="webkit">
	<meta content="always" name="referrer">     
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="${pageContext.request.contextPath }/img/favicon.ico">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/login.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/jquery.min.js" ></script>
	<script type="text/javascript">
		$(function(){
			$('#loginForm input[name=userCode]').focus();
			$('#loginForm input[name=userCode]').keypress(function(event){
				if(event.keyCode == 9){
					$('#loginForm input[name=password]').focus();
				}
				if(event.keyCode == 13){
					submitForm();
				}
			});
			$('#loginForm input[name=password]').keypress(function(event){
				if(event.keyCode == 13){
					submitForm();
				}
			});
			$('#loginForm input[id=reset]').click(function(){
				$('#error_msg').html('');
				$('#loginForm input[name=userCode]').val('');
				$('#loginForm input[name=password]').val('');
				$('#loginForm input[name=userCode]').focus();
			});
		});
		function submitForm(){
			var userCode = $('#loginForm input[name=userCode]').val();
			var password = $('#loginForm input[name=password]').val();
			if(!$.trim(userCode)){
				$('#error_msg').html('账号不能为空，请重新输入！');
				$('#loginForm input[name=userCode]').select();
				return false;
			}
			if(!$.trim(password)){
				$('#error_msg').html('密码不能为空，请重新输入！');
				$('#loginForm input[name=password]').select();
				return false;
			}
			$('#loginForm').submit();
		}	
	</script>
  </head>
  
  <body style="text-align: center;">
  	<div class="sys-title">三月三海会塔管理系统</div>
	<div class="login-container">
		<form id="loginForm" action="${pageContext.request.contextPath }/admin/userLogin.action" method="post">
			<div class="login-header">
				<div class="login-title">用户登陆</div>
			</div>
			<div class="login-context">
				<div class="form-item-group">
					<div class="form-item">
						<label>账&nbsp;号：</label>
						<span><input class="text-input" type="text" value="${user.userCode }" name="userCode" id="userCode" /></span>
					</div>
					<div class="form-item">
						<label>密&nbsp;码：</label>
						<span><input class="text-input" type="password" value="${user.password }" name="password" id="password" /></span>
					</div>
				</div>
				<div class="login-btn-group">
					<input type="button" id="reset" value="重&nbsp;置" class="login-btn warring"/>
					<input type="button" value="登&nbsp;陆" onclick="submitForm()"  class="login-btn"/>
				</div>
				<div id="error_msg">${msg }</div>
			</div>
		</form>
	</div>
  </body>
</html>
