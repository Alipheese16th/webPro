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
	<form action="<%=conPath%>/ex1_servlet">
		<p>ID<input type="text" name="id"></p>
		<p>PW<input type="password" name="pw"></p>
		<p>NAME<input type="text" name="name"></p>
		<p><input type="submit" value="확인"></p>
	</form>
</body>
</html>