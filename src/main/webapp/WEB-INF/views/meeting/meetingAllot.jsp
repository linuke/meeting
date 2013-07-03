<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>会议通知管理系统</title>
<link Href="<%=path%>/style/new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<script>
	function allot() {
		var flag = 0;
		for ( var i = 0; i < allotForm.elements.length; i++) {
			if (allotForm.elements[i].name == "roomId") {
				if (allotForm.elements[i].checked) {//取得选定的会议室
					flag = 1;
				}
			}
		}
		if (flag == 0) {
			alert("提示：请选择要分配的会议室！");
			return false;
		}
		allotForm.ctrl.value = "allotRoom";
		allotForm.action = "MeetingServlet";
		allotForm.submit();
	}
</script>
</head>
<body>
	<div class="container">
		<jsp:include page="/index/top.jsp"/>
		<FORM name="allotForm" METHOD=POST action="">
			<div style="height:auto; text-align: center;margin: 0 auto;">
				<table class="dtable" align="center" cellpadding="1" cellspacing="1">
					<tr >
						<th width="10%" align="center">选中</th>
						<th width="20%" align="center">所在楼会议时</th>
						<th width="20%" align="center">房间号</th>
						<th width="20%" align="center">容纳人数</th>
						<th width="30%" align="center">说明</th>
					</TR>
					<c:forEach items="${mrs }" var="mr">
						<tr>
							<TD width="10%" valign="middle" align="center">
								<input type="radio" value="${mr.id }" name="roomId"  
									<c:if test="${mr.id == roomId }">checked="checked"</c:if>
									<c:if test="${mr.isFree != '1' }">disabled="disabled"</c:if>
								>
							</TD>
							<TD width="20%">${mr.building }</TD>
							<TD width="20%">${mr.room }</TD>
							<TD width="20%">${mr.capacity }</TD>
							<TD width="30%">${mr.remark }</TD>
						</TR>
					</c:forEach>
					<tr bgcolor="#FFFFFF">
						<TD align="center" colspan="5" >
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" value="提交"  onclick="allot()">
									&nbsp;&nbsp;&nbsp;&nbsp; 
									<input type="reset" value="重置" name="renew"></font>
						</TD>
					</tr>
				</table>
				<br style="clear: both;" />
			</div>
			<input type="hidden" name="ctrl" value=""> 
			<input type="hidden" name="from" value="${from }"> 
			<input type="hidden" name="meetingID" value="${meetingId }">
		</form>
	</div>
	<br style="clear: both;" />
	<jsp:include page="/index/bottom.jsp"></jsp:include>
	
</body>
</html>
