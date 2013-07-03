<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript" src="script/myscript.js"></script>
<script language="JavaScript">
<!--
	function MM_reloadPage(init) { //reloads the window if Nav4 resized
		if (init == true)
			with (navigator) {
				if ((appName == "Netscape") && (parseInt(appVersion) == 4)) {
					document.MM_pgW = innerWidth;
					document.MM_pgH = innerHeight;
					onresize = MM_reloadPage;
				}
			}
		else if (innerWidth != document.MM_pgW
				|| innerHeight != document.MM_pgH)
			location.reload();
	}
	MM_reloadPage(true);
	// -->

	function MM_swapImgRestore() { //v3.0
		var i, x, a = document.MM_sr;
		for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
			x.src = x.oSrc;
	}

	function MM_preloadImages() { //v3.0
		var d = document;
		if (d.images) {
			if (!d.MM_p)
				d.MM_p = new Array();
			var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
			for (i = 0; i < a.length; i++)
				if (a[i].indexOf("#") != 0) {
					d.MM_p[j] = new Image;
					d.MM_p[j++].src = a[i];
				}
		}
	}

	function MM_findObj(n, d) { //v4.0
		var p, i, x;
		if (!d)
			d = document;
		if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
			d = parent.frames[n.substring(p + 1)].document;
			n = n.substring(0, p);
		}
		if (!(x = d[n]) && d.all)
			x = d.all[n];
		for (i = 0; !x && i < d.forms.length; i++)
			x = d.forms[i][n];
		for (i = 0; !x && d.layers && i < d.layers.length; i++)
			x = MM_findObj(n, d.layers[i].document);
		if (!x && document.getElementById)
			x = document.getElementById(n);
		return x;
	}

	function MM_swapImage() { //v3.0
		var i, j = 0, x, a = MM_swapImage.arguments;
		document.MM_sr = new Array;
		for (i = 0; i < (a.length - 2); i += 3)
			if ((x = MM_findObj(a[i])) != null) {
				document.MM_sr[j++] = x;
				if (!x.oSrc)
					x.oSrc = x.src;
				x.src = a[i + 2];
			}
	}
	//-->
	function setFocus() {
		MyForm.userid.focus();
	}
</script>
</head>

<body onload="setFocus()" bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0"
	onLoad="MM_preloadImages('images/dl-2%20copy.jpg')">
	<form name="MyForm" method="POST" action="login" onSubmit="return isValid(MyForm);">
		<div id="Layer1"
			style="position: absolute; left: 48%; top: 284px; width: 235px; height: 27px; z-index: 1">
			<input type="text" name="userid" style="font-size: 12" size="27" maxlength="8" value=""><br>
			<font color=red></font>
		</div>
		<div id="Layer1"
			style="position: absolute; left: 48%; top: 332px; width: 235px; height: 27px; z-index: 1">
			<input type="password" name="passwd" style="font-size: 12" size="30"> <input
				type="hidden" name="url" value=""></input>
		</div>
		<div id="Layer2"
			style="position: absolute; left: 45%; top: 386px; width: 159px; height: 38px; z-index: 2">
			<div align="center">
				<a href="#" onMouseOut="MM_swapImgRestore()"
					onMouseOver="MM_swapImage('Image4','','images/dl-2%20copy.jpg',1)"><input type="image"
					name="Image4" border="0" src="images/dl-1%20copy.jpg" width="119" height="25"></a>
			</div>
		</div>

		<table width="1000px" border="0" cellspacing="0" cellpadding="0" height="610" align="center">
			<tr>
				<td height="101"><img src="images/head_login.jpg" width="1004" height="81"></td>
			</tr>
			<tr>
				<td background="images/bg.jpg">
					<div align="center">
						<img src="images/login1.jpg" width="433" height="193">
					</div>
				</td>
			</tr>
			<tr>
				<td height="5 px"><img src="images/line.jpg" width="1004" height="5"></td>
			</tr>
			<tr>
				<td height="60" bgcolor="#DFDFDF">
					<div align="center">
						<p>
							<font color="#0000CC" size="2">江苏核电有限公司信息中心</font>
						</p>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
