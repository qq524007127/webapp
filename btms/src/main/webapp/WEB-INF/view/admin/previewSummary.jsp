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
    
    <title>数据汇总打印</title>
    
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
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/plugs/print.js" charset="UTF-8" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/plugs/jquery.jqprint-0.3.js" charset="UTF-8" ></script>
	<script type="text/javascript">
		function print(){
			$('#printContainer').jqprint();
		}
		function submitForm(){
			$('#form').submit();
		}
		$(function(){
			$('#printBtn').click(function(){
				printer.preview({
					footer:'&b&b&b第&p页/共&P页'
				});
			});
		});
	</script>
  </head>
  
  <body>
  	<div class="noPrint" style="background-color: #dddddd;">
  		<form id="form" method="get" action="${pageContext.request.contextPath }/admin/previewSummary.action" style="padding: 0;margin: 0;">
	  		<a title="打印预览" class="easyui-linkbutton" data-options="iconCls:'icon-print'" id="printBtn">打印</a>
	  		<!-- <a title="打印" class="easyui-linkbutton" data-options="iconCls:'icon-print'" onclick="print()">打印</a> -->
	  		<span style="padding-left: 25px;">
  				开始时间：
	  			<input name="startDate" value="<s:date name='startDate' format='yyyy-MM-dd' />" type="text" class="easyui-datebox" data-options="editable:false">
	  			结束时间：
	  			<input name="endDate" type="text" value="<s:date name='endDate' format='yyyy-MM-dd' />" class="easyui-datebox" data-options="editable:false">
	  			<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="submitForm()">查询</a>
  			</span>
  		</form>
  	</div>
  	<div id="printContainer" style="width: 100%; text-align: center;">
		<table width="100%" align="center" border="1" cellpadding="0" cellspacing="0" style="text-align: center; border:0.5px blue soild; padding:0; margin: 0;">
  			<thead>
  				<tr>
  					<td colspan="14" style="vertical-align: middle;">
						<h3>数据汇总表(<s:date name="startDate" format="yyyy-MM-dd"/>至<s:date name="endDate" format="yyyy-MM-dd"/>)</h3>
					</td>
  				</tr>
  				<tr>
  					<td>&nbsp;</td>
  					<td colspan="5">福位统计</td>
  					<td colspan="3">牌位统计</td>
  					<td colspan="2">管理费统计</td>
  					<td colspan="2">其它费用统计</td>
  					<td>&nbsp;</td>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
  					<td>统计日期</td>
  					<td>捐赠数量</td>
  					<td>捐赠金额</td>
  					<td>租赁数量</td>
  					<td>租赁金额</td>
  					<td>剩余数量</td>
  					<td>捐赠数量</td>
  					<td>捐赠金额</td>
  					<td>剩余数量</td>
  					<td>缴费数量</td>
  					<td>缴费金额</td>
  					<td>数量</td>
  					<td>金额</td>
  					<td>小计</td>
  				</tr>
  			<s:iterator value="dataSummaryList" var="summary" status="statu">
  				<s:if test="#statu.index % 27 == 0 && #statu.index > 0">
  					<tr class="pageNext">
  				</s:if>
  					<td>${summary.createDate }</td>
  					<td>${summary.bsLeaseCount }</td>
  					<td>${summary.bsLeaseTotalPrice }</td>
  					<td>${summary.bsBuyCount }</td>
  					<td>${summary.bsBuyTotalPrice }</td>
  					<td>${summary.tbltBuyCount }</td>
  					<td>${summary.tblTotalPrice }</td>
  					<td>${summary.memberCount }</td>
  					<td>${summary.memberTotalPrice }</td>
  					<td>${summary.mngRecCount }</td>
  					<td>${summary.mngTotalPrice }</td>
  					<td>${summary.itemCount }</td>
  					<td>${summary.itemTotalPrice }</td>
  					<td>${summary.total }</td>
  				</tr>
  			</s:iterator>
  			</tbody>
  			<!-- <tfoot>
  				<tr>
  					<td>小计</td>
  					<td></td>
  					<td></td>
  					<td></td>
  					<td></td>
  					<td></td>
  					<td></td>
  					<td></td>
  					<td></td>
  					<td></td>
  					<td></td>
  					<td></td>
  					<td></td>
  				</tr>
  			</tfoot> -->
  		</table>  		
  	</div>
  </body>
</html>
