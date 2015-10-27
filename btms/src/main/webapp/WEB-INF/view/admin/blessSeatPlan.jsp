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
    
    <title>福位平面展示图</title>
    
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
	<style type="text/css">
		* {
			font-family: "微软雅黑";
			font-size: 14px;
		}
		.box {
			min-width:100px;
			min-height:80px;
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
		.main_tontainer tr td {
			width: 140px;
			height: 80px;
		}
	</style>
	<script type="text/javascript">
		function doDisable(){
			if(!checkForm()){
				$.messager.alert('','请选择需要设置为无效的福位');
				return;
			}
			$.messager.confirm('警告','设置为无效后对应此福位的使用者将被删除，捐赠记录也失效，数据将不可回复，请谨慎使用，是否继续？',function(flag){
				if(flag){
					$('#mainForm').form('submit',{
						url:'api/blessSeatPlan_disable.action',
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
			});
		}
		
		function doEnable(){
			if(!checkForm()){
				$.messager.alert('','请选择需要设置为无效的福位');
				return;
			}
			$('#mainForm').form('submit',{
				url:'api/blessSeatPlan_enable.action',
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
		
		function checkForm(){
			if($('#mainForm input[name=bsIds]:checked').length > 0){
				return true;
			}
			return false;
		}
		
		function addBlessSeat(){
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

		function editBSCode(){
			var checkeds = $('.box-content input[type=checkbox]:checked');
			if(checkeds.length != 1){
				$.messager.alert('','请选择一个需要编辑的福位');
				return;
			}
			var bs = {};
			bs.bsId= $(checkeds[0]).val();
			bs.bsCode = $('#'+bs.bsId).html();
			$('#editCodeDialog').dialog({
				title:'编辑福位编号',
				iconCls: 'icon-edit',
				width: 300,
				height: 160,
				modal: true,
				buttons: [{
					text: '提交',
					iconCls: 'icon-ok',
					handler: function () {
						$('#editCodeForm').form('submit',{
							success: function (data) {
								data = $.parseJSON(data);
								$.messager.show({
									title: '提示',
									msg: data.msg
								});
								if (data.success) {
									$('#' + bs.bsId).html($('#editCodeForm input[name=bsCode]').val());
									$('#editCodeDialog').dialog('close');
								}
							}
						});
					}
				}]
			});
			$('#editCodeForm').form('clear');
			$('#editCodeForm').form('load', bs);
		}
	</script>
  </head>
  
  <body>
  	<div class="easyui-dialog" title="福位平面图" fit=true toolbar="#toolbarPanel" closable=false draggable=false">
  		<form id="mainForm" method="post">
  			<s:if test="shelf != null">
		    <table class="main_tontainer" align="center" border="0" cellpadding="1" cellspacing="8">
		    	<s:iterator begin="1" end="shelf.shelfRow" step="1" var="row">
		    	<tr style="text-align: center; vertical-align: middle;">
		    		<s:iterator begin="1" end="shelf.shelfColumn" step="1" var="column">
			    	<td style="padding: 5px; background-color: graytext;">
			    		<s:iterator value="blessSeatList" var="bs">
			    			<s:if test="(#bs.shelfRow == #row) && (#bs.shelfColumn == #column)">
			    				<s:if test="#bs.permit">
			    					<div class="box enable">
			    				</s:if>
			    				<s:else>
			    					<div class="box disable">
			    				</s:else>
			    					<div class="box-content">
			    						<p><input type="checkbox" name="bsIds" value="${bs.bsId }"></p>
			    						<p class="target-info">编号：<span id="${bs.bsId}">${bs.bsCode }</span></p>
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
  		<a class="easyui-linkbutton" iconCls="icon-add" onClick="addBlessSeat()">添加福位</a>
  		<a class="easyui-linkbutton" iconCls="icon-ok" onClick="doEnable()">设为有效</a>
  		<a class="easyui-linkbutton" iconCls="icon-cancel" onClick="doDisable()">设为无效</a>
		<a class="easyui-linkbutton" iconCls="icon-edit" onClick="editBSCode()">自定义编号</a>
  		<a class="easyui-linkbutton" iconCls="icon-reload" onClick="location.reload()">刷新</a>
  	</div>

	<!--编辑福位编号窗口 -->
	<div id="editCodeDialog">
		<form id="editCodeForm" method="post" action="${pageContext.request.contextPath}/api/blessSeatPlan_editCode.action">
			<input name="bsId" type="hidden">
			<table class="form-container" align="center">
				<tr>
					<td class="title"><label>自定义编号：</label></td>
					<td>
						<p>
							<input name="bsCode" class="easyui-validatebox" data-options="required:true">
						</p>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!--添加福位窗口-->
  	<div id="addDialog" class="easyui-dialog" title="添加福位" iconCls="icon-add" buttons="#addDialogToolbar"
  		closed=true modal=true style="width:400px;height: 200px">
  		<form id="addForm" method="post" action="${pageContext.request.contextPath }/api/blessSeatPlan_add.action">
  			<input type="hidden" name="shelfId" value="${shelf.shelfId }" > 
  			<table class="form-container" align="center">
  				<tr>
  					<td class="title"><label>所在福位架：</label></td>
  					<td>
  						<p>${shelf.shelfCode }</p>
  					</td>
  				</tr>
  				<tr>
  					<td class="title"><label>所在福位架行：</label></td>
  					<td>
  						<input name="shelfRow" type="text" class="easyui-numberbox" value="${shelf.shelfRow + 1 }" data-options="min:1,max:${shelf.shelfRow + 1 },precision:0,required:true">
  					</td>
  				</tr>
  				<tr>
  					<td class="title"><label>所在福位架列：</label></td>
  					<td>
  						<input name="shelfColumn" type="text" class="easyui-numberbox" value="${shelf.shelfColumn + 1 }" data-options="min:1,max:${shelf.shelfColumn + 1 },precision:0,required:true">
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
