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
		app.addScript('member.js');
	</script>
	
  </head>
  
  <body>
 	<table id="memberGrid" data-options="toolbar:'#toolbarPanel'"></table>
 	<%-- 工具栏 --%>
 	<div id="toolbarPanel">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="showAddWindow()">添加会员</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="showEidtWindow()">会员信息修改</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="shwoPayWindow()">会员捐赠</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="showPreSellWindow()">福位预售</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="memberExite()">会员退会</a>
		<%-- <a class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart'">导出</a> --%>
		<span style="margin-left:50px;">
			<input id="memberGirdSearchbox" class="easyui-searchbox" data-options="prompt:'输入关键字搜索',menu:'#searchboxMenu'" style="width:300px"></input>
			<div id="searchboxMenu"> 
				<div name="memberName">会员名称</div>
		    	<div name="memberIdentNum">身份证号</div>
		    	<div name="memberCard.memCode">会员证号</div>
			</div>
		</span>
	</div>
	
	<!-- 添加会员窗口 -->
  	<div id="addWindow" style="text-align: center;">
    	<form id="addForm" action="${pageContext.request.contextPath }/api/member_add.action" method="post">
    		<table align="center" class="form-container">
    			<tr>
    				<td class="title"><label>会员名称：</label></td>
    				<td><input id="editUserName" name="memberName" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>名族：</label></td>
    				<td><input id="editUserName" name="memberNational" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>籍贯：</label></td>
    				<td><input id="editUserName" name="memberNatPlace" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>家庭地址：</label></td>
    				<td><input id="editUserName" name="memberAddress" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>出身日期：</label></td>
    				<td><input id="editUserName" name="memberBirthday" class="easyui-datebox"  data-options="required:true,editable:false"></td>
    				<td class="title"><label>身份证号：</label></td>
    				<td><input id="editUserName" name="memberIdentNum" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>联系电话：</label></td>
    				<td><input id="editUserName" name="memberTell" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>工作单位：</label></td>
    				<td><input id="editUserName" name="memberUnit" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>性别：</label></td>
    				<td>
    					<select name="memberSex" class="easyui-combobox" data-options="editable:false,width:100,required:true,panelHeight:100">
			    			<option value="男" selected="selected">男</option>
			    			<option value="女">女</option>
			    			<option value="其它">其它</option>
			    		</select>
    				</td>
    				<td class="title"><label>备用联系人姓名：</label></td>
    				<td><input id="editUserName" name="spareName" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>备用联系人电话：</label></td>
    				<td><input id="editUserName" name="spareTell" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label for="addMemberPermit">有效：</label></td>
    				<td><input type="checkbox" id="addMemberPermit" name="memberPermit" value=true checked="checked"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>经办人：</label></td>
    				<td colspan="3">
    					<input name="saler.salerId" id="addSaler">
    				</td>
    			</tr>
    			<tr>
    				<td class="title"><label>备注：</label></td>
    				<td colspan="3"><textarea rows="3" cols="50" name="memberRemark"></textarea></td>
    			</tr>
    		</table>
	    </form>
	</div>
	
	<!-- 修改会员窗口 -->
    <div id="editWindow" style="text-align: center;">
    	<form id="editForm" action="${pageContext.request.contextPath }/api/member_edit.action" method="post">
	    	<input type="hidden" name="memberId">
	    	<input type="hidden" name="memberPassword">
	    	<table align="center" class="form-container">
    			<tr>
    				<td class="title"><label>会员名称：</label></td>
    				<td><input id="editUserName" name="memberName" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>名族：</label></td>
    				<td><input id="editUserName" name="memberNational" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>籍贯：</label></td>
    				<td><input id="editUserName" name="memberNatPlace" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>家庭地址：</label></td>
    				<td><input id="editUserName" name="memberAddress" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>出身日期：</label></td>
    				<td><input id="editUserName" name="memberBirthday" class="easyui-datebox"  data-options="required:true,editable:false"></td>
    				<td class="title"><label>身份证号：</label></td>
    				<td><input id="editUserName" name="memberIdentNum" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>联系电话：</label></td>
    				<td><input id="editUserName" name="memberTell" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label>工作单位：</label></td>
    				<td><input id="editUserName" name="memberUnit" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>性别：</label></td>
    				<td>
    					<select name="memberSex" class="easyui-combobox" data-options="editable:false,width:100,required:true,panelHeight:100">
			    			<option value="男" selected="selected">男</option>
			    			<option value="女">女</option>
			    			<option value="其它">其它</option>
			    		</select>
    				</td>
    				<td class="title"><label>备用联系人姓名：</label></td>
    				<td><input id="editUserName" name="spareName" class="easyui-validatebox"  data-options="required:true"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>备用联系人电话：</label></td>
    				<td><input id="editUserName" name="spareTell" class="easyui-validatebox"  data-options="required:true"></td>
    				<td class="title"><label for="editMemberPermit">有效：</label></td>
    				<td><input type="checkbox" id="editMemberPermit" value=true name="memberPermit" checked="checked"></td>
    			</tr>
    			<tr>
    				<td class="title"><label>经办人：</label></td>
    				<td colspan="3">
    					<input name="saler.salerId" id="editSaler">
    				</td>
    			</tr>
    			<tr>
    				<td class="title"><label>备注：</label></td>
    				<td colspan="3"><textarea rows="3" cols="50" name="memberRemark"></textarea></td>
    			</tr>
    		</table>
	    </form>
    </div>
    <!-- 会员捐赠窗口 -->
    <div id="memberPayWindow"></div>
    
     <!-- 福位预售窗口 -->
    <div id="preSellWindow"></div>
    
    <!-- 会员已捐赠福位、牌位窗口 -->
    <div id="buyedListWindow"></div>
    
    <!-- 会员缴费记录窗口 -->
    <div id="payedListWindow"></div>
    
    <!-- 会员社会关系窗口 -->
    <div id="relationWindow"></div>
  </body>
</html>
