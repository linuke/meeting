<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../include.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>会议通知管理系统</title>
<meta Name="generator" Content="Microsoft FrontPage 4.0">
<meta Name="author" Content="">
<meta Name="keywords" Content="">
<meta Name="description" Content="">
<link Href="<%=basePath%>/style/new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<SCRIPT type="text/javascript">
<!--
	function add() {
		visitForm.ctrl.value = "toAdd";
		visitForm.action = "MeetingPlanServlet";
		visitForm.submit();
	}

	function modify() {
		var i = 0;
		for ( var j = 0; j < visitForm.elements.length; j++) {
			if (visitForm.elements[j].name == "id") {
				if (visitForm.elements[j].checked) {
					i++;
				}
			}
		}
		if (i == 0) {
			alert("提示：请选择修改项！");
			return false;
		} else if (i > 1) {
			alert("提示：每次只能修改一条记录！");
			return false;
		} else {
			visitForm.ctrl.value = "toUpdate";
			visitForm.action = "MeetingPlanServlet";
			visitForm.submit();
		}
	}

	function toMeeting() {
		var i = 0;
		for ( var j = 0; j < visitForm.elements.length; j++) {
			if (visitForm.elements[j].name == "id") {
				if (visitForm.elements[j].checked) {
					i++;
				}
			}
		}
		if (i == 0) {
			alert("提示：请选择转项！");
			return false;
		} else if (i > 1) {
			alert("提示：每次只能转一条记录！");
			return false;
		} else {
			visitForm.ctrl.value = "toMeeting";
			visitForm.action = "MeetingPlanServlet";
			visitForm.tmdisabled = "disabled";
			visitForm.submit();
		}
	}

	function del() {
		if (confirm("提示：将删除选中的信息？")) {
			var i = 0;
			for ( var j = 0; j < visitForm.elements.length; j++) {
				if (visitForm.elements[j].name == "id") {
					if (visitForm.elements[j].checked) {
						i++;
					}
				}
			}
			if (i == 0) {
				alert("提示：请选择删除项！");
				return false;
			} else {
				visitForm.ctrl.value = "del";
				visitForm.action = "MeetingPlanServlet";
				visitForm.submit();
			}
		}
	}
//-->
</SCRIPT>
</head>
<body>
	<div class="container">
		<jsp:include page="/index/top.jsp" />
		<form name="visitForm" method="post" action="">
			<div style="height: auto; text-align: center; margin: 0 auto;">
					<table class="dtable" align="center" cellpadding="1" cellspacing="1">
						<tr>
							<th width="3%" align="center">选中</th>
							<th width="10%" align="center">日期</th>
							<th width="20%" align="center">会议名称</th>
							<th width="10%" align="center">主持人</th>
							<th width="10%" align="center">
								<p align="center">公司领导
							</th>
							<th width="15%" align="center">参加单位/人员</th>
							<th width="15%" align="center">有关说明</th>
							<th width="12%" align="center">发布人<br />日期
							</th>
							<th width="5%" align="center">正式<br />会议
							</th>
						</TR>
						<c:forEach items="${mps }" var="mp">
							<tr>
								<TD width="3%" align="center"><input type="checkbox" name="id" value="${mp.id }"
									<c:if test="${!empty mp.meetingid  }">disabled="disabled"</c:if> /></TD>
								<TD width="10%" align="center"><fmt:formatDate value="${mp.starttime }" pattern="E" /><br />
								<fmt:formatDate value="${mp.starttime }" pattern="yyyy-MM-dd HH:mm" /><br />
								<fmt:formatDate value="${mp.endtime }" pattern="yyyy-MM-dd HH:mm" /></TD>
								<TD width="20%" align="center">${mp.content }</TD>
								<TD width="10%" align="center">${mp.presider }</TD>
								<TD width="10%" align="center">
									<p align="center">${mp.leader }
								</TD>
								<TD width="15%" align="center">${mp.depart }<br />${mp.fdepart }
								</TD>
								<TD width="15%" align="center">${mp.remark }</TD>
								<TD width="12%" align="center">${mp.commiterid }<br />
								<fmt:formatDate value="${mp.creattime }" pattern="yyyy-MM-dd HH:mm" /></TD>
								<td width="5%" align="center"><c:if test="${!empty mp.meetingid  }">
										<font color="green">是</font>
									</c:if>
									<c:if test="${empty mp.meetingid  }">
										<font color="red">否</font>
									</c:if></td>
							</tr>
						</c:forEach>

						<tr bgcolor="#FFFFFF">
							<TD align="center" colspan="9">
								<p align="center">
									<input type="button" value="添加" name="addBot" onclick="add();">&nbsp; &nbsp; <input
										type="button" value="修改" name="modifyBot" onclick="modify();">&nbsp; &nbsp; <INPUT
										TYPE="button" value=" 删除 " name="deleteBot" onclick="del();">&nbsp;&nbsp;&nbsp; <input
										type="button" value="转为正式会议" id="tm" name="tm" onclick="toMeeting();">
								</p>
							</TD>
						</tr>
					</table>
					</table>
					<input type="hidden" name="ctrl" value="">
			</div>
		</form>
	</div>
	<br class="clear"/>
	<jsp:include page="/index/bottom.jsp" />
</body>
</html>
