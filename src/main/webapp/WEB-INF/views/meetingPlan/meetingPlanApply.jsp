<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="../include.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<title>会议通知管理系统</title>
<meta http-equiv="content-type" content="text/html; Charset=UTF-8">
<link href="<%=path%>/style/new.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/style/ul.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/style/jquery-ui/base/jquery.ui.all.css" rel="stylesheet">
<script src="<%=path%>/script/My97DatePicker/WdatePicker.js"></script>
<script src="<%=path%>/script/map.js"></script>
<script src="<%=path%>/script/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/easyui/easyloader.js"></script>
<script src="<%=path%>/script/form2Json.js"></script>
<script src="<%=path%>/script/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/script/ui/jquery.ui.widget.js"></script>
<script src="<%=path%>/script/ui/jquery.ui.button.js"></script>
<script src="<%=path%>/script/ui/jquery.ui.position.js"></script>
<script src="<%=path%>/script/ui/jquery.ui.menu.js"></script>
<script src="<%=path%>/script/ui/jquery.ui.autocomplete.js"></script>
<style>
	.ui-autocomplete {
		max-height: 100px;
		overflow-y: auto;
		overflow-x: hidden;
	}
	
	* html .ui-autocomplete {
		height: 100px;
	}
</style>

<script type="text/javascript">
	using('form');
	easyloader.locale = "zh_CN"; 
	$(function() {
		$("#presider").autocomplete({
			minLength : 1,
			delay : 300,
			source : function(request, response) {
				var url = "ajax?ctrl=ens&presider="+ encodeURI(encodeURI($("#presider").val()));
				$.ajax({
					url : url,
					type : "post",
					contentType : "pplication/x-www-form-urlencoded; charset=utf-8",
					dataType : "json",
					success : function(data,
							textStatus, jqXHR) {
						response($.map(data, function(
								item, index) {
							return {
								name : item.name,
								org : item.org
							};
						}));
					}
				});
			},
			focus : function(event, ui) {
				$("#presider").val(ui.item.name + '(' + ui.item.org + ')');
				return false;
			},
			select : function(event, ui) {
				$("#presider").val(ui.item.name + '(' + ui.item.org + ')');
				return false;
			},
			close : function(event, ui) {
				var url = "ajax?ctrl=checkP&presider="+ encodeURI(encodeURI($("#presider").val())) + "&starttime="
						+ $("#st").val() + "&endtime="+ $("#et").val() + "&id="+ $("#id").val();
				$.getJSON(url, function(data) {
					if (data == 1) {
						alert("主持人在该时间段已有会议!");
					}
				});
			}}).data("ui-autocomplete")._renderItem = function(ul,item) {
				return $("<li>").append("<a>" + item.name + " <font size='1.5'color='#3C3C3C'>"+ item.org + "</font></a>").appendTo(ul);
		};

		function split(val) {
			return val.split(/,\s*/);
		}
		function extractLast(term) {
			return split(term).pop();
		}
		$("#depart").bind("keydown",function(event) {
			if (event.keyCode === $.ui.keyCode.TAB && $(this).data("ui-autocomplete").menu.active) {
				event.preventDefault();
			}
		}).autocomplete({
			source : function(request, response) {
				$.getJSON("ajax?ctrl=pu&unit="+ encodeURI(encodeURI(extractLast($("#depart").val()))), response);
			},
			search : function() {
				var term = extractLast(this.value);
				if (term.length < 2) {
					return false;
				}
			},
			focus : function() {
				return false;
			},
			select : function(event, ui) {
				var terms = split(this.value);
				terms.pop();
				terms.push(ui.item.value);
				terms.push("");
				this.value = terms.join(",");
				return false;
			}
		});
		$("#fdepart").bind("keydown",function(event) {
			if (event.keyCode === $.ui.keyCode.TAB && $(this).data("ui-autocomplete").menu.active) {
				event.preventDefault();
			}
		}).autocomplete({
			source : function(request, response) {
				$.getJSON("ajax?ctrl=wu&unit="+ encodeURI(encodeURI($("#fdepart").val())),response);
			},
			search : function() {
				var term = extractLast(this.value);
				if (term.length < 2) {
					return false;
				}
			},
			focus : function() {
				return false;
			},
			select : function(event, ui) {
				var terms = split(this.value);
				terms.pop();
				terms.push(ui.item.value);
				terms.push("");
				this.value = terms.join(",");
				return false;
			}
		});
	});

	function check() {
		var flag = true;
		if (timeForm.content.value == "") {
			alert("请输入会议内容！");
			timeForm.content.focus();
			flag = false;
			return;
		}
		if (timeForm.starttime.value == "") {
			alert("请输入会议开始时间！");
			timeForm.starttime.focus();
			flag = false;
			return;
		}
		if (timeForm.endtime.value == "") {
			alert("请输入会议结束时间！");
			timeForm.endtime.focus();
			flag = false;
			return;
		}

		if (flag) {

			//选中所有的参会领导	
			var x = document.timeForm.leaders;
			for ( var i = 0; i < x.length; i++) {
				x.options[i].selected = true;
			}
			var remark = "";
			remark += $("#remark").val();
			$("input[id^=me_c_]:checked").each(function() {
				remark += $(this).val() + ",";
			});
			$("select[id^=me_s_]").each(function() {
				var val = $(this).val();
				if (val.length > 0) {
					remark += $(this).val() + ",";
				}
			});
			$("input[id^=me_t_]").each(function() {
				var id = $(this).attr("id");
				var val = $(this).val();
				if (val.length > 0) {
					var n = id.substr(5);
					remark += $("#n_me_t_" + n).val() + " " + val+ $("#u_me_t_" + n).val() + ",";
				}
			});
			$("#remark").val(remark);
			timeForm.action = "MeetingServlet";
			if ($("#depart").val() == "内部单位/人员") {
				$("#depart").val("");
			}
			if ($("#fdepart").val() == "外部单位/人员") {
				$("#fdepart").val("");
			}
			if ($("#contact").val() == "联系人") {
				$("#contact").val("");
			}
			if ($("#contactphone").val() == "联系人电话") {
				$("#contactphone").val("");
			}
			$("#sub").attr("disabled", true);
			$("#form1").attr('action','<%=path%>/MeetingPlanServlet?json='+form2json("form"));
			timeForm.submit();
		}
	}

	var l1 = new Map();
	var selLeaders = new Array();
	<c:forEach items="${leaderList}" var="a" varStatus='i'>
	l1.put('${a}', '${i.count}');
	</c:forEach>

	function leaderCheck(source, dest, isAll) {
		var leader = $("#" + source).val();
		var url = "ajax?ctrl=checkL&leader=" + encodeURI(encodeURI(leader))
				+ "&starttime=" + $("#st").val() + "&endtime=" + $("#et").val()
				+ "&id=" + $("#id").val();
		$.getJSON(url, function(data) {
			if (data == 1) {
				flag = true;
				alert("领导:" + leader + "\r\n 在该时间段已有会议!");
			} else {
				select_add(source, dest, isAll);
			}
		});
	}

	function select_add(source, dest, isAll) {
		var m_source = document.getElementById(source);
		var m_dest = document.getElementById(dest);
		var tempLaders = new Map();
		var leader_indexs = new Array();//下标
		for ( var i = 0; i < m_dest.length; i++) {
			var text = m_dest.options[i].text;
			tempLaders.put(l1.get(text), m_dest.options[i].value);
			leader_indexs[i] = l1.get(text);
		}
		m_dest.length = 0;
		for ( var i = 0; i < m_source.length; i++) {
			var current = m_source.options[i];
			if ((!isAll && current.selected == true) || (isAll)) {
				if (!tempLaders.containsKey(l1.get(current.text))) {
					tempLaders.put(l1.get(current.text), current.value);
					leader_indexs[leader_indexs.length] = l1.get(current.text);
				}
				//m_source.remove(i);
				//i--;
			}
		}
		leader_indexs.sort(function(a, b) {
			return a - b;
		});
		// alert(leader_indexs);
		for ( var i = 0; i < leader_indexs.length; i++) {
			var s = "";
			if (!isNaN(leader_indexs[i])) {
				s = tempLaders.get(leader_indexs[i]);
				var newOption = new Option(s, s);
				m_dest.add(newOption, i);
			}
		}
	}
	function del() {
		var x = document.timeForm.leaders;
		for ( var i = x.length - 1; i >= 0; i--) {
			if (x.options[i].selected) {
				x.options[i] = null;
			}
		}
	}

