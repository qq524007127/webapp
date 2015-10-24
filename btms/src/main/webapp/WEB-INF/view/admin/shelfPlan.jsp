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
    
    <title>福位架平面图</title>
    
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
	</script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		* {
			font-family: "微软雅黑";
			font-size: 12px;
		}
		.box {
			width:100px;
			height:80px;
		}
		.box .box-content {
			text-align: left;
			font-size: 12px;
		}
		.target-info {
			font-family: "微软雅黑";
			font-size: 12px;
		}
		.enable {
			min-wiight:100px;
			background-color: #008866;
			color: #CCEEFF;
		}
		
		.disable {
			min-widght:100px;
			background-color: #E0ECFF;
		}
	</style>
	
	<script type="text/javascript">
		function addShelf(){
			$('#addDialog').dialog('open');
		}
		
		function submitAddForm(){
			$('#addForm').form('submit',{
				success:function(data){
					data = $.parseJSON(data);
					if(data.success){
						location.reload();
					}
					else{
						$.messager.alert('',data.msg);
					}
				}
			});
		}
		
		//启用
		function setEnable(){
			if(!checkFrom()){
				$.messager.alert('', '请选择需要操作的数据！');
				return;
			}
			$('#mainForm').form('submit',{
				url:'api/shelfPlan_enable.action',
				success:function(data){
					data = $.parseJSON(data);
					if(data.success){
						location.reload();
					}
					else{
						$.messager.alert('',data.msg);
					}
				}
			});
		}
		
		function setDisable(){
			if(!checkFrom()){
				$.messager.alert('', '请选择需要操作的数据！');
				return;
			}
			$.messager.confirm('警告','设置福位架无效后，此福位架上对应的福位也就不可用，请谨慎使用。是否继续？',function(flag){
				$('#mainForm').form('submit',{
					url:'api/shelfPlan_disable.action',
					success:function(data){
						data = $.parseJSON(data);
						if(data.success){
							location.reload();
						}
						else{
							$.messager.alert('',data.msg);
						}
					}
				});
			});
		}
		
		function checkFrom(){
			return $('#mainForm input[name=shelfIds]:checked').length < 1 ? false : true;
		}
	</script>

  </head>
  
  <body>
  	<div class="easyui-dialog" title="福位架平面图" fit=true toolbar="#toolbarPanel" closable=false draggable=false">
  		<form action="" method="post" id="mainForm">
	  		<s:if test="area != null">
	    	<table align="center" border="0" cellpadding="1" cellspacing="5">
	    	<s:iterator begin="1" end="area.areaRow" step="1" var="row">
	    	<tr style="text-align: center; vertical-align: middle;">
	    		<s:iterator begin="1" end="area.areaColumn" step="1" var="column">
		    	<td style="padding: 5px; background-color: graytext;">
		    		<s:iterator value="shelfList" var="shelf">
		    			<s:if test="(#shelf.postionRow == #row) && (#shelf.postionColumn == #column)">
		    				<s:if test="#shelf.permit">
		    					<div class="box enable">
		    				</s:if>
		    				<s:else>
		    					<div class="box disable">
		    				</s:else>
		    					<div class="box-content">
		    						<p><input type="checkbox" name="shelfIds" value="${shelf.shelfId}" ></p>
		    						<p>编号：${shelf.shelfCode}</p>
		    						<p>
		    							<a href="${pageContext.request.contextPath }/admin/blessSeatPlan.action?shelfId=${shelf.shelfId}">查看福位</a>
		    						</p>
		    					</div>
		    				</div>
		    			</s:if>
		    		</s:iterator>
		    	</td>
		    	</s:iterator>	
	    	</tr>
	    	</s:iterator>
	    </table>
	    </s:if>
  		</form>
  	</div>
  	<div id="toolbarPanel">
  		<a class="easyui-linkbutton" iconCls="icon-back" onClick="history.back()">返回</a>
  		<a class="easyui-linkbutton" iconCls="icon-add" onClick="addShelf()">添加福位架</a>
  		<a class="easyui-linkbutton" iconCls="icon-ok" onClick="setEnable()">设为有效</a>
  		<a class="easyui-linkbutton" iconCls="icon-cancel" onClick="setDisable()">设为无效</a>
  		<a class="easyui-linkbutton" iconCls="icon-reload" onClick="location.reload()">刷新</a>
  	</div>
  	
  	<div id="addDialog" class="easyui-dialog" title="添加福位架" iconCls="icon-add" buttons="#addDialogToolbar"
  		closed=true modal=true style="width:400px;height: 200px">
  		<form id="addForm" method="post" action="${pageContext.request.contextPath }/api/shelfPlan_add.action">
  			<input type="hidden" name="areaId" value="${area.areaId }" > 
  			<table class="form-container" align="center">
  				<tr>
  					<td class="title"><label>所在区域：</label></td>
  					<td>
  						<p>${area.areaName }</p>
  					</td>
  				</tr>
  				<tr>
  					<td class="title"><label>所在区域行：</label></td>
  					<td>
  						<input name="areaRow" type="text" class="easyui-numberbox" value="${area.areaRow + 1 }" data-options="min:1,max:${area.areaRow + 1 },precision:0,required:true">
  					</td>
  				</tr>
  				<tr>
  					<td class="title"><label>所在区域列：</label></td>
  					<td>
  						<input name="areaColumn" type="text" class="easyui-numberbox" value="${area.areaColumn + 1 }" data-options="min:1,max:${area.areaColumn + 1 },precision:0,required:true">
  					</td>
  				</tr>
  			</table>
  		</form>
  	</div>
  	<div id="addDialogToolbar">
  		<a class="easyui-linkbutton" iconCls="icon-ok" onClick="submitAddForm()">添加</a>
  	</div>
  </body>
</html>
