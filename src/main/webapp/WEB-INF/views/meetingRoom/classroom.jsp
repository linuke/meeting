﻿<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="../include.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<title>会议通知管理系统</title>
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<link Href="<%=path%>/style/new.css" Rel="stylesheet" Type="text/css">
<link href="<%=path%>/style/ul.css" rel="stylesheet" type="text/css">
<script src="<%=path%>/script/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/easyui/easyloader.js"></script>
<script>
	function check() {
		if (meetingRoomForm.building.value == "") {
			alert("请输入会议室地点！");
			return false;
		} else {
			meetingRoomForm.action = "MeetingRoomServlet";
			meetingRoomForm.submit();
			meetingRoomForm.subdisabled = "disabled";
		}
	}

	function ifNumber() {
		var num, i, j, strTemp;
		num = meetingRoomForm.capacity.value;
		if (num != "") {
			for (i = 0; i < num.length; i++) {
				strTemp = "0123456789";
				j = strTemp.indexOf(num.charAt(i));
				if (j == -1) {
					alert("会议室容纳人数应该为数字，请重新填写!");
					meetingRoomForm.capacity.value = "";
					meetingRoomForm.capacity.focus();
					return false;
				}
			}
		}
	}
	function setFocus() {
		meetingRoomForm.building.focus();
	}
</script>
</head>
<body onload="setFocus();">
	<div class="container">
		<jsp:include page="/index/top.jsp" />
		<FORM name="meetingRoomForm" METHOD=POST action="">
			<div style="width: 500px;height:auto; text-align: center;margin: 0 auto;">
				<dl class="dl-table">
					<dt>地点：</dt>
					<dd>
						<input type="hidden"  name="building" value="培训中心">
						培训中心
					</dd>
					<dt>房间号：</dt>
					<dd>
						<input type="text" name="room" size="27" value="${mr.room }">
					</dd>
					<dt>容纳人数：</dt>
					<dd>
						<input type="text" name="capacity" size="27" value="${mr.capacity }" onchange="ifNumber()">
					</dd>
					<%-- <dt>会议室类型：</dt>
					<dd>
						<input type="radio" name="type" size="50" value="1" <c:if test="${mr.type == null || mr.type==1 }">checked="checked"</c:if>>会议室
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="type" size="50" value="2" <c:if test="${mr.type==2 }">checked="checked"</c:if>>培训教室
					</dd> --%>
					<dt>说明：</dt>
					<dd>
						<input type="text" name="remark" size="50" value="${mr.remark }">
					</dd>
					<dt>&nbsp;&nbsp;</dt>
					<dd>
						<input type="button" name="sub" value="提交" onClick="check()">
						&nbsp;&nbsp;&nbsp;&nbsp; 
						<input type="reset" value="重置" name="renew">
						<input type="hidden" name="ctrl" value="${ctrl }"> 
						<input type="hidden" name="id"  value="${mr.id }">
						<input type="hidden" name="type" value="2"/>
						<input type="hidden" name="from" value="${from }"/>
					</dd>
				</dl>
			</div>
		</FORM>
		<br style="clear: both;">
	</div>
	<jsp:include page="/index/bottom.jsp" />

</body>
</html>
