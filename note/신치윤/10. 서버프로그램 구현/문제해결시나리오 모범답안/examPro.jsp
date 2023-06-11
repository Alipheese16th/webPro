<%@page import="java.net.URLEncoder"%>
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
	<%
		int su = Integer.parseInt(request.getParameter("su"));
		int random = (int)(Math.random()*3+1);
		
 		if(su != random){
 			String msg = "Wrong! Try Again!<br> ("+su+"은(는) 아니고 정답은 "+random+")";
 			msg = URLEncoder.encode(msg,"utf-8");
 			response.sendRedirect("exam.jsp?msg="+msg);
 			
 		}
	%>
	<h3 id="good">
		정답입니다<br>
		동전이 있던 곳은 <%=su %>
	</h3>
	<h3>
		<button onclick="location.href='exam.jsp'">다시 도전</button>
	</h3>
</body>
</html>