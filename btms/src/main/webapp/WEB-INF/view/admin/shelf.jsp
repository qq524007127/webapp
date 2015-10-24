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
		//app.addScript('shelf.js');
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/shelf.js"></script>
  </head>
  
  <body>
   <table id="shelfGrid"></table>
   <div id="addWindow" style="text-align: center;">
    	<form id="addForm" action="${pageContext.request.contextPath }/api/user_add.action" method="post">
	    	<p>
	    		<label for="addUserName">用户名称：</label>
	    		<input id="addUserName" name="userName" class="easyui-validatebox" data-options="required:true">
	    	</p>
	    	<p>
	    		<label for="addUserCode">登陆账号：</label>
	    		<input id="addUserCode" name="userCode" class="easyui-validatebox" data-options="required:true">
	    	</p>
	    	<p>
	    		<input type="checkbox" id="addPermit" name="permit" value=true checked="true">
	    		<label for="addPermit">有效</label>
	    	</p>
	    	<p>
	    		<s:iterator value="%{roleList }" var="role">
	    			<input type="checkbox" name="roleIds" value="${role.roleId }" id="${role.roleId }">
	    			<label for="${role.roleId }">${role.roleName }</label>
	    		</s:iterator>
	    	</p>
	    </form>
    </div> 
    <div id="editWindow" style="text-align: center;">
    	<form id="editForm" action="${pageContext.request.contextPath }/api/shelf_edit.action" method="post">
	    	<input type="hidden" name="shelfId">
	    	<p>
	    		<span>
	    			<label for="editUserName">编号：</label>
	    			<input id="editUserName" disabled="false" name="shelfCode" class="easyui-validatebox"  data-options="required:true">
	    		</span>
	    		<span>
		    		<label for="editShelfArea">所在区域：</label>
		    		<input id="editShelfArea" name="shelfArea.areaId" data-options="required:true">
	    		</span>
	    	</p>
	    	<p>
	    		<span>
		    		<label for="editUserCode">所在行数：</label>
		    		<input id="editUserCode" name="postionRow" class="easyui-validatebox" data-options="required:true">
	    		</span>
	    		<span>
	    			<label for="editUserCode">所在列数：</label>
	    			<input id="editUserCode" name="postionColumn" class="easyui-validatebox" data-options="required:true">
	    		</span>
	    	</p>
	    	<p>
	    		<span>
		    		<label for="editUserCode">总行数：</label>
		    		<input id="editUserCode" name="shelfRow" class="easyui-validatebox" data-options="required:true">
	    		</span>
	    		<span>
	    			<label for="editUserCode">总列数：</label>
	    			<input id="editUserCode" name="shelfColumn" class="easyui-validatebox" data-options="required:true">
	    		</span>
	    	</p>
	    	<p>
	    		<label for="editUserCode">备注：</label>
	    		<textarea rows="3" cols="50" name="remark"></textarea>
	    	</p>
	    </form>
    </div>
  </body>
</html>
