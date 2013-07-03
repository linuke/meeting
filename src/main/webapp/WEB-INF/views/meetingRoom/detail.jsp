<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=path%>/style/new.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/style/ul.css" rel="stylesheet" type="text/css">
<title>Insert title here</title>
</head>
<body>
<div style="width: 100%;">
	<table cellpadding="0" cellspacing="1" style="border: 0;background-color:#698cc3;width: 90%;margin-left: 0;margin-top: 0">
		<tr class="P1" style="border-color: gray;background-color:#FFFFFF;font-size: 11pt; ">
			<th style="width:5%">序号</th>
			<th style="width:25%">建筑名称</th>
			<th style="width:25%">房间名称</th>
			<th style="width:5%">人数</th>
			<th style="width:25%">说明</th>
			<th style="width:15%">操作</th>
		</tr>
		<c:forEach var="mr" items="${mrs }" varStatus="i">
		
		</c:forEach>
	</table>
</div>
</body>
</html>