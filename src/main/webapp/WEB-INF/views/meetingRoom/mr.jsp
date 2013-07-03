<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=path%>/style/new.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/style/ul.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path%>/script/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/easyui/easyloader.js"></script>
<script type="text/javascript">
	var url = '<%=path%>/MeetingRoom2Servlet?ctrl=listChild&pid=';
	function dynamicTbl(pid){
		var dtable = $("#dtable");
		$.ajax({
			url : url+pid,
			type : "post",
			contentType : "pplication/x-www-form-urlencoded; charset=utf-8",
			dataType : "json",
			success : function(data) {
				dtable.find("tr:not(:first)").remove();
				$.each(data,function(index,item){
					var tr = "<tr>";
					var No = "<td style='widtd:5%'>"+(index+1)+"</td>";
					var roomname = "<td style='widtd:25%'>"+item.roomname+"</td>";
					var capacity = "<td style='widtd:15%'>"+(item.capacity == undefined ? "" : item.capacity)+"</td>";
					var remark = "<td style='widtd:35%'>"+(item.remark == undefined ? "" : item.remark)+"</td>";
					var oper = "<td style='widtd:15%'>";
					var edit = "<a href=\"javascript:update('"+item.id+"','"+pid+"','"+item.type+"','"+item.roomname+"','"+(item.capacity == undefined ? "" : item.capacity)+"','"+(item.remark == undefined ? "" : item.remark)+"');\">修改</a>&nbsp;&nbsp;";
					var del = "<a href='javascript:confirmDelete(\"<%=path%>/MeetingRoom2Servlet?ctrl=del&id="+item.id+"&pid="+pid+"\")'>删除</a>";
					var end = "</td></tr>";
					$(tr+No+roomname+capacity+remark+oper+edit+del+end).insertAfter(dtable.find("tr:eq("+index+")"));
				});
			}
		});
	}
	using('form');
	easyloader.locale = "zh_CN"; 
    function update(id,parentid,type,roomname,capacity,remark){
        $("#id").val(id);
        $("#pid").val(parentid);
        $("#type").val(type);
        $("#roomname").val(roomname);
        $("#capacity").val(capacity);
        $("#remark").val(remark);
        $("#ctrl").val('update');
        $("#dialog").dialog('open');
    }
    function addroom(id,type){
    	$("#pid").val(id);
        $("#type").val(type);
        $("#roomname").val("");
        $("#ctrl").val('add');
        $("#dialog").dialog('open');
    }
	$(function(){
		$("#pid").change(function(){
			var type = $(this).find('option:selected').attr('t');
			$("#type").val(type);
		});
	});
</script>

<title>Insert title here</title>
</head>
<body>
	<div class="container" >
		<jsp:include page="/index/top.jsp"></jsp:include>
		<div style="text-align: left;float: left;margin:20px 25px;width: 200px;">
			<a href="javascript:addroom('','1');" class="easyui-linkbutton">添加建筑</a>
			<ul id="tt" class="easyui-tree" style="float: left;width: 200px;">
				<li>
					<span><a href="javascript:dynamicTbl('');">建筑名称</a></span>
					<c:forEach items="${pmrs }" var="mr">
						<ul style="text-align: left !important;">
							<li style="text-align: left !important;">
								<a href="javascript:dynamicTbl(${mr.id })">${mr.roomname }</a> 
								<a href="javascript:addroom('${mr.id }','${mr.type }');">添加房间</a> 
							</li>
						</ul>
					</c:forEach>
				</li>
			</ul>
		</div>
		<div style="position:relative; margin-top:20px;margin-left:200px; width:82%;min-height:400px;height:auto;">
			<table id="dtable" class="dtable" cellpadding="0" cellspacing="1">
				<tr>
					<th style="width:5%">序号</th>
					<th style="width:35%">建筑/房间名称</th>
					<th style="width:15%">容纳人数</th>
					<th style="width:30%">说明</th>
					<th style="width:15%">操作</th>
				</tr>
				<c:forEach var="mr" items="${cmrs }" varStatus="i">
					<tr >	
						<td style="widtd:5%">${i.count }</td>
						<td style="widtd:30%">${mr.roomname }</td>
						<td style="widtd:15%">${mr.capacity }</td>
						<td style="widtd:35%">${mr.remark }</td>
						<td style="widtd:20%">
							<a href="javascript:update('${mr.id }','${mr.parentid }','${mr.type }','${mr.roomname }','${mr.capacity }','${mr.remark }');">修改</a>&nbsp;&nbsp;
							<a href="javascript:confirmDelete('<%=path%>/MeetingRoom2Servlet?ctrl=del&id=${mr.id}&pid=${mr.parentid}')">删除</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div style="clear: both;"></div>
	</div>
	<jsp:include page="/index/bottom.jsp"></jsp:include>
	<div id="dialog" title="添加/修改会议室" class="easyui-dialog" style="width: 400px;height: 320px;" data-options="
		 closed:true,
		 buttons:[{
				text:'提交',
				handler:function(){
					$('#form1').form('submit');
					$('#dialog').dialog('close');
				}
			},{
				text:'关闭',
				handler:function(){
					$('#dialog').dialog('close');
				}
			}] ">
		<form action="<%=path %>/MeetingRoom2Servlet" method="post" id="form1">
			<dl class="dl-table">
				<dt>建筑名称:</dt>
				<dd>
					<select id="pid" name="pid" style="width:100px;">
						<option t="1">建筑名称</option>
						<c:forEach items="${pmrs }" var="mr">
							<option value="${mr.id }" t="${mr.type }">${mr.roomname }</option>
						</c:forEach>
					</select>
				</dd>
				<dt>房间名称:</dt>
				<dd>
					<input id="roomname" style="font-size: 11pt;margin-top:1px; height: 20px;" name="roomname" class="easyui-validatebox" data-options="required:true" />
					<br/><font color="red" size="1">当建筑名称选择为建筑名称时,输入房间名称.否则输入建筑名称!</font>
				</dd>
				<dt>容纳人数:</dt>
				<dd>
					<input id="capacity" style="font-size: 11pt;margin-top:1px; height: 20px;" name="capacity" class="easyui-numberbox" data-options="precision:0" />
					</dd>
				<dt>会议室类型:</dt>
				<dd>
					<select id="type" name="type">
						<option value="1">会议室</option>
						<option value="2">培训教室</option>
					</select>
				</dd>
				<dt>有关说明:</dt>
				<dd>
					<textarea id="remark" name="remark" style="resize:none;height:70px; width: 200px;" class="easyui-validatebox"></textarea>
				</dd>
			</dl>
				<input id="ctrl" name="ctrl" type="hidden" value=""/>
				<input id="id" name="id" type="hidden" value=""/>
		</form>
	</div>
</body>
</html>