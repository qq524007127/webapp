<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>缴费清单</title>
    
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
		$(function(){
			$('#print_btn').click(function(){
				printer.preview({});
			});
		});
	</script>
	<style type="text/css">
		.payinfo-container {
			text-align: center; 
			width:98%;
		}
		.payinfo-container tr td{
			padding: 5px 0;
		} 
		
		.payinfo-container tr .title{
			font-weight: bold;
			padding: 10px 0;
		} 
	</style>
  </head>
  
  <body>
  	<div class="noPrint">
  		<a class="easyui-linkbutton" data-options="iconCls:'icon-print'" id="print_btn">打印</a>
  	</div>
  	<div id="print_container">
  		<table class="payinfo-container" align="center">
  			<tr>
  			<s:if test="payRecord.mem != null">
  				<td>会员名称：${payRecord.mem.memberName }</td>
  				<td>会员证编号：${payRecord.mem.memberCard.cardCode }</td>		
  				<td>缴费日期：<s:date name="payRecord.payDate" format="yyyy-MM-dd HH:mm:ss"/></td>
  				<td>收费员：${payRecord.payUser.userName }</td>
  			</s:if>
  			<s:else>
  				<td>会员名称：${payRecord.enterprise.enterName }</td>
  				<td>会员证编号：${payRecord.enterprise.card.cardCode }</td>		
  				<td>收费日期：<s:date name="payRecord.payDate" format="yyyy-MM-dd HH:mm:ss"/></td>
  				<td>收费员：${payRecord.payUser.userName }</td>
  			</s:else>
  			</tr>
  			<%-- 牌位捐赠（租赁） --%>
  			<s:if test="payRecord.bsRecordSet != null && payRecord.bsRecordSet.size > 0">
  			<tr>
  				<td class="title" colspan="4">福位捐赠（租赁）费<hr></td>
  			</tr>
  			<tr>
  				<td>福位编号</td>
  				<td>捐赠类型</td>
  				<td>租赁时长</td>
  				<td>金额</td>
  			</tr>
  			<s:iterator value="payRecord.bsRecordSet" var="bsr">
  			<tr>
  				<td>${bsr.blessSeat.bsCode }</td>
  				<td>
  					<s:if test='#bsr.donatType.toString() == "buy"'>捐赠</s:if>
  					<s:else>租赁</s:else>
  				</td>
  				<td>
  					<s:if test='#bsr.donatType.toString() == "buy"'>/</s:if>
  					<s:else>${bsr.donatLength }</s:else>
  				</td>
  				<td>
					<s:if test='#bsr.donatType.toString() == "buy"'>
					<fmt:formatNumber value="${bsr.blessSeat.lev.levPrice }" pattern="0.00"></fmt:formatNumber>
					</s:if>
  					<s:else>/</s:else>
				</td>
  			</tr>
  			</s:iterator>
  			</s:if>
  			
  			<%-- 牌位捐赠 --%>
  			<s:if test="payRecord.tlRecordSet != null && payRecord.tlRecordSet.size > 0">
  			<tr>
  				<td class="title" colspan="4">牌位捐赠费<hr></td>
  			</tr>
  			<tr>
  				<td>牌位捐赠</td>
  				<td>牌位价格</td>
  				<td>捐赠年限（时长）</td>
  				<td>金额</td>
  			</tr>
  			<s:iterator value="payRecord.tlRecordSet" var="tbr">
  			<tr>
  				<td>${tbr.tablet.tabletName }</td>
  				<td>
  					<fmt:formatNumber value="${tbr.tablet.tabletPrice }" pattern="0.00"></fmt:formatNumber>
  				</td>
  				<td>${tbr.tlRecLength }</td>
  				<td>
  					<fmt:formatNumber value="${tbr.tlTotalPrice }" pattern="0.00"></fmt:formatNumber>
  				</td>
  			</tr>
  			</s:iterator>
  			</s:if>
  			
  			<%-- 其它费用 --%>
  			<s:if test="payRecord.payDatailSet != null && payRecord.payDatailSet.size > 0">
  			<tr>
  				<td class="title" colspan="4">其它费用<hr></td>
  			</tr>
  			<tr>
  				<td>项目名称</td>
  				<td>项目价格</td>
  				<td>缴费年限（时长）</td>
  				<td>金额</td>
  			</tr>
  			<s:iterator value="payRecord.payDatailSet" var="detail">
  			<tr>
  				<td>${detail.detailItemName }</td>
  				<td>
  					<fmt:formatNumber value="${detail.itemPrice }" pattern="0.00"></fmt:formatNumber>
  				</td>
  				<td>
  				<s:if test="#detail.detailLength == 0 && #detail.detTotalPrice != 0">/</s:if>
  				<s:else>${detail.detailLength }</s:else>
  				</td>
  				<td>
  					<fmt:formatNumber value="${detail.detTotalPrice }" pattern="0.00"></fmt:formatNumber>
  				</td>
  			</tr>
  			</s:iterator>
  			</s:if>
  			<tr>
  				<td align="left" colspan="4" class="title">
  				<hr>合计：<fmt:formatNumber value="${payRecord.totalPrice }" pattern="0.00"></fmt:formatNumber>
  				</td>
  			</tr>
  			<tr>
  				<td align="right" colspan="4">
					签名：<input type="text" readonly="readonly" style="border:none;border-bottom: 1px black solid;"> 
				</td>
  			</tr>
  		</table>
  	</div>
  </body>
</html>
