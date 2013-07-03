<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../include.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<title>会议通知管理系统</title>
<link Href="<%=path %>/style/new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<link rel="stylesheet"
	href="<%=basePath%>style/jquery-ui/base/jquery.ui.all.css">
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
	input { display:block;padding: 0.3em; }
	input.text { margin-bottom:12px; width:95%; padding: .4em; }
	h1 { font-size: 1.2em; margin: .6em 0; }
	.ui-dialog .ui-state-error { padding: .3em; }
	.validateTips { border: 1px solid transparent; padding: 0.3em; }
	#table {
   	 	width:265px;
   	 	margin:0 auto;
	}
	#table li{
	    list-style-type:none;
	    height:30px;
	    line-height:30px;
	    float:left;
	    margin-left:1px;
	    margin-bottom:1px;
	   	background:#ccc;
	}
	#table li.right{
		text-align:right;
		width:70px;
		padding-right: 2px;
	}
	#table li.left{
		text-align:left;
		width:180px;
		padding-left: 2px;
	}
</style>
<script type="text/javascript">
$(function(){
	var name = $("#name"), style = $("#style"),unit = $("#unit"),display = $("#display"), allFields = $([]).add(name)
	.add(style).add(unit).add(display), tips = $(".validateTips");
	function updateTips(t) {
		tips.text(t).addClass("ui-state-highlight");
		setTimeout(function() {
			tips.removeClass("ui-state-highlight", 1500);
		}, 500);
	}

	function checkLength(o) {
	if (o.val().length <= 0) {
		o.addClass("ui-state-error");
		setTimeout(function() {
			o.removeClass("ui-state-error", 1500);
		}, 500);
		updateTips("名称不能为空！");
		return false;
	} else {
		return true;
	}
	}

	$("#dialog-form").dialog({
	autoOpen : false,
	height : 320,
	width : 300,
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
	$("#add").click(function() {
		$("#name").val("");
		$("#style").attr("disabled",false);
		$("#unit").attr("disabled",false);
		$("#id").val("");
		$("#ctrl").val("add");
		$("#dialog-form").dialog("open");
	});
	
	$("#tj").height($(window).height()*0.80 -$("#data").height());//调整页面高度
	
	var msg = '${msg}';
	if(msg == '1'){
		$("#msg").text("操作成功!");
		$( "#dialog-message" ).dialog("open");
	}else if(msg == '0'){
		$("#msg").text("操作失败!");
		$( "#dialog-message" ).dialog("open");
	}
	
});
function update(id,name,style,unit,display){
	$("#name").val(name);
	$("#style").val(style);
	$("#unit").val(unit).attr("disabled",false);
	$("#display").val(display);
	$("#id").val(id);
	$("#ctrl").val("update");
	$("#dialog-form").dialog("open");
}

function addChild(id,name,display){
	$("#pname").html(name);
	$("#style").attr("disabled",true);
	$("#unit").attr("disabled",true);
	$("#display").val(display);
	$("#parentid").val(id);
	$("#ctrl").val("add");
	$("#dialog-form").dialog("open");
}

function updateChild(id,pname,name,display){
	$("#pname").html(pname);
	$("#name").val(name);
	$("#style").attr("disabled",true);
	$("#unit").attr("disabled",true);
	$("#display").val(display);
	$("#id").val(id);
	$("#ctrl").val("update");
	$("#dialog-form").dialog("open");
}

function expan(obj,id){
	var title = $(obj).attr("title");
	if(title == "展开"){
		$("tr[id^=expan_"+id+"_]").show();
		$(obj).attr("src","images/datagrid_row_collapse.gif");
		$(obj).attr("title","收缩");
	}else {
		$("tr[id^=expan_"+id+"_]").hide();
		$(obj).attr("src","images/datagrid_row_expand.gif");
		$(obj).attr("title","展开");
	}
}
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
							<font size="2"><b>${room }会议有关说明项目一览</b></font><br/><br/>
							<input style="COLOR:#44606B;background-color:#FFFFFF;" id="add"	name="" type="button" value="增加" />
							<br/>
						</p>
					</td>
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
											<th width="5%"></th>
											<th width="5%" align="center">序号</th>
											<th width="20%" align="center">显示名称</th>
											<th width="10%" align="center">显示样式</th>
											<th width="5%" align="center">单位</th>
											<th width="10%" align="center">是否显示</th>
											<th width="20%" align="center">操作</th>
										</tr>
										<c:forEach items="${mes }" var="me" varStatus="i">
											<tr bordercolor="gray" bordercolordark="white" bgcolor="#ffffff" class="p1">
												<th width="5%">
													<c:if test="${me.style == 3 && fn:length(me.child) > 0}">
														<img title="展开" alt="" src="images/datagrid_row_expand.gif" onclick="expan(this,${me.id})"/>
													</c:if>
												</th>
												<th width="5%" align="center">${i.count }</th>
												<td width="20%" align="center">${me.name }</td>
												<td width="10%" align="center">
													<c:if test="${me.style == 1 }">复选框</c:if>
													<c:if test="${me.style == 2 }">文本框</c:if>
													<c:if test="${me.style == 3 }">下拉列表</c:if>
												</td>
												<td width="5%" align="center">${me.unit }</td>
												<td width="10%" align="center">
													<c:if test="${me.display == 0 }">隐藏</c:if>
													<c:if test="${me.display == 1 }">显示</c:if>
												</td>
												<td width="20%" align="center">
													<c:if test="${me.style == 3 }">
														<a href="javascript:addChild('${me.id }','${me.name }','${me.style }','${me.unit }','${me.display }')">添加子项</a>
													</c:if>
													<a href="javascript:update('${me.id }','${me.name }','${me.style }','${me.unit }','${me.display }')">修改</a>
													<a href="MeetingExplainServlet?ctrl=del&id=${me.id }">删除</a>
												</td>
											</tr>
											<c:if test="${me.style == 3 }">
												<c:forEach var="m" items="${me.child }" varStatus="n">
													<tr id="expan_${me.id }_${m.id}" bordercolor="gray" bordercolordark="white" bgcolor="#ffffff" class="p1" style="display: none;">
														<th width="5%"></th>
														<td width="5%" align="right">${n.count }</td>
														<td width="20%" align="center">${m.name }</td>
														<td width="10%" align="center"></td>
													<td width="5%" align="center">${m.unit }</td>
														<td width="10%" align="center">
															<c:if test="${m.display == 0 }">隐藏</c:if>
															<c:if test="${m.display == 1 }">显示</c:if>
														</td>
														<td width="20%" align="center">
															<a href="javascript:updateChild('${m.id }','${me.name }','${m.name }','${m.unit }','${me.display }')">修改</a>
															<a href="MeetingExplainServlet?ctrl=del&id=${m.id }">删除</a>
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</c:forEach>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td bgcolor="#FFFFFF">
						<div id="tj"></div><!-- 调节页面高度 -->
					</td>
				</tr>
				<tr height="20px">
				</tr>
			</table>
		</center>
	</div>
	<div id="dialog-form" title="会议有关说明设置">
		<p class="validateTips"></p>
		<form id="form1" name="form1" action="MeetingExplainServlet" method="post">
			<ul id="table">
				<li class="right">父名称</li>
				<li class="left" id="pname"></li>
				<li class="right">名称</li>
				<li class="left"><input type="text" name="name" id="name" /></li>
				<li class="right">样式</li>
				<li class="left">
					<select id="style" name="style">
						<option value="1">复选框</option>
						<option value="2">文本框</option>
						<option value="3">下拉列表</option>
					</select>
				</li>
				<li class="right">单位</li>
				<li class="left"><input type="text" name="unit" id="unit" /></li>
				<li class="right">是否显示</li>
				<li class="left">
					<select id="display" name="display">
						<option value="1">显示</option>
						<option value="0">隐藏</option>
					</select> 
				</li>
			</ul>
				<input id="ctrl" name="ctrl" value="add" type="hidden" />
				<input id="id" name="id" type="hidden"/>
				<input id="parentid" name="parentid" type="hidden"/>
		</form>
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