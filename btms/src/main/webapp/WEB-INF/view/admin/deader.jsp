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
		app.addScript('deader.js');
	</script>
	
  </head>
  
  <body>
 	<table id="deaderGrid" data-options="toolbar:'#toolbarPanel'"></table>
 	<%-- 工具栏 --%>
 	<div id="toolbarPanel">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="showAddWindow()">添加使用者</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="showEidtWindow()">使用者信息修改</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeDeader()">删除使用者</a>
		<span style="margin-left:50px;">
			<input id="deaderGridSearchbox" class="easyui-searchbox" data-options="prompt:'输入关键字搜索',menu:'#searchboxMenu'" style="width:300px"></input>
			<div id="searchboxMenu"> 
				<div name="deadName">使用者姓名</div>
		    	<div name="identNum">身份证号</div>
			</div>
		</span>
	</div>
	
	<!-- 添加使用者窗口 -->
  	<div id="addWindow" style="text-align: center;">
    	<form id="addForm" action="${pageContext.request.contextPath }/api/deader_add.action" method="post">
    		<table align="center" class="form-container">
    			<tr>
    				<td class="title"><label>姓名：</label></td>
    				<td><input name="deadName" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>名族：</label></td>
    				<td><input name="deadNational" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>籍贯：</label></td>
    				<td><input name="deadNatPlace" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>家庭地址：</label></td>
    				<td><input name="deadAdress" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>出身日期：</label></td>
    				<td><input name="deadBirthday" class="easyui-datebox"  data-options="required:true,editable:false"></td>
    				<td class="title"><label>身份证号：</label></td>
    				<td><input name="identNum" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>往生时间：</label></td>
    				<td><input name="deadedDate" class="easyui-datebox"  data-options="required:true,editable:false"></td>
    				<td class="title"><label>入住时间：</label></td>
    				<td><input name="inputDate" class="easyui-datebox"  data-options="required:true,editable:false"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>接洽居士姓名：</label></td>
    				<td><input name="contactName" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>接洽居士电话：</label></td>
    				<td><input name="contactTell" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>联系人地址：</label></td>
    				<td><input name="contactAdress" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>使用者性别：</label></td>
    				<td>
    					<select name="desSex" class="easyui-combobox" data-options="editable:false,width:100,required:true,panelHeight:100">
			    			<option value="男" selected="selected">男</option>
			    			<option value="女">女</option>
			    			<option value="其它">其它</option>
			    		</select>
    				</td>
    			</tr>
    			<tr>
    				<td class="title"><label>对应福位：</label></td>
    				<td>
    					<input name="blessSeatId">
    				</td>
    				<td class="title"><label>昄依证编号：</label></td>
    				<td>
    					<input name="advCardCode" id="addAdvCardCode" class="easyui-validatebox" >
    				</td>
    			</tr>
    			<tr>
    				<td class="title"><label>备注：</label></td>
    				<td colspan="3"><textarea rows="4" cols="65" name="deadRemark"></textarea></td>
    			</tr>
    		</table>
	    </form>
	</div>
	
	<!-- 修改使用者信息窗口 -->
    <div id="editWindow" style="text-align: center;">
    	<form id="editForm" action="${pageContext.request.contextPath }/api/deader_edit.action" method="post">
    		<input type="hidden" name="deadId">
    		<table align="center" class="form-container">
    			<tr>
    				<td class="title"><label>姓名：</label></td>
    				<td><input name="deadName" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>名族：</label></td>
    				<td><input name="deadNational" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>籍贯：</label></td>
    				<td><input name="deadNatPlace" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>家庭地址：</label></td>
    				<td><input name="deadAdress" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>出身日期：</label></td>
    				<td><input name="deadBirthday" class="easyui-datebox"  data-options="required:true,editable:false"></td>
    				<td class="title"><label>身份证号：</label></td>
    				<td><input name="identNum" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>往生时间：</label></td>
    				<td><input name="deadedDate" class="easyui-datebox"  data-options="required:true,editable:false"></td>
    				<td class="title"><label>入住时间：</label></td>
    				<td><input name="inputDate" class="easyui-datebox"  data-options="required:true,editable:false"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>接洽居士姓名：</label></td>
    				<td><input name="contactName" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>接洽居士电话：</label></td>
    				<td><input name="contactTell" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>联系人地址：</label></td>
    				<td><input name="contactAdress" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>使用者性别：</label></td>
    				<td>
    					<select name="desSex" class="easyui-combobox" data-options="editable:false,width:100,required:true,panelHeight:100">
			    			<option value="男" selected="selected">男</option>
			    			<option value="女">女</option>
			    			<option value="其它">其它</option>
			    		</select>
    				</td>
    			</tr>
    			<tr>
    				<td class="title"><label>对应福位：</label></td>
    				<td>
    					<input id="editBlessSeatId" name="blessSeatId">
    				</td>
    				<td class="title"><label>昄依证编号：</label></td>
    				<td>
    					<input name="advCardCode" id="addAdvCardCode" class="easyui-validatebox" >
    				</td>
    			</tr>
    			<tr>
    				<td class="title"><label>备注：</label></td>
    				<td colspan="3"><textarea rows="3" cols="50" name="deadRemark"></textarea></td>
    			</tr>
    		</table>
	    </form>
    </div>
    <!-- 会员捐赠窗口 -->
    <div id="checkBlessSeatWindow">
    	<table id="blessSeatGrid"></table>
    	<div id="blessSeatGridTB">
    		<a id="checkBSButton">选择</a>
    		<input id="blessSeatGridSearchbox">
    		<div id="bsgridSearchboxMenu">
    			<div name="bsCode">福位编号</div>
    		</div>
    	</div>
    </div>
</html>
