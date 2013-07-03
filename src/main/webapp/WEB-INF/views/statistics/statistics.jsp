<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../include.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>会议通知管理系统</title>
<link Href="<%=path%>/style/new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<script src="<%=basePath%>script/jquery-1.9.1.min.js"></script>
<script src="<%=basePath%>script/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div class="container">
		<jsp:include page="/index/top.jsp" />
		<div>
			<form action="MeetingServlet" method="post">
				<label for="org">处室:</label> 
				<select id="org" name="org" class="Wdate" style="height: 20px;">
					<option value="">--全部--</option>
					<c:forEach items="${orgs }" var="org">
						<option value="${org[0] }">${org[1] }</option>
					</c:forEach>
				</select> 
				<label for="startTime">日期:</label> 
				<input class="Wdate" id="st" type="text" name="startTime"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'et\')}'})" value="" /> <label
					for="endTime">&nbsp;&nbsp;至:&nbsp;&nbsp;</label> <input class="Wdate" id="et" type="text"
					name="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'st\')}'})"
					value="" /> <input style="COLOR: #44606B; background-color: #FFFFFF" type="submit" value="搜索">
				<input type="hidden" name="ctrl" value="statistics" />
			</form>
		</div>
		<div>
			<table class="dtable" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th width="5%" align="center">序号</th>
					<th width="30%" align="center">承办部门</th>
					<th width="10%" align="center">会议召开总次数</th>
					<th width="20%" align="center">会议召开总时长（小时）</th>
					<th width="20%" align="center">平均时长（小时/次）</th>
				</tr>
				<c:forEach items="${result }" var="r" varStatus="i">
					<tr>
						<td width="5%" align="center">${i.count }</td>
						<td width="30%" align="center">${r[2] }</td>
						<td width="10%" align="center">${r[0] }</td>
						<td width="20%" align="center"><fmt:parseNumber value="${r[1] }" type="number"
								pattern="#0.0#" /></td>
						<td width="20%" align="center"><fmt:parseNumber value="${r[1]/r[0] }" type="number" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<jsp:include page="/index/bottom.jsp" />
</body>
</html>