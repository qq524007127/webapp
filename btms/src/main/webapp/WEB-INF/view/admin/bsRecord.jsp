<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>福位级别管理</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit">
    <meta content="always" name="referrer">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <script type="text/javascript" src="${pageContext.request.contextPath }/js/app.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/plugs/wordPrinter.js" charset="UTF-8"></script>
    <script type="text/javascript">
        app.init('${pageContext.request.contextPath}');
        app.addScript('bsRecord.js');
    </script>

</head>

<body>
<table id="bsRecordGrid"></table>
<s:if test="memberId != null">
    <script type="text/javascript">
        bsRecord.initOnMember('${memberId}');
    </script>
</s:if>
<s:elseif test="enterpriseId != null">
    <script type="text/javascript">
        bsRecord.initOnEnterprise('${enterpriseId}');
    </script>
</s:elseif>
</body>

</html>
