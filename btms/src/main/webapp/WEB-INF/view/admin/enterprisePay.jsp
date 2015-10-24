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

<title>企业捐赠</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta name="renderer" content="webkit">
	<meta content="always" name="referrer"> 
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/app.js" charset="UTF-8"></script>
<script type="text/javascript">
		app.init('${pageContext.request.contextPath}');
		app.addScript('enterprisePay.js');
</script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/plugs/excelPrinter.js" charset="UTF-8" ></script>

</head>

<body>
	
	<div id="mainPanel" class="easyui-panel" data-options="fit:true,border:false">
		<%-- <div id="enterInfoPanel" class="easyui-panel" data-options="height:'20%',border:false" title="企业信息">
			企业名称：${enterprise.enterName }，营业执照编号：${enterprise.busLisCode }
		</div> --%>
		<div id="enterprisePayItemPanel" class="easyui-dialog" data-options="border:false,draggable:false,fit:true,closable:false,toolbar:'#payToolbar'" title="捐赠清单">
			<form id="payForm" style="margin: 0; padding: 0" method="post" action="${pageContext.request.contextPath }/api/enterprisePay_doPay.action">
				<div>
					<input type="hidden" name="enterId" value="${enterprise.enterId }">
				
					<%-- 福位捐赠（租赁） --%>
					<table id="BsBuyList" align="center" style="text-align: center;"  width="95%">
						<thead>
							<tr>
								<td colspan="6"><h3>福位捐赠</h3><hr></td>
							</tr>
							<tr>
								<td width='20%'>福位编号</td>
								<td width='16%'>福位级别</td>
								<td width='16%'>价格</td>
								<td width='16%'>捐赠类型</td>
								<td width='16%'>租赁时长</td>
								<td width='16%'>操作</td>
							</tr>
						</thead>
						<tbody align="center"></tbody>
					</table>
					
					<%-- 牌位捐赠 --%>
					<table id="tabletBuyList" align="center" style="text-align: center;"  width="95%">
						<thead align="center">
							<tr>
								<td colspan="5"><h3>牌位捐赠</h3><hr></td>
							</tr>
							<tr>
								<td width='20%'>牌位名称</td>
								<td width='20%'>牌位价格</td>
								<td width='20%'>购买年限/年</td>
								<td width='20%'>总价</td>
								<td width='20%'>操作</td>
							</tr>
						</thead>
						<tbody align="center"></tbody>
					</table>
					
					<%-- 其它收费项目 --%>
					<table id="itemBuyList" align="center" style="text-align: center;"  width="95%">
						<thead align="center">
							<tr>
								<td colspan="5"><h3>其它收费项目</h3><hr></td>
							</tr>
							<tr>
								<td width='20%'>项目名称</td>
								<td width='20%'>项目价格</td>
								<td width='20%'>年限/年</td>
								<td width='20%'>总价</td>
								<td width='20%'>操作</td>
							</tr>
						</thead>
						<tbody align="center"></tbody>
					</table>
				</div>
			</form>
			<h2 id="allTotalPrice"></h2>
		</div>
	</div>
	<div id="payToolbar">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="showBSWindow()">捐赠福位</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="showTLWindow()">捐赠牌位</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="showItemWindow(0)">捐赠其它项目</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="showBuyedWindow()">捐赠福位管理费</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="showItemWindow(1)">捐赠会员费</a>
		<span style="margin-left:50px;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitPayForm()">提交</a>
		</span>
	</div>		
	
	<!-- 福位选择窗口 -->
	<div title="福位列表" id="blessSeatWindow">
		<table id="blessSeatGrid"></table>
		<div id="bsGridTB">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="checkBlessSeat()">捐赠</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="checkBlessSeatOnLease()">租赁</a>
			<input id="BSGridSearchBox" class="easyui-searchbox" data-options="searcher:'doSearch', prompt:'输入福位编号搜索'" style="width:200px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doBSGridSearch()">搜索</a>
		</div>
	</div>
	<!-- 牌位选择窗口 -->
	<div title="牌位列表" id="tabletWindow">
		<table id="tabletGrid"></table>
		<div id="tlGridTB">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="checkTablet()">选择</a>
			<input id="TLGridSearchBox" class="easyui-searchbox" data-options="searcher:'doSearch', prompt:'输入福位编号搜索'" style="width:200px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doTLGridSearch()">搜索</a>
		</div>
	</div>
	<!-- 其它项目选择窗口 -->
	<div title="其它收费项目列表" id="expensItemWindow">
		<table id="expensItemGrid"></table>
		<div id="itemGridTB">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="checkExpensItem()">选择</a>
			<input id="ItemSearchBox" class="easyui-searchbox" data-options="searcher:'doSearch', prompt:'输入福位编号搜索'" style="width:200px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doItemGridSearch()">搜索</a>
		</div>
	</div>
	<!-- 收缴福位管理费窗口 -->
	<div title="我捐赠的福位" id="owerBSWindow">
		<table id="owerBSGrid"></table>
		<div id="owerBSGridTB">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="addBSMngCost()">选择</a>
			<input id="owerBSGridSearchBox" class="easyui-searchbox" data-options="searcher:'doSearch', prompt:'输入福位编号搜索'" style="width:200px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doOwerBSGridSearch()">搜索</a>
		</div>
	</div>
</body>
</html>
