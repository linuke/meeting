<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=path%>/style/new.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/style/ul.css" rel="stylesheet" type="text/css">
<script src="<%=path%>/script/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/easyui/easyloader.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<jsp:include page="/index/top.jsp" />
		<div style="height: auto; text-align: center; margin: 0 auto;">
			<form action="" method="post">
				<fieldset>
					<legend>会议室预定</legend>
					<table class="dtable" align="center" cellpadding="1" cellspacing="1">
						<thead>
							<tr>
								<th width="5%" align="center">序号</th>
								<th width="25%" align="center">会议室所在楼</th>
								<th width="20%" align="center">房间号</th>
								<th width="10%" align="center">容纳人数</th>
								<th width="25%" align="center">说明</th>
								<th width="15%" align="center">操作</th>
							</tr>
						</thead>
						<c:forEach items="${mrs }" var="mr" varStatus="i">
					<tr>
						<td width="5%" align="center">${i.count }</td>
						<td width="25%" align="center">${mr.building }</td>
						<td width="20%" align="center">${mr.room }</td>
						<td width="10%" align="center">${mr.capacity }</td>
						<td width="25%" align="center">${mr.remark }</td>
						<td width="15%" align="center"></td>
					</tr>
				</c:forEach>
					</table>
					
				</fieldset>
			</form>
		</div>
	</div>
	<jsp:include page="/index/bottom.jsp" />
</body>
</html>