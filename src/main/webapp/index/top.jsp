<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<div style="width: 100%;background-color:red; background: url('<%=path%>/images/topbg.gif') repeat-x;">
	<div style="float: left;">
		<img alt="" src="<%=path%>/images/logo.gif" width="210" height="110">
	</div>
	<div style="clear: both;"></div>
</div>
<div>
	<p align="center" class="tabletitle">
		<font size="5"><b>${title }</b></font>
	</p>
	<br />
</div>