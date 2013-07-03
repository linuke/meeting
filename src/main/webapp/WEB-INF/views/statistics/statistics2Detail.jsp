<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../include.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>会议通知管理系统</title>
<link Href="<%=path %>/style/new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
</head>
<body>
	<div class="container">
		<jsp:include page="/index/top.jsp" />
		<div style="height: auto; text-align: center; margin: 0 auto;">
			<table class="dtable" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th width="5%" align="center">序号</th>
					<th width="30%" align="center">处室</th>
					<th width="10%" align="center">使用次数</th>
					<th width="5%" align="center">序号</th>
					<th width="30%" align="center">处室</th>
					<th width="10%" align="center">使用次数</th>
				</tr>
				<tr>
					<c:forEach items="${result }" var="r" varStatus="i">
						<th width="5%" align="center">${i.count }</th>
						<td width="30%" align="center">${r[2] }</td>
						<td width="10%" align="center">${r[0] }</td>
						<c:if test="${i.count % 2 == 0 && i.count != fn:length(result)}">
				</tr>
				<tr>
					</c:if>
					</c:forEach>
					<c:if test="${fn:length(result) % 2 != 0 }">
						<th width="5%" align="center">&nbsp;</th>
						<td width="30%" align="center">&nbsp;</td>
						<td width="10%" align="center">&nbsp;</td>
					</c:if>
				</tr>
			</table>
		</div>
	</div>
	<!-- <div>
		<a href="javascript:history.go(-1);">返回</a>
	</div> -->
	<jsp:include page="/index/bottom.jsp" />

</body>
</html>