</script>
</head>
<body>
	<div class="container">
		<jsp:include page="/index/top.jsp"/>
		<form id="form1" name="timeForm" action="<%=path%>/MeetingPlanServlet" method="post">
			<div style="width: 950px;height:auto; text-align: center;margin: 0 auto;">
				<dl class="dl-table">
					<dt>会议类型：</dt>
					<dd><input type="radio" value="1" name="type" 
							<c:if test="${meeting.type =='1' || empty meeting.type}">checked="checked"</c:if>>列会
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="2" name="type" 
							<c:if test="${meeting.type =='2' || empty meeting.type}">checked="checked"</c:if>>会议
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" value="3"name="type" 
							<c:if test="${meeting.type =='3'}">checked="checked"</c:if>>外部会议
							<%-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" value="4"name="type" 
							<c:if test="${meeting.type =='4'}">checked="checked"</c:if>>培训通知 --%>
						<div id="lh" style="display: none;">
							<input type="radio" name="lht" value="1" checked="checked"/> 周例会
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="lht" value="2" /> 月例会
						</div>
						<script type="text/javascript">
						$(function(){
							$('input[name=type]').click(function(){
								var val = $(this).val(); 
								if(val == 1){
									$("#lh").show();
									$("#addr_t").hide();
									$("#addr").hide();
									/* $("#_radio").hide();
							 		$("#tr").hide(); */
							 		$("#mr").show();
								}else if(val == 2){
									$("#lh").hide();
									$("#addr_t").hide();
									$("#addr").hide();
									/* $("#_radio").hide();
							 		$("#tr").hide(); */
							 		$("#mr").show();
								}else if(val == 3){
									$("#lh").hide();
									$("#addr_t").show();
									$("#addr").show();
									/* $("#_radio").hide();
							 		$("#tr").hide(); */
							 		$("#mr").show();
								}/* else if(val == 4){
									$("#lh").hide();
									$("#addr_t").hide();
									$("#addr").hide();
									$("#_radio").show();
							 		$("#tr").show();
							 		$("#mr").hide();
								} */
							});
							});  
						</script>
					</dd>
					<dt>会议级别：</dt>
					<dd> 
						<input type="radio" value="1" name="grade"
							<c:if test="${meeting.grade==1 || empty meeting.grade }">checked="checked"</c:if>>综合会议
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" value="2"name="grade"
							<c:if test="${meeting.grade==2 }">checked="checked"</c:if>>处内会议
					</dd>
					<dt>会议类目：</dt>
					<dd>
						<input type="radio" value="1" name="category"
							<c:if test="${meeting.category==1 || empty meeting.category }">checked="checked"</c:if>>通用
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" value="2" name="category"
							<c:if test="${meeting.category==2 }">checked="checked"</c:if>>生产1-2号机组
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    <input type="radio" value="3" name="category"
							<c:if test="${meeting.category==3 }">checked="checked"</c:if>>扩建3-4号机组
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						<input type="radio"value="4" name="category"
							<c:if test="${meeting.category==4 }">checked="checked"</c:if>>扩建5-6号机组
					</dd>
					<dt>会议时间：</dt>
					<dd>
						<input class="Wdate easyui-validatebox" id="st" type="text" style="width: 200px" name="starttime"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:%m',maxDate:'#F{$dp.$D(\'et\',{m:-30})}'})"
							value="<fmt:formatDate value="${meeting.starttime }" pattern="yyyy-MM-dd HH:mm"/>" />
						&nbsp;&nbsp; <font style="font-weight: bold;">至</font>&nbsp;&nbsp;
						<input class="Wdate easyui-validatebox" id="et" type="text" style="width: 200px" name="endtime" 
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'st\')||\'%y-%M-%d %H:%m\'}'})"
							value="<fmt:formatDate value="${meeting.endtime }" pattern="yyyy-MM-dd HH:mm"/>" />
					</dd>
					<dt>主持人：</dt>
					<dd>
						<input class="input" type="text" id="presider" name="presider" size="35" value="${meeting.presider }">
					</dd>
					<dt>会议名称：</dt>
					<dd>
						<textarea rows="3" name="content" style="width: 550px" class="easyui-validatebox" >${meeting.content }</textarea>
					</dd>
					<dt>公司领导：</dt>
					<dd>
						<div style="margin:0 auto; width:100%;">
							<div style="float:left;width: 127px;height: 100px;">
								<select id="selLeaders" multiple size="10" style="width: 127px; height: 97px;">
									<c:forEach items="${leaderList }" var="l">
										<option value="${l }">${l }</option>
									</c:forEach>
								</select>
							</div>
							<div style="float:right;width: 127px;height: 100px;margin-right: 400px;">
								<select id="leaders" name="leader" multiple="multiple" size="10" style="width: 127px; height: 97px;">
									<c:forEach items="${fn:split(meeting.leader,',') }"
										var="leader">
										<c:if test="${!empty leader }">
											<option value="${leader }">${leader }</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
							<div style=" height:100px;text-align: center;vertical-align: middle;" >
								<br/><input type="button" name="11" value="添加>>" onclick="leaderCheck('selLeaders','leaders',false)">
								<br/><input type="button" name="22" value="<<删除" onclick="del()">
							</div>
							<div style="clear:both;" ></div>
						</div>
					</dd>
					<dt>会议预算：</dt>
					<dd>
						<input class="input easyui-numberbox" type="text" id="budget" name="budget" size="35" value="${meeting.budget }">万元
					</dd>
					<dt>组织处室：</dt>
					<dd>
						<select id="org" name="org" class="easyui-combobox" style="width:260px;" >
							<option value=""> 请选择组织处室</option>
							<c:forEach items="${orgs }" var="org">
								<option value="${org[1] }"  <c:if test="${org[0]==meeting.org }">selected="selected"</c:if>>${org[1] }</option>
							</c:forEach>
						</select>
					</dd>
					<dt>会议联系人：</dt>
					<dd>
						<input class="input" id="contact" name="contact" size="35"type="text" value="${empty meeting.contact ? '':meeting.contact}"/>
					</dd>
					<dt>联系人电话：</dt>
					<dd>
						<input class="input" id="contactphone" name="contactphone" size="35" type="text" value="${empty meeting.contactphone ? '':meeting.contactphone}"/>
					</dd>
					<dt>预定会议室：</dt>
					<dd>
						<select name="reserve_roomid" class="easyui-combobox" style="width:260px;">
							<option value="">请选择会议室</option>
							<c:forEach items="${mrs }" var="mr">
								<option value="${mr.id }">${mr.building }${mr.room }</option>
							</c:forEach>
						</select>
					</dd>
					<dt id="addr_t" style="display: none;">会议地点：</dt>
					<dd id="addr" style="display: none;">
						<textarea rows="3" id="address" name="address" style="width: 550px">${meeting.address }</textarea>
					</dd>
					<dt>参加单位/人员：</dt>
					<dd>
							<textarea rows="3" id="depart" name="depart" style="width: 550px" onfocus="if(this.value=='内部单位/人员'){this.value='';}"
								onblur="if(this.value==''){this.value='内部单位/人员';}">${empty meeting.depart ?'内部单位/人员' : meeting.depart }</textarea>
							<textarea rows="3" id="fdepart" name="fdepart"  style="width: 550px" onfocus="if(this.value=='外部单位/人员'){this.value='';}"
								onblur="if(this.value==''){this.value='外部单位/人员';}">${empty meeting.fdepart ?'外部单位/人员' : meeting.fdepart }</textarea>
					</dd>
					<dt>有关说明：</dt>
					<dd>
						<div>
							<c:forEach var="me" items="${mes }" varStatus="i">
								<c:if test="${me.style == '1' }">
									${me.name }&nbsp;<input id="me_c_${i.count }"  type="checkbox" value="${me.name }" />&nbsp;
								</c:if>
								<c:if test="${me.style == '2' }">
									${me.name }<input id="n_me_t_${i.count }" type="hidden" value="${me.name }" />
									&nbsp;<input id="me_t_${i.count }"  type="text" value="" size="1" />
									<input id="u_me_t_${i.count }"  type="hidden" value="${me.unit }" />&nbsp;${me.unit }
								</c:if>
								<c:if test="${me.style == '3' }">
									<select id="me_s_${i.count }" name="">
										<option value="">--${me.name }--</option>
										<c:forEach var="child" items="${me.child }">
											<option value="${child.name }">${child.name }</option>
										</c:forEach>
									</select>
								</c:if>
								<c:if test="${i.count == 10 }">
									<br />
								</c:if>
							</c:forEach> 
						</div>
						<div style="margin-top: 3px;">	
							<textarea rows="3" id="remark" name="remark" style="width: 550px">${meeting.remark }</textarea>
						</div>
						<div style="clear:both;" ></div>
					</dd>
					<dt></dt>
					<dd style="text-indent: 100px;">
						<input id="sub"  type="button" value="提交" onclick="check()">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" value="重置" >
						<input type="hidden" name="show" value="${show }" /> 
						<input type="hidden" name="ctrl" value="${ctrl }"> 
						<input type="hidden" id="id" name="id" value="${meeting.id }"> 
						<input type="hidden" name="json" id="json">
					</dd>
				</dl>
				<br style="clear:both;" />
			</div>
			<br style="clear:both;" />
			<br style="clear:both;" />
		</form>
	</div>
	<jsp:include page="/index/bottom.jsp"/>
</body>
</html>