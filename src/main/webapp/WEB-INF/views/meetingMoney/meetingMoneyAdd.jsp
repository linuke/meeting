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
<link Href="<%=basePath %>style/new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<script src="<%=basePath %>script/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#tj").height($(window).height()*0.80 -$("#data").height());
	var bValid = true;
	$("#sub").click(function(){
		var json = "[";
		var inputs = $("input[id^=m_]");
		var len = inputs.length;
		inputs.each(function(index){
			var org = $(this).attr("id").substr(2);
			var orgname = $("#org_n_"+org).val();
			var orgsort = $("#org_s_"+org).val();
			var money = $("#m_"+org).val();
			var year = $("#year").val();
			if(money.length == 0){
				if(confirm((orgname+"经费预算还没有填!\n\r确认提交么?"))){
					bValid = true;
				}else {
					bValid = false;
					return false;//跳出循环
				}
			}
			if(index == len - 1){
				json += "{org:'"+org+"',orgname:'"+orgname+"',orgsort:'"+orgsort+"',money:'"+money+"',year:'"+year+"'}]";
			}else {
				json += "{org:'"+org+"',orgname:'"+orgname+"',orgsort:'"+orgsort+"',money:'"+money+"',year:'"+year+"'},";
			}
		});
		if(bValid){
			$("#money").val(json);
			$("#sub").attr("disabled",true);
			$("#form1").submit();
		}
	});
});
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table width="870" border="0" align=center cellpadding=0 cellspacing=0
		background="images/topbg.gif" bgcolor=#ffffff>
		<tr>
			<td width="31%" rowspan="2"><img src="images/logo.gif"
				width="210" height="110"></td>
			<td width="69%" height="80">&nbsp;</td>
		</tr>
	</table>
	<div align="center">
		<center>
			<table width="870" height="77%" border="0" cellpadding="0" id="data"
				cellspacing="0" bgcolor="#ffffff" style="table-layout: fixed">
				<tr>
					<td valign="top">
						<p align="center" class="tabletitle">
							<font size="2"><b>添加 
								<select id= "year" name="year" >
									<option value="${year+1 }">${year+1 }</option>
									<option value="${year }" selected="selected">${year }</option>
									<option value="${year-1 }">${year-1 }</option>
								</select>
							  年各处室会议经费预算</b></font><br/>
						</p>
					</td>
				</tr>
				<tr></tr>
				<tr valign="middle">
				</tr>
				<tr>
					<td valign="top">
						<table width="96%" border="0" align="center" cellpadding="0"
							cellspacing="0" bgcolor="698cc3">
							<tr>
								<td>
								
									<table width="100%" border="0" cellpadding="1" cellspacing="1">
										<tr bordercolor="gray" bordercolordark="white"
											bgcolor="#ffffff" class="p1">
											<th width="5%" align="center">序号</th>
											<th width="27%" align="center">处室</th>
											<th width="15%" align="center">经费预算(万元)</th>
											<th width="5%" align="center">序号</th>
											<th width="27%" align="center">会议室</th>
											<th width="15%" align="center">经费预算(万元)</th>
										</tr>
										<tr bordercolor="gray" bordercolordark="white" bgcolor="#ffffff" class="p1">
											<c:forEach items="${orgs }" var="org" varStatus="i">
												<th width="5%" align="center">${i.count }</th>
												<td width="27%" align="center">${org[1] }</td>
												<td width="15%" align="center">
													<input id="m_${org[0] }" name="" type="text" value="" style="text-align: right;width: 95%;height: 95%"/>
													<input id="org_${org[0] }" value="${org[0] }" type="hidden"/>
													<input id="org_n_${org[0] }" value="${org[1] }" type="hidden" />
													<input id="org_s_${org[0] }" value="${org[2] }" type="hidden" />
												</td>
												<c:if test="${i.count % 2 == 0 && i.count != fn:length(orgs)}">
												</tr><tr bordercolor="gray" bordercolordark="white" bgcolor="#ffffff" class="p1">
												</c:if>
											</c:forEach>
											<c:if test="${fn:length(orgs) % 2 != 0 }">
												<th width="5%" align="center">&nbsp;</th>
												<td width="30%" align="center">&nbsp;</td>
												<td width="10%" align="center">&nbsp;</td>
											</c:if>
										</tr>
										<tr bordercolor="gray" bordercolordark="white" bgcolor="#ffffff" class="p1">
											<td colspan="6"> 
												<input id="sub" type="button" value="提交"/>&nbsp;&nbsp;
												<input id="res" type="reset" value="重置"/>
											</td>
										</tr>
									</table>
									
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td >
						<div id="tj"></div><!-- 调节页面高度 -->
					</td>
				</tr>
				<tr height="20px">
				</tr>
			</table>
		</center>
		<form action="MeetingMoneyServlet" id="form1" method="post">
			<input id="ctrl" name="ctrl" value="add" type="hidden"/>
			<input id="money" name="money" value="" type="hidden"/>
		</form>
	</div>
	<br class="clear"/>
	<p align="center">会议通知管理系统 江苏核电 2004</p>
</body>
</html>