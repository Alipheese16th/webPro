<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style type="text/css">
/* Reset */
html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, img, ins, kbd, q, s, samp,
small, strike, strong, tt, var,
b, u, i, center,
dl, dt, dd, ol, ul, li,
fieldset, form, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td,
article, aside, canvas, details, embed, 
figure, figcaption, footer, header, hgroup, 
menu, nav, output, ruby, section, summary,
time, mark, audio, video {
	margin: 0;
	padding: 0;
	border: 0;
	font-size: 100%;
	font: inherit;
	vertical-align: baseline;
}
/* HTML5 display-role reset for older browsers */
article, aside, details, figcaption, figure, 
footer, header, hgroup, menu, nav, section {
	display: block;
}
body {
	line-height: 1;
	font-family: 'Arial', 'Simsun', 'SamsungOne', 'Malgun Gothic';
	font-size:10pt;
	color:#333333;
	background: #ededed;
}
ol, ul {
	list-style: none;
}
blockquote, q {
	quotes: none;
}
blockquote:before, blockquote:after,
q:before, q:after {
	content: '';
	content: none;
}
table {
	border-collapse: collapse;
	border-spacing: 0;
}
b { 
	color:#1e75ce;
}
.center{text-align:center;}
.left{text-align:left;}
.right{text-align:right;}

/* Layout */
#wrapper {
	position:absolute;
    overflow:hidden;
	margin:0px auto;
	background:#ffffff;	
	border:1px solid #e0e0e0;
	width:710px;
	min-height:200px;
	padding:20px;
	left:50%;
	margin-left:-350px;
	margin-top:100px;
}
#header {
	border-bottom: 2px solid #505050;
	width:100%;
	padding:15px 0 15px 0;
}
#contents {
	width:100%;

}
/* Base */
.title{
	width:100%;
	overflow:hidden;
	border-bottom:0px solid #eaeaea;
}
.title dl{
	display:table;
}
.title dl dt{
	display:table-cell;
	width:550px;
	height:120px;
	vertical-align:middle;
	text-align:left;
	padding:0px;
	font-size:16pt;
	line-height:30px;
}
.title dl dd{
	display:table-cell;
	width:400px;
	height:120px;
	vertical-align:middle;
	text-align:center;
}
h1 {
	text-align:center;
	padding:8px 0 8px 15px;
	font-size:20pt;
	color:#1e75ce;

}
</style>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta charset="UTF-8">	
	<title>Error</title>
	<link rel="stylesheet" type="text/css" href="css/common.css">
</head>
<body>

<div id="wrapper">
	<div id="header">
		<h1>Not Found</a></h1>
	</div>
	<div id="contents">
			<div class="title">
				<dl>
				    <dd><img src="/images/common/error_img.png" alt="error"></dd>
					<dt>The server has not found anything matching the Request.<br>
					<font size="3pt" color="#1e75ce">E-mail : dehs@donggramisoft.co.kr</font></dt>
			    </dl>
			</div>
	</div>
</div>
</body>
</html>