<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<link href="<%=basePath%>style/new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="Content-type" content="text/html; Charset=UTF-8">
<link rel="stylesheet"
	href="<%=basePath%>style/jquery-ui/base/jquery.ui.all.css">
<script src="<%=basePath%>script/jquery-1.9.1.min.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.core.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.widget.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.accordion.js"></script>
<style type="">
	body {
		font-size: 9pt;
		text-align: left;
	}
	.accordionContent{
		font-size: 11px;
	}
</style>
<script type="text/javascript">
	$(function() {
		$("#accordion").accordion({
			collapsible : true,
			heightStyle: "accordionContent"
		});
	});
</script>
<title>Insert title here</title>
</head>
<body>
	<div align="center" style=" margin-top:0px; height: 80px;">
		<img src="<%=basePath%>images/logos.gif" alt="  江苏核电有限公司\n会议管理系统">
	</div> 
	<div id="accordion" style="width:98%;">
		<h3>会议提醒</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>BookRoomServlet?ctrl=toBook" target="main">会议室预定</a> </li>
		</ul>
		<h3>部门会议申请</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>MeetingServlet?ctrl=toAdd" target="main">会议申请</a> </li>
			<li><a href="<%=basePath%>MeetingServlet?ctrl=listByDept" target="main">会议维护</a></li>
			<li><a href="<%=basePath%>MeetingPlanServlet?ctrl=toAdd" target="main">计划会议申请</a></li>
			<li><a href="<%=basePath%>MeetingPlanServlet?ctrl=listDepart" target="main">计划会议维护</a></li>
		</ul>
		<h3>培训通知</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>MeetingTrainingServlet?ctrl=toAdd" target="main">培训通知申请</a> </li>
			<li><a href="<%=basePath%>MeetingTrainingServlet?ctrl=listByDepart" target="main">培训通知维护</a> </li>
		</ul>
		<h3>办公室审批</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>MeetingServlet?ctrl=approve" target="main">会议审批</a></li>
			<li><a href="<%=basePath%>MeetingTrainingServlet?ctrl=approve" target="main">培训通知审批</a></li>
		</ul>
		<h3>会议室分配</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>MeetingServlet?ctrl=toAllot" target="main">会议室分配</a> </li>
			<li><a href="<%=basePath%>MeetingServlet?ctrl=toReAllot" target="main">会议室调配</a> </li>
			<li><a href="<%=basePath%>MeetingTrainingServlet?ctrl=toReAllot" target="main">培训通知教室调配</a> </li>
		</ul>
		<h3>会议浏览修改</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>MeetingServlet?ctrl=listMeeting&title=1&type=2" target="main">最新会议</a> </li>
			<li><a href="<%=basePath%>MeetingServlet?ctrl=listMeeting&title=2&type=2" target="main">历史会议</a></li>
			<li><a href="<%=basePath%>MeetingServlet?ctrl=listMeeting&title=3&type=1" target="main">最新例会</a></li>
			<li><a href="<%=basePath%>MeetingServlet?ctrl=listMeeting&title=4&type=1" target="main">历史例会</a></li>
			<li><a href="<%=basePath%>MeetingServlet?ctrl=listMeeting&title=5&type=3" target="main">最新外部会议</a> </li>
			<li><a href="<%=basePath%>MeetingServlet?ctrl=listMeeting&title=6&type=3" target="main">历史外部会议</a></li>
		</ul>
		<h3>培训通知浏览修改</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>MeetingTrainingServlet?ctrl=toQuery" target="main">培训通知查询</a> </li>
		</ul>
		<h3>会议室维护</h3>
		<ul style="list-style: none;" class="accordionContent">
			<%-- <li><a href="<%=basePath%>MeetingRoom2Servlet?ctrl=listParent" target="main">会议室维护</a> </li> --%>
			<li><a href="<%=basePath%>MeetingRoomServlet?ctrl=toAdd" target="main">会议室添加</a></li>
			<li><a href="<%=basePath%>MeetingRoomServlet?ctrl=toAddClassRoom" target="main">培训教室添加</a></li>
			<li><a href="<%=basePath%>MeetingRoomServlet?ctrl=list&from=mr" target="main">会议室维护</a></li>
			<li><a href="<%=basePath%>MeetingRoomServlet?ctrl=listClassRoom&from=cr" target="main">培训教室维护</a></li>
		</ul>
		<%-- <h3>例会模板维护</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>RegularMeetingTemplateServlet?ctrl=add" target="main">例会模板添加</a> </li> 
			<li><a href="<%=basePath%>RegularMeetingTemplateServlet?ctrl=qry" target="main">例会模板维护</a></li>
		</ul> --%>
		<h3>会议查询</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>MeetingServlet?ctrl=list" target="main">会议查询</a></li>
			<li><a href="<%=basePath%>MeetingServlet?ctrl=list&from=mt" target="main">培训通知查询</a></li>
		</ul>
		<h3>会议室查询</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>MeetingServlet?ctrl=toMeetingRoomQry" target="main">会议室查询</a> </li>
		</ul>
		<h3>系统设置</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>MeetingCommonServlet?ctrl=list" target="main">外来单位/固定称谓</a> </li>
			<li><a href="<%=basePath%>MeetingExplainServlet?ctrl=list" target="main">有关说明项浏览</a></li>
		</ul>
		<h3>报表查询</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>StatisticsServlet?ctrl=statistics" target="main">处室情况统计表</a> </li>
			<li><a href="<%=basePath%>StatisticsServlet?ctrl=statistics2" target="main">会议室情况统计表</a></li>
			<li><a href="<%=basePath%>StatisticsServlet?ctrl=count" target="main">会议召开次数统计表</a></li>
			<li><a href="<%=basePath%>StatisticsServlet?ctrl=meetingAndMeetingPlan" target="main">会议计划及会议执行情况</a></li>
			<li><a href="<%=basePath%>export?ctrl=toExport" target="main">会议计划及会议执行情况导出</a></li>
		</ul>
		<h3>会议经费</h3>
		<ul style="list-style: none;" class="accordionContent">
			<li><a href="<%=basePath%>MeetingMoneyServlet?ctrl=toAdd" target="main">会议经费预算录入</a></li> 
			<li><a href="<%=basePath%>MeetingMoneyServlet?ctrl=list" target="main">会议经费预算一览</a></li>
		</ul>
	</div>
	<div style="height: 27px;width: 97%;margin-top: 2px;text-align: center;" class="ui-accordion-header ui-helper-reset ui-state-default ui-corner-all ui-accordion-icons">
		<a href="<%=basePath%>Logout" style="vertical-align: middle;"><!-- <img src="images/quit.gif" alt="" border="0" height="20px;" /> --><font size="4" ><b>退出</b></font></a>
	</div>
</body>
</html>