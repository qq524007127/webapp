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
    
    <title>会员社会关系维护</title>
    
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
		app.addScript('relation.js');
	</script>
	
  </head>
  
  <body>
 	<table id="relationGrid" data-options="toolbar:'#toolbarPanel'"></table>
	
	<!-- 添加企业窗口 -->
  	<div id="addWindow" style="text-align: center;">
    	<form id="addForm" action="${pageContext.request.contextPath }/api/relation_add.action" method="post">
    		<input name="memberId" type="hidden" value="${memberId }">
    		<table align="center" class="form-container">
    			<tr>
    				<td class="title"><label>姓名:</label></td>
    				<td><input class="easyui-validatebox" name="relName" data-options="required:true"></td>
    				<td class="title"><label>与本人关系:</label></td>
    				<td><input class="easyui-validatebox" name="relation" data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>出生日期:</label></td>
    				<td><input name="relBirthday" class="easyui-datebox"  data-options="required:true,editable:false"></td>
    				<td class="title"><label>联系电话:</label></td>
    				<td><input class="easyui-validatebox" name="relTell" data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>性别:</label></td>
    				<td>
    					<select name="relSex" class="easyui-combobox" data-options="editable:false,width:100,required:true,panelHeight:100">
			    			<option value="男" selected="selected">男</option>
			    			<option value="女">女</option>
			    			<option value="其它">其它</option>
			    		</select>
    				</td>
    				<td class="title"><label>家庭地址:</label></td>
    				<td><input class="easyui-validatebox" name="relAddress" data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title">备注:</td>
    				<td colspan="3">
    					<textarea rows="5" cols="60" name="relRemark" ></textarea>
    				</td>
    			</tr>
    		</table>
	    </form>
    </div>
    
    <!-- 修改企业信息窗口 -->
    <div id="editWindow" style="text-align: center;">
    	<form id="editForm" action="${pageContext.request.contextPath }/api/relation_edit.action" method="post">
    		<input name="memberId" type="hidden">
    		<input type="hidden" name="relId" >
    		<table align="center" class="form-container">
    			<tr>
    				<td class="title"><label>姓名:</label></td>
    				<td><input class="easyui-validatebox" name="relName" data-options="required:true"></td>
    				<td class="title"><label>与本人关系:</label></td>
    				<td><input class="easyui-validatebox" name="relation" data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>出生日期:</label></td>
    				<td><input name="relBirthday" class="easyui-datebox"  data-options="required:true,editable:false"></td>
    				<td class="title"><label>联系电话:</label></td>
    				<td><input class="easyui-validatebox" name="relTell" data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>性别:</label></td>
    				<td>
    					<select name="relSex" class="easyui-combobox" data-options="editable:false,width:100,required:true,panelHeight:100">
			    			<option value="男" selected="selected">男</option>
			    			<option value="女">女</option>
			    			<option value="其它">其它</option>
			    		</select>
    				</td>
    				<td class="title"><label>家庭地址:</label></td>
    				<td><input class="easyui-validatebox" name="relAddress" data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title">备注:</td>
    				<td colspan="3">
    					<textarea rows="5" cols="60" name="relRemark" ></textarea>
    				</td>
    			</tr>
    		</table>
	    </form>
    </div>
	
  </body>
	<script type="text/javascript">
		relation.init('${memberId}');
	</script>
</html>
