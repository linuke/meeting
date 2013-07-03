<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link Href="<%=basePath%>style/new.css" Rel="stylesheet" Type="text/css">
<script src="<%=path%>/script/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		var date = new Date();
		var year = date.getFullYear();
		for ( var i = year - 4; i < year + 4; i++) {
			if (i == year) {
				$("#year").append('<option value="'+i+'" selected="selected">' + i+ '</option>');
			} else {
				$("#year").append('<option value="'+i+'">' + i+ '</option>');
			}
		}
		var month = date.getMonth() + 1;
		for ( var i = 1; i <= 12; i++) {
			if (i == month) {
				$("#month").append(
						'<option value="'+i+'" selected="selected">' + i
								+ '</option>');
			} else {
				$("#month").append('<option value="'+i+'">' + i + '</option>');
			}
		}

		
	});
</script>
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<jsp:include page="/index/top.jsp" />
		<form name="allotForm" method="post" action="MeetingServlet">
			<div style="height: auto; text-align: center; margin: 0 auto;width: 700px;">
				<fieldset style="width: 700px;" >
					日期：<select id="year" name="year"></select> 年 <select id="month"
						name="month"></select>月  <input type="submit" value="统计" />
					<input type="hidden" name="ctrl" value="count" />
				</fieldset>
			</div>
			<br>
		</form>
		<div>
			<table class="dtable" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th>部门</th>
					<th>累计召开次数</th>
					<th>当前月召开次数</th>
					<th>下月计划召开次数</th>
				</tr>
				<c:forEach var="c" items="${counters }"> 
					<tr>
						<td>${c.depart }</td>
					<td>${c.totalCount }</td>
					<td>${c.curCount }</td>
					<td>${c.nextCount }</td>
					</tr>
				</c:forEach>
			</table>
			<br/>
		</div>
	</div>
	<jsp:include page="/index/bottom.jsp" />
</body>
</html>