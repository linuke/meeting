<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>会议通知管理系统</title>
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<link href="<%=path %>/style/new.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="<%=basePath%>style/jquery-ui/base/jquery.ui.all.css"/>
<script src="<%=basePath%>script/jquery-1.9.1.min.js"></script>
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
<style>
		label, input { display:block; }
		input.text { margin-bottom:12px; width:95%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
</style>
<script type="text/javascript">
	$(function() {
		var name = $("#name"), type = $("#type"), allFields = $([]).add(name)
				.add(type), tips = $(".validateTips");

		function updateTips(t) {
			tips.text(t).addClass("ui-state-highlight");
			setTimeout(function() {
				tips.removeClass("ui-state-highlight", 1500);
			}, 500);
		}

		function checkLength(o) {
			if (o.val().length <= 0) {
				o.addClass("ui-state-error");
				updateTips("名称不能为空！");
				return false;
			} else {
				return true;
			}
		}

		$("#dialog-form").dialog({
			autoOpen : false,
			height : 270,
			width : 250,
			modal : true,
			buttons : {
				"确定" : function() {
					var bValid = true;
					allFields.removeClass("ui-state-error");
					bValid = bValid && checkLength(name);
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

		$("#add").click(function() {
			$("#name").val("");
			$("#type").attr("disabled",false);
			$("#id").val("");
			$("#ctrl").val("add");
			$("#dialog-form").dialog("open");
		});
		$("#tj").height($(window).height()*0.77 -$("#data").height());
		
	});
	function click(id,title,type){
		$("#name").val(title);
		$("#type").val(title).attr("disabled",true);
		$("#id").val(id);
		$("#ctrl").val("update");
		$("#dialog-form").dialog("open");
	}
</script>
</head>
<body Leftmargin="0" Topmargin="0" Marginwidth="0" Marginheight="0">
	<div id="dialog-form" title="固定称谓/外来单位">
		<p class="validateTips"></p>
		<form id="form1" name="form1" action="MeetingCommonServlet" method="post">
			<fieldset>
				<label for="name">名称</label> <input type="text" name="name"
					id="name" /> <label for="type">类型</label> <select id="type" 
					name="type">
					<option value="1">固定称谓</option>
					<option value="2">外来单位</option>
				</select> 
				<input id="ctrl" name="ctrl" value="add" type="hidden" />
				<input id="id" name="id" type="hidden"/>
			</fieldset>
		</form>
	</div>
	<table Width="870" Border=0 Align=center Cellpadding=0 Cellspacing=0
		Background="images/topbg.gif" Bgcolor=#ffffff>
		<tr>
			<td Width="31%" Rowspan="2"><img Src="images/logo.gif"
				width="210" height="110"></td>
			<td Width="69%" Height="80">&nbsp;</td>
		</tr>
	</table>
	<div align="center">
		<center>
			<table Width="870" Height="77%" Border="0" Cellpadding="0" id="data"
				Cellspacing="0" Bgcolor="#ffffff" Style="table-layout: Fixed">
				<tr>
					<td Valign="top">
						<p align="center" class="tabletitle">
							<font size="2"><b>固定称谓/外来单位浏览</b></font><br /> 
							<input style="COLOR:#44606B;background-color:#FFFFFF" id="add"	name="" type="button" value="增加" />
						</p>

						<table width="96%" border="0" align="center" cellpadding="0"
							cellspacing="0" bgcolor="#698cc3">
							<tr>
								<td>
									<table width="100%" border="0" cellpadding="1" cellspacing="1">
										<tr bordercolor="gray" bordercolordark="white"
											bgcolor="#FFFFFF" class="P1">
											<td width="5%" align="center">序号</td>
											<td width="50%" align="center">名称</td>
											<td width="20%" align="center">类别</td>
											<td width="25%" align="center">操作</td>
										</tr>
										<c:forEach items="${mcs }" var="mc" varStatus="i">
											<tr bordercolor="gray" bordercolordark="white"
												bgcolor="#ffffff" class="p1">
												<td width="5%" align="center">${i.count }</td>
												<td width="50%" align="center">${mc.title }</td>
												<td width="20%" align="center"><c:if
														test="${mc.type == '1' }">固定称谓</c:if> <c:if
														test="${mc.type == '2' }">外来单位</c:if></td>
												<td width="25%" align="center">
													<a href="javascript:click('${mc.id }','${mc.title}','${mc.type }');" >修改</a>
													<a href="MeetingCommonServlet?ctrl=del&id=${mc.id }">删除</a>
												</td>
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<center>
							<p></p>
						</center>
				<tr style="height: 77%">
					<td bgcolor="#FFFFFF">
						<div id="tj"></div><!-- 调节页面高度 -->
					</td>
				</tr>
				<tr height="20px">
				</tr>
			</table>
		</center>
	</div>
	<p align="center">会议通知管理系统 江苏核电 2004</p>
</body>
</html>