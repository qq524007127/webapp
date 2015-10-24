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
		app.addScript('role.js');
	</script>

  </head>
  
  <body>
    <table id="roleGrid"></table>
    <div id="addWindow" style="text-align: center;">
    	<form id="addForm" action="${pageContext.request.contextPath }/api/role_add.action" method="post">
    		<table align="center" class="form-container">
				<tr>
					<td class="title"><label>角色名称：</label></td>
					<td><input name="roleName" class="easyui-validatebox" data-options="required:true"></td>
				</tr>    
				<tr>
					<td class="title"><label>备注：</label></td>
					<td><textarea name="remark" id="editRemark" cols="30" rows="4"></textarea></td>
				</tr>   
				<tr>
					<td class="title"><label>权限：</label></td>
					<td>
						<s:iterator value="%{moduleList }" var="module" status="statu">
			    			<s:if test="(#statu.index % 3) == 0 && #statu.index != 0"><hr></s:if>
			    			<input type="checkbox" name="moduleIds" value="${module.moduleId }" id="add${module.moduleId }">
			    			<label for="add${module.moduleId }">${module.moduleName }</label>
			    		</s:iterator>
					</td>
				</tr>   		
    		</table>
	    </form>
    </div>
    <div id="editWindow" style="text-align: center;">
    	<form id="editForm" action="${pageContext.request.contextPath }/api/role_edit.action" method="post">
	    	<input type="hidden" name="roleId">
	    	<table align="center" class="form-container">
				<tr>
					<td class="title"><label>角色名称：</label></td>
					<td><input name="roleName" class="easyui-validatebox" data-options="required:true"></td>
				</tr>    
				<tr>
					<td class="title"><label>备注：</label></td>
					<td><textarea name="remark" id="editRemark" cols="30" rows="4"></textarea></td>
				</tr>   
				<tr>
					<td class="title"><label>权限：</label></td>
					<td>
						<s:iterator value="%{moduleList }" var="module" status="statu">
							<s:if test="(#statu.index % 3) == 0 && #statu.index != 0"><hr></s:if>
			    			<input type="checkbox" name="moduleIds" value="${module.moduleId }" id="edit${module.moduleId }">
			    			<label for="edit${module.moduleId }">${module.moduleName }</label>
			    		</s:iterator>
					</td>
				</tr>   		
    		</table>
	    </form>
    </div>
  </body>
</html>
