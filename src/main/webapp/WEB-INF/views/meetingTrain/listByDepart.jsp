<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<div class="container">
		<jsp:include page="/index/top.jsp"></jsp:include>
		<div style="text-align: center;margin: 0 auto;">
			<table class="dtable" align="center" cellspacing="1">
				<tr>
					<th width="10%" align="center">日期</th>
					<th width="10%" align="center">会议名称</th>
					<th width="5%" align="center">主持人</th>
					<th width="10%" align="center">参加单位/人员</th>
					<th width="10%" align="center">有关说明</th>
					<th width="10%" align="center">预定教室</th>
					<th width="10%" align="center">分配教室</th>
					<th width="10%" align="center">状态</th>
					<th width="12%" align="center">申请部门<br />日期</th>
					<th width="3%" align="center">操作</th>
				</tr>
				<c:forEach items="${meetings }" var="mp">
					<tr>
						<td width="10%" align="center"><fmt:formatDate
								value="${mp.starttime }" pattern="E" /><br /> <fmt:formatDate
								value="${mp.starttime }" pattern="yyyy-MM-dd HH:mm" /><br />
							<fmt:formatDate value="${mp.endtime }"
								pattern="yyyy-MM-dd HH:mm" /></td>
						<td width="10%" align="center">${mp.content }</td>
						<td width="5%" align="center">${mp.presider }</td>
						<td width="10%" align="center">${mp.depart }</td>
						<td width="10%" align="center">${mp.remark }</td>
						<td width="10%" align="center">${mp.reserve_address }</td>
						<td width="10%" align="center">${mp.address1 }</td>
						<td width="10%" align="center"><c:if
								test="${mp.status =='0' }">
								<font color="">未处理</font>
							</c:if> <c:if test="${mp.status =='1' }">
								<font color="green">已审批</font>
							</c:if> <c:if test="${mp.status =='2' }">
								<font color="red">拒批</font>
							</c:if> <c:if test="${mp.status =='3' }">
								<font color="">已安排会议室</font>
							</c:if> <c:if test="${mp.status =='4' }">
								<font color="">退回</font>
							</c:if></td>
						<td width="12%" align="center">${mp.commitdepart }<br />
							<fmt:formatDate value="${mp.committime }"
								pattern="yyyy-MM-dd HH:mm" /></td>
						<td width="3%" align="center"><a
							href="MeetingTrainingServlet?ctrl=toUpdate&show=my&id=${mp.id }">修改</a> <a
							href="javascript:confirmDelete('MeetingTrainingServlet?ctrl=del&id=${mp.id }')">删除</a>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td align="right" colspan="11">${tag }</td>
				</tr>
			</table>
		</div>
	</div>
	<jsp:include page="/index/bottom.jsp"/>
</body>
</html>