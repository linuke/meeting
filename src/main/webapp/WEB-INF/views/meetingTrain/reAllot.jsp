<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>会议通知管理系统</title>
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">
<link Href="<%=path%>/style/new.css" Rel="stylesheet" Type="text/css">
<link Href="<%=path%>/style/ul.css" Rel="stylesheet" Type="text/css">
<script type="text/javascript" src="<%=path%>/script/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/easyui/easyloader.js"></script>
<script type="text/javascript">
easyloader.locale = "zh_CN"; 
var url = window.location.href;
using(['dialog','form','validatebox'],function(){
	$(function(){
		$("#roomid").change(function() {
			if($(this).val() != ''){
				var url = "<%=path%>/MeetingRoomServlet?ctrl=roomDetail&roomID="+$(this).val()+
				"&starttime="+$("#starttime").val()+"&endtime="+$("#endtime").val();
				$.ajax({
					url : url,
					type: "post",
					datatype:"json",
					success : function(data) {
						var d = $.parseJSON(data);
						$("#capacity").html(d.capacity);
						$("#remark").html(d.remark);
						if(d.isFree == '1'){
							$("#msg").html('<font color="red">不可用</font>');
							$("#isFree").val("false");
						}else if (d.isFree == '0'){
							$("#msg").html('<font color="green">可用</font>');
							$("#isFree").val("true"); 
						}
					}
				});
			}
		});
		$('#dlg').dialog({  
		    title: '会议室分配',  
		    closed: true,  
		    cache: false,  
		    modal: true,
		    buttons:[{
				text:'确定',
				handler:function(){
					$("#rn").val($("#child option:selected").text());
					$('#form1').form('submit',{
						onSubmit: function(){
							var isValid = $(this).form('validate');
							if (isValid){
								$('#dlg').dialog('close');	
							}
							return isValid;	
						},
						success:function(data){  
							window.location.href = url;
					    }  
					});
				}
			},{
				text:'关闭',
				handler:function(){
					$('#dlg').dialog('close');
				}
			}]  
		}); 
		$("input[id^=at_]").click(function(){
			var id = $(this).attr('id');
			$("#id").val(id.substring(3,id.lastIndexOf('_')));
			$("#roomid").val(id.substring(id.lastIndexOf('_')+1));
			var childs = $(this).parent().parent().children("td");
			$("#mn").html(childs.eq(1).html());
			var val = childs.eq(0).text();
			var leaders = childs.eq(2).html();
			if(leaders != ''){
				$("#leaders").val(leaders);
				$("#l_t").show();
				$('#l_c').show();
			}
			$("#starttime").val(val.substring(3,19));
			$("#endtime").val(val.substring(19));
			$('#dlg').dialog('open');
		});
		$("#bk").click(function(){
			var url = 'MeetingServlet?ctrl=goBack&id='+$(this).attr('id').substr(3);
			window.location = url;
		});
	});
	$.extend($.fn.validatebox.defaults.rules, {  
	    equals: {  
	        validator: function(value,param){ 
	            return "true" == $(param[0]).val();  
	        },  
	        message: '会议室不可用.'  
	    }  
	});
});

</script>

</head>
<body>
<div class="container" >
		<jsp:include page="/index/top.jsp"/>
			<div style="height:auto; text-align: center;margin: 0 auto;">
				<table class="dtable" align="center" cellpadding="1" cellspacing="1">
					<tr>
						<th width="10%" align="center">日期</th>
						<th width="18%" align="center">会议名称</th>
						<th width="10%" align="center">公司领导</th>
						<th width="12%" align="center">参加单位/部门/人员</th>
						<th width="15%" align="center">有关说明</th>
						<th width="12" align="center">分配会议室</th>
						<th width="12" align="center">预定会议室</th>
						<th width="10%" align="center">申请部门<br />日期</th>
						<th width="4%" align="center">操作</th>
					</tr>
					<c:forEach items="${meetings }" var="m">
						<tr>
							<TD  align="center"><fmt:formatDate value="${m.starttime }" pattern="E" /><br/><fmt:formatDate value="${m.starttime }" pattern="yyyy-MM-dd HH:mm" /><br/><fmt:formatDate value="${m.endtime }" pattern="yyyy-MM-dd HH:mm" /></TD>
							<TD  align="center">${m.content }</TD>
							<TD  align="center">${m.leader }</TD>
							<TD  align="center">${m.depart }<br />${m.fdepart}</TD>
							<TD  align="center">${m.remark }</TD>
							<td  align="center">${m.address1 }</td>
							<td  align="center">${m.reserve_address }</td>
							<TD  align="center">${m.commitdepart }<br />
								<fmt:formatDate value="${m.committime }" pattern="yyyy-MM-dd HH:mm" />
							</TD>
							<TD  align="center">
								<input class="btn" type="button" id="at_${m.id }_${m.roomid}" value="${btn }">
								<input class="btn" type="button" id="bk_${m.id }" value="退回" >
							</TD>
						</tr>
					</c:forEach>
				</table>
			</div>
	</div>
	<div id="dlg" style="text-align: center;vertical-align: middle;margin: 0 auto;width: 400px;height: 215px;overflow: hidden;">
		<form id="form1" action="MeetingServlet" method="post">
			<dl class="dl-table">
				<dt>会议名称：</dt>
				<dd><label id="mn"></label></dd>
				<dt>分配会议室：</dt>
				<dd>
					<select id="roomid" name="roomID" class="easyui-validatebox" data-options="required:true">
						<option value="">请选择会议室</option>
						<c:forEach items="${mrs }" var="mr">
							<option value="${mr.id }">${mr.building }${mr.room }</option>
						</c:forEach>
					</select>
					&nbsp;&nbsp;<span id="msg"></span>
				</dd>
				<dt>容纳人数：</dt>
				<dd><label id="capacity">&nbsp;</label></dd>
				<dt>说明：</dt>
				<dd><label id="remark">&nbsp;</label></dd>
				<dt id="l_t" style="display: none;">提醒：</dt>
				<dd id="l_c" style="display: none;">
					<select id="" name="isemail">
						<option value="true">是</option>
						<option value="false" selected="selected">否</option>
					</select>&nbsp;&nbsp;
					<em>是否给领导发送提醒</em>
				</dd>
			</dl>			
			<input type="hidden" name="ctrl" value="allotRoom">
			<input type="hidden" id="isFree" value="false">
			<input type="hidden" name="leaders" id="leaders" value="">
			<input type="hidden" name="building" id="bn" value="">
			<input type="hidden" name="roomname" id="rn" value="">
			<input type="hidden" name="leaders" id="leaders" value="">
			<input type="hidden" id="starttime" value="">
			<input type="hidden" id="endtime" value="">
			<input type="hidden" name="id" id="id" value="">
		</form>
	</div>
	<jsp:include page="/index/bottom.jsp"/>
</body>