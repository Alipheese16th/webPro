<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="<%=conPath %>/css/exam.css" rel="stylesheet">
</head>
<body>
	<header>동전이 있는 곳을 맞춰바</header>
	<form action="examPro.jsp">
		<button name="su" value="1">1번</button>
		<button name="su" value="2">2번</button>
		<button name="su" value="3">3번</button>
	</form>
	<div id="msg">
	<%
		String msg = request.getParameter("msg");
		if(msg!=null){
			out.print(msg);
		}
	%>
	</div>
</body>
</html>