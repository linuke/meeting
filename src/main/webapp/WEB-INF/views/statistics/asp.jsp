<!-- 
	公司各处室月度会议计划及会议执行情况汇总
 -->
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../include.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>会议通知管理系统</title>
<link Href="<%=path%>/style/new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<script src="<%=basePath%>script/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		
		
	var date = new Date();
	var year = date.getFullYear();
	for ( var i = year - 4; i < year + 4; i++) {
		if (i == year) {
			$("#year").append('<option value="'+i+'" selected="selected">' + i+ '</option>');
		} else {
			$("#year").append('<option value="'+i+'">' + i+ '</option>');
		}
	}
	var month = date.getMonth() + 1;
	for ( var i = 1; i <= 12; i++) {
		if (i == month) {
			$("#month").append(
					'<option value="'+i+'" selected="selected">' + i
							+ '</option>');
		} else {
			$("#month").append('<option value="'+i+'">' + i + '</option>');
		}
	}

		$('#charat').highcharts(
				{
					data : {
						table : document.getElementById('datatable')
					},
					chart : {
						type : 'column'
					},
					title : {
						text : '公司各处室月度会议计划及会议执行情况'
					},
					yAxis : {
						allowDecimals : false,
						title : {
							text : '次数'
						}
					},

					tooltip : {
						formatter : function() {
							return '<b>' + this.series.name + '</b><br/>'
									+ this.y + ' ' + this.x.toLowerCase();
						}
					},
					credits : {
						enabled : false,
					}
				});
	});
</script>
</head>
<body>
	<script src="<%=basePath%>script/Highcharts/js/highcharts.js"></script>
	<script src="<%=basePath%>script/Highcharts/js/modules/data.js"></script>
	<script type="text/javascript" src="<%=basePath%>script/Highcharts/js/themes/grid.js"></script>
	<div class="container">
		<jsp:include page="/index/top.jsp" />
		<div>
			
			<div>
				<form action="<%=basePath%>MeetingServlet?ctrl=meetingAndMeetingPlan" method="post">
					<select id="year" name="year">
					</select>年
					<select id="month" name="month">
					</select>月
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="搜索" />
				</form>
			</div>
		
			<div id="charat" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
			<table id="datatable" style="display: none;">
				<thead>
					<tr>
						<th></th>
						<th>实际会议次数</th>
						<th>计划会议次数</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="r" items="${result }">
						<tr>
							<th>${r.key }</th>
							<c:forEach var="c" items="${r.value }">
								<td>${c }</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<jsp:include page="/index/bottom.jsp" />
</body>
</html>