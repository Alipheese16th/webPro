<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="<%=conPath %>/css/style.css" rel="stylesheet">
</head>
<body>
	<h1>ex6_param_include.jsp 입니다</h1>
	<hr>
	<jsp:include page="ex4.jsp">
		<jsp:param name="n" value="1"/>
		<jsp:param name="id" value="aaa"/>
		<jsp:param name="pw" value="111"/>
	</jsp:include>
	<hr>
	<h1>다시 ex6_param_include.jsp 입니다</h1>
</body>
</html>