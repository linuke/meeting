<%@ page contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<link Href="<%=basePath%>style/new.css" Rel="stylesheet" Type="text/css">
</head>
<body>
	<div class="container" >
		<jsp:include page="/index/top.jsp"></jsp:include>
		<div><p align="center" ><font size="5"><b>欢迎使用会议通知管理系统！</b></font></p></div>
	</div>
	<br style="clear:both;" />
	<jsp:include page="/index/bottom.jsp"></jsp:include>
</body>
</html>