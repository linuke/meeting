<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>会议室详细信息查询</title>
<link Href="<%=path %>/style/new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<script src="<%=basePath%>script/My97DatePicker/WdatePicker.js"></script>

</head>
<body>
	<div class="container">
		<jsp:include page="/index/top.jsp" />
		<form name="form" method="post" action="MeetingServlet">
			<div style="height: auto; text-align: center; margin: 0 auto;">
				<table width="800" border="0" align="center" cellpadding="0"
					cellspacing="0" bgcolor="698cc3">
					<tr>
						<td>
							<table width="100%" border="0" cellpadding="1" cellspacing="1"
								bordercolordark="#FFFFFF" bordercolorlight="#999999"
								align="center">
								<tr align="center" bgcolor="#FFFFFF">
									<td nowrap>会议地点: <select name="roomID" id="roomID">
											<option value="">--会议地点--</option>
											<c:forEach items="${result }" var="r" varStatus="i">
												<option value="${r.id }">${r.building}${r.room }</option>
											</c:forEach>
									</select>
									</td>
									<td nowrap>查询期间 : <input class="Wdate" id="st" type="text"
										style="width: 200px" name="startTime"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'et\')}'})"
										value="" /> &nbsp;&nbsp;<font style="font-weight: bold;">至</font>&nbsp;&nbsp;
										<input class="Wdate" id="et" type="text" style="width: 200px"
										name="endTime"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'st\')}'})"
										value="" />
									</td>
									<td width="60"><input type="submit" name="operate"
										style="COLOR: #44606B; background-color: #FFFFFF" value="查找" />
										<input type="reset" name="Reset"
										style="COLOR: #44606B; background-color: #FFFFFF" value="重写">
									</td>
								</tr>
							</table> <input type="hidden" name="ctrl" value="meetingRoomQry">
						</td>
					</tr>
				</table>

				<br />
				<table align="center" class="dtable" class="dtable" align="center" cellpadding="1" cellspacing="1">
					<tr align="center" bgcolor="#FFFFFF">
						<TD width="14%" align="center">日期</TD>
						<TD width="12%" align="center">会议名称</TD>
						<TD width="10%" align="center">主持人</TD>
						<TD width="13%" align="center">公司领导</TD>
						<TD width="15%" align="center">参加单位/人员</TD>
						<TD width="12%" align="center">承办单位及说明</TD>
						<TD width="13%" align="center">申请部门<br />日期
						</TD>
					</tr>
					<c:forEach var="m" items="${meetings }">
						<tr align="center" bgcolor="#FFFFFF">
							<TD width="14%" align="center"><fmt:formatDate
									value="${m.starttime }" pattern="yyyy-MM-dd HH:mm" /><br /> <fmt:formatDate
									value="${m.endtime }" pattern="yyyy-MM-dd HH:mm" /></TD>
							<TD width="12%" align="center">${m.content }</TD>
							<TD width="10%" align="center">${m.presider }</TD>
							<TD width="13%" align="center">${m.leader }</TD>
							<TD width="15%" align="center">${m.depart }</TD>
							<TD width="12%" align="center">${m.remark }</TD>
							<TD width="13%" align="center">${m.commitdepart }<br />
							<fmt:formatDate value="${m.committime }"
									pattern="yyyy-MM-dd HH:mm" /></TD>
						</tr>
					</c:forEach>
				</table>
			</div>
		</form>
	</div>
	<jsp:include page="/index/bottom.jsp" />
</body>
</html>
