<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../include.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>会议通知管理系统</title>
<link Href="<%=path %>/style/new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<script src="<%=basePath %>script/jquery-1.9.1.min.js"></script>
<script src="<%=basePath%>script/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div class="container">
		<jsp:include page="/index/top.jsp"/>
		<div>
			<form action="StatisticsServlet" method="post">
				<label for="roomID">教室:</label> 
				<select id="parent" name="_roomID" class="Wdate" style="height: 20px;">
					<option value="">-----------</option>
					<c:forEach items="${mrs }" var="mr">
						<option value="${mr.id }" >${mr.building }${mr.room }</option>
					</c:forEach>
				</select>
				<label for="startTime">日期:</label>
			<input class="Wdate" id="st" type="text" name="startTime"	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'et\')}'})" value="" />
			<label for="endTime">&nbsp;&nbsp;至:&nbsp;&nbsp;</label>
			<input class="Wdate" id="et" type="text" name="endTime"	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'st\')}'})" value="" /> 
				<input style="COLOR: #44606B; background-color: #FFFFFF"
					type="submit" value="搜索"> <input type="hidden" name="ctrl"
					value="statistics2" />
			</form>
		</div>
		<br>
		<div>
			<table class="dtable" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th width="5%" align="center">序号</th>
					<th width="30%" align="center">教室</th>
					<th width="10%" align="center">使用次数</th>
					<th width="5%" align="center">序号</th>
					<th width="30%" align="center">教室</th>
					<th width="10%" align="center">使用次数</th>
				</tr>
				<tr>
					<c:forEach items="${result }" var="r" varStatus="i">
						<th width="5%" align="center">${i.count }</th>
						<td width="30%" align="center">${r[2] }${r[3] }</td>
						<td width="10%" align="center"><a title="点击查看详细"
							href="StatisticsServlet?ctrl=sdetail&roomID=${r[1]}&roomname=${r[2] }${r[3] }&startTime=${startTime}&endTime=${endTime}">${r[0] }</a></td>
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
	<jsp:include page="/index/bottom.jsp" />

</body>
</html>