<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="join.css" rel="stylesheet">
</head>
<body>
<%
	request.setCharacterEncoding("utf-8");
	String name = request.getParameter("name");
	String id = request.getParameter("id");
	String pw = request.getParameter("pw").replaceAll(".","*");
	String birth = request.getParameter("birth");
	String[] hobby = request.getParameterValues("hobby");
	String gender = request.getParameter("gender");
	String email = request.getParameter("email");
	String[] mailSend = request.getParameterValues("mailSend");
	Date date = new Date(System.currentTimeMillis());
	String ip = request.getRemoteAddr();
%>
<div id="joinForm_wrap">
	<div id="join_title">회원가입정보</div>
	<p>name : <%=name %></p>
	<p>id : <%=id %></p>
	<p>pw : <%=pw %></p>
	<p>birth : <%=birth %></p>
	<p>hobby : <%
		if(hobby==null){
			out.print("없음");
		}else{
			for(String h : hobby){out.print(h);}
		}
	%></p>
	
	<p>gender : <%=gender %></p>
	<p>email : <%=email %></p>
	<p>mailSend : <%
		if(mailSend==null){
			out.print("모두 수신 거부");
		}else{
			for(String m : mailSend){out.print(m);}
		}
	%></p>
	<p>가입일 : <%=date %></p>
	<p>가입IP : <%=ip %></p>
</div>
<%@include file="footer2.jsp" %>
</body>
</html>
