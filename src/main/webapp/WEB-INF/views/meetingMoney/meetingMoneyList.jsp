<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="../../../WEB-INF/c.tld"%>
<%@taglib prefix="fn" uri="../../../WEB-INF/fn.tld"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
		    + request.getServerName() + ":" + request.getServerPort()
		    + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>会议通知管理系统</title>
<link Href="<%=basePath %>style/new.css" Rel="stylesheet" Type="text/css">
<link rel="stylesheet"
	href="<%=basePath%>style/jquery-ui/base/jquery.ui.all.css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<script src="<%=basePath %>script/jquery-1.9.1.min.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.core.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.widget.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.mouse.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.button.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.draggable.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.position.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.resizable.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.button.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.dialog.js"></script>
<script src="<%=basePath%>script/ui/jquery.ui.effect.js"></script>
<script type="text/javascript">
$(function(){
	var money = $("#money"), allFields = $([]).add(money), tips = $(".validateTips");

	function updateTips(t) {
	tips.text(t).addClass("ui-state-highlight");
	setTimeout(function() {
		tips.removeClass("ui-state-highlight", 1500);
	}, 500);
	}
	
	function checkLength(o) {
	if (o.val().length <= 0) {
		o.addClass("ui-state-error");
		updateTips("预算金额不能为空！");
		return false;
	} else {
		return true;
	}
	}
	
	$("#dialog-form").dialog({
	autoOpen : false,
	height : 250,
	width : 300,
	modal : true,
	buttons : {
		"确定" : function() {
			var bValid = true;
			allFields.removeClass("ui-state-error");
			bValid = bValid && checkLength(money);
			if (bValid) {
				$("#form1").submit();
				$(this).dialog("close");
			}
		},
		"取消" : function() {
			$(this).dialog("close");
		}
	},
	close : function() {
		allFields.val("").removeClass("ui-state-error");
	}
	});
	$( "#dialog-message" ).dialog({
		modal: true,
		autoOpen : false,
		height : 150,
		width : 50,
		buttons: {
			Ok: function() {
				$( this).dialog( "close" );
			}
		}
	});
	
	$("#tj").height($(window).height()*0.77 -$("#data").height());
	var msg = '${msg}';
	if(msg == '1'){
		$("#msg").text("操作成功!");
		$( "#dialog-message" ).dialog("open");
	}else if(msg == '0'){
		$("#msg").text("操作失败!");
		$( "#dialog-message" ).dialog("open");
	}
});
function update(id,orgname,year,money){
	$("#orgname").html(orgname);
	$("#year").html(year);
	$("#id").val(id);
	$("#money").val(money);
	$("#ctrl").val("update");
	$("#dialog-form").dialog("open");
}
</script>
<style>
dl{
	padding-left:27%;
}
dt{float:left;font-weight:bold;padding:12px 0 4px;width:50px;}
dd{ text-align:left;height:auto;padding:8px 0;}
#df{
	padding-left: 2px;
}
.df dt{width: 40%;float:left;font-weight:bold;}
.df dd{text-align:left;height:auto;}
</style>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="dialog-form" title="修改会议预算经费">
		<p class="validateTips"></p>
		<form id="form1" name="form1" action="MeetingMoneyServlet" method="post">
			<fieldset>
				<dl class="df">
					<dt >处室</dt>
					<dd ><label id="orgname"></label></dd>
					<dt >年度</dt>
					<dd ><label id="year"></label></dd>
					<dt >预算金额</dt>
					<dd >
						<input id="money" name="money" value="" type="text" size="5"/>(万元)
					</dd>
				</dl>
				<input id="ctrl" name="ctrl" value="update" type="hidden" />
				<input id="id" name="id" type="hidden" value=""/>
			</fieldset>
		</form>
	</div>
	<table width="90%" border="0" align=center cellpadding=0 cellspacing=0
		background="images/topbg.gif" bgcolor=#ffffff>
		<tr>
			<td width="31%" rowspan="2"><img src="images/logo.gif"
				width="210" height="110"></td>
			<td width="69%" height="80">&nbsp;</td>
		</tr>
	</table>
	<div align="center">
		<center>
			<table width="90%" height="77%" border="0" cellpadding="0" id="data"
				cellspacing="0" bgcolor="#ffffff" style="table-layout: fixed">
				<tr>
					<td valign="top">
						<p align="center" class="tabletitle">
							<font size="2"><b>会议经费预算一览</b></font><br/>
						</p>
						<form action="MeetingMoneyServlet" method="post">
							<dl>
								<dd>
								处室&nbsp;&nbsp;
									<select name="org">
										<option value="">----全部----</option>
										<c:forEach items="${orgs }" var="org">
											<option value="${org[0] }">${org[1] }</option>
										</c:forEach>
									</select>&nbsp;&nbsp;
									年度&nbsp;&nbsp;
									<select name="year">
										<option value="">--全部--</option>
										<option value="${year + 1 }">${year + 1 }</option>
										<option value="${year }">${year }</option>
										<option value="${year - 1 }">${year - 1 }</option>
										<option value="${year -2 }">${year -2 }</option>
										<option value="${year -3 }">${year -3 }</option>
										<option value="${year -4 }">${year -4 }</option>
									</select>&nbsp;&nbsp;
									<input id="" name="" value="搜索" type="submit"/>
									<input id="" name="ctrl" value="list" type="hidden"/>
									&nbsp;&nbsp;
								</dd>
							</dl>
						</form>
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
											<th width="5%" align="center">年</th>
											<th width="5%" align="center">序号</th>
											<th width="25%" align="center">处室</th>
											<th width="10%" align="center">经费预算(万元)</th>
											<th width="10%" align="center">操作</th>
											<th width="5%" align="center">序号</th>
											<th width="25%" align="center">会议室</th>
											<th width="10%" align="center">经费预算(万元)</th>
											<th width="10%" align="center">操作</th>
										</tr>
										
											<c:forEach items="${mms }" var="m">
												<tr bordercolor="gray" bordercolordark="white" bgcolor="#ffffff" class="p1">
													<th width="5%" align="center" rowspan="${fn:length(m.value) % 2 == 0 ? fn:length(m.value) /2 : (fn:length(m.value) / 2 +1) }">${m.key }</th>
														<c:forEach items="${m.value }" var="mm" varStatus="i">
															<th width="5%" align="center">${i.count }</th>
															<td width="25%" align="center">${mm.orgname }</td>
															<td width="10%" align="center">${mm.money }	</td>
															<th width="10%" align="center">
																<a href="javascript:update('${mm.id }','${mm.orgname }','${mm.year }','${mm.money }')">修改</a>
															</th>
															<c:if test="${i.count % 2 == 0 }">
																</tr><tr bordercolor="gray" bordercolordark="white" bgcolor="#ffffff" class="p1">
															</c:if>
															<c:if test="${fn:length(m.value) % 2 != 0 && i.count == fn:length(m.value) }">
																<th width="5%" align="center">&nbsp;</th>
																<td width="25%" align="center">&nbsp;</td>
																<td width="10%" align="center">&nbsp;</td>
																<th width="10%" align="center"></th>
															</c:if>
														</c:forEach>
												</tr>
											</c:forEach>
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
	</div>
	<div id="dialog-message" title="提示">
		<p align="center" style="text-align: center; ">
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:20px 7px 20px 0;"></span>
		</p>
		<p id="msg" align="center" style="text-align: center;margin:26px 7px 20px 0;">
		</p>
	</div>
	<p align="center">会议通知管理系统 江苏核电 2004</p>
</body>
</html>