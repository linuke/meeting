<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
<title></title>
<meta Name="generator" Content="Microsoft FrontPage 4.0">
<meta Name="author" Content="">
<meta Name="keywords" Content="">
<meta Name="description" Content="">
<link Href="<%=request.getContextPath() %>/style/new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8"></head>
<body Leftmargin="0" Topmargin="0" Marginwidth="0" Marginheight="0">
<div class="container" >
		<jsp:include page="/index/top.jsp"></jsp:include>
		<div><p align="center" ><font color="red">${empty error ? "服务器忙，请稍候再试！":error }&nbsp;&nbsp;&nbsp;<a href="#" onclick="history.go(-1);">返回</a></font></p></div>
	</div>
	<br style="clear:both;" />
	<jsp:include page="/index/bottom.jsp"></jsp:include>
</body>
</html>
