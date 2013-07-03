<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>会议通知管理系统</title>
<link Href="<%=basePath%>style/new.css" Rel="stylesheet" Type="text/css">
<script src="<%=basePath%>script/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>script/jquery-1.9.1.min.js"></script>
<script src="<%=basePath%>script/common.js"></script>
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
</head>
<script type="text/javascript">
	function clearForm(obj){
		$(obj).find(':input').not(':button, :submit, :reset, :hidden').val('')
		.removeAttr('checked').removeAttr('selected');
	}
	function update(url){
		//url2 = window.location.href;
		window.location.href = url/* +"&url="+url2.substring(url2.indexOf('/meeting')).replace(/\&/g,"%26") */;
	}
</script>
<body>
	<div class="container">
		<jsp:include page="/index/top.jsp"></jsp:include>
		<div style="height: auto; text-align: center; margin: 0 auto;">
			<form id="form1" action="MeetingServlet" method="post">
				<label for="roomID">会议室:</label> <select name="_roomID" style="width: 260px;">
					<option value="">请选择会议室</option>
					<c:forEach items="${mrs }" var="mr">
						<option value="${mr.id }">${mr.building }${mr.room }</option>
					</c:forEach>
				</select> &nbsp;&nbsp;&nbsp;&nbsp; <label for="org">部门:</label> <select id="org" name="_org"
					class="Wdate" style="height: 20px;">
					<option value="">-------------</option>
					<c:forEach items="${orgs }" var="o">
						<option value="${o[0] }" <c:if test="${org == o[0] }">selected="selected"</c:if>>${o[1]
							}</option>
					</c:forEach>
				</select> &nbsp;&nbsp;&nbsp;&nbsp; <label for="content">会议类容:</label> <input id="content" name="_content"
					type="text" value="${content }" size="35"><br />
				<br /> <label for="starttime">开始日期:</label> <input class="Wdate" id="st" type="text"
					name="_starttime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'st2\')}'})"
					value="${starttime }" /> <label for="starttime2">&nbsp;&nbsp;至:&nbsp;&nbsp;</label> <input
					class="Wdate" id="st2" type="text" name="_starttime2"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'st\')}'})"
					value="${starttime2 }" /> &nbsp;&nbsp;&nbsp;&nbsp; <label for="endtime">结束日期:</label> <input
					class="Wdate" id="et" type="text" name="_endtime"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'et2\')}'})" value="${endtime }" />
				<label for="endtime2">&nbsp;&nbsp;至:&nbsp;&nbsp;</label> <input class="Wdate" id="et2"
					type="text" name="_endtime2"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'et\')}'})" value="${endtime2 }" />
				&nbsp;&nbsp; <input style="COLOR: #44606B; background-color: #FFFFFF" type="submit" value="搜索">&nbsp;&nbsp;
				<input style="COLOR: #44606B; background-color: #FFFFFF" type="button" value="清空"
					onclick="clearForm(this.form)"> 
					<input type="hidden" name="ctrl" value="list" /> 
					<%-- <input type="hidden" name="from" value="${from }" /> --%>
			</form>
			<br />
		</div>
		<div style="height: auto; text-align: center; margin: 0 auto;">
			<table class="dtable" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th width="10%" align="center">日期</th>
					<th width="10%" align="center">会议名称</th>
					<th width="5%" align="center">主持人</th>
					<th width="10%" align="center">
						<p align="center">公司领导</p>
					</th>
					<th width="10%" align="center">参加单位/人员</th>
					<th width="10%" align="center">有关说明</th>
					<!-- <th width="10%" align="center">预定会议室</th> -->
					<th width="10%" align="center">地点</th>
					<th width="10%" align="center">状态</th>
					<th width="12%" align="center">申请部门<br />日期
					</th>
					<!-- <th width="3%" align="center">操作</th> -->
				</tr>
				<c:forEach items="${meetings }" var="mp">
					<tr>
						<td width="10%" align="center"><fmt:formatDate value="${mp.starttime }" pattern="E" /><br />
							<fmt:formatDate value="${mp.starttime }" pattern="yyyy-MM-dd HH:mm" /><br /> <fmt:formatDate
								value="${mp.endtime }" pattern="yyyy-MM-dd HH:mm" /></td>
						<td width="10%" align="center">${mp.content }</td>
						<td width="5%" align="center">${mp.presider }</td>
						<td width="10%" align="center">
							<p align="center">${mp.leader }</p>
						</td>
						<td width="10%" align="center">${mp.depart }<br />${mp.fdepart
							}
						</td>
						<td width="10%" align="center">${mp.remark }</td>
						<%-- <td width="10%" align="center"><c:if
								test="${empty mp.schedule_roomid  }">
							</c:if> <c:if test="${!empty mp.schedule_roomid  }">${mp.building2 }${mp.room2 }(${mp.capacity2 }人)</c:if>
						</td> --%>
						<td width="10%" align="center"><c:if test="${mp.type =='3' }">${mp.address }</c:if> <c:if
								test="${mp.type !='3' }">${mp.address1 }</c:if></td>
						<td width="10%" align="center"><c:if test="${mp.status =='0' }">
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
						<td width="12%" align="center">${mp.commitdepart }<br /> <fmt:formatDate
								value="${mp.committime }" pattern="yyyy-MM-dd HH:mm" /></td>
						<%-- <td width="3%" align="center"><a
							<c:if test="${from =='mt' }">href="javascript:update('MeetingTrainingServlet?ctrl=toUpdate&show=all&id=${mp.id }')"</c:if>
							<c:if test="${from =='m' }">href="javascript:update('MeetingServlet?ctrl=toUpdate&show=all&id=${mp.id }')"</c:if>>修改</a>
							<a href="javascript:confirmDelete('MeetingServlet?ctrl=del&id=${mp.id }')">删除</a></td> --%>
					</tr>
				</c:forEach>
				<tr>
					<td align="right" height="20px" colspan="11">${tag }</td>
				</tr>
			</table>
		</div>
		<br class="clear"/>
		<br class="clear"/>
	</div>
	<br class="clear"/>
	<jsp:include page="/index/bottom.jsp" />
</body>