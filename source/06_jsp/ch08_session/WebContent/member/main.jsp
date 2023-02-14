<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style>
		#mainForm_wrap{
			border: 1px solid #D4A190;
			height:500px;
			line-height:500px;
			font-size:32px;
			font-weight:bold;
			color:#805040;
			text-align:center;
			width:800px;
			margin:10px auto;
		}
	</style>
</head>
<body>
	<jsp:include page="../member/header.jsp"/>
	<div id="mainForm_wrap">
		main 화면
	</div>
	<jsp:include page="../member/footer.jsp"/>
</body>
</html>