<%@page import="com.lec.member.MemberDto"%>
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
	<%
		MemberDto member = (MemberDto)session.getAttribute("member");
		if(member!=null){ // 로그인 후
			out.println("<h2>"+member.getName()+"("+member.getId()+")님 어서오세요</h2>");
			out.println("<p>");
			out.println("<button onclick=\"location.href='"+conPath+"/lect1_member/modify.jsp'\">정보수정</button>");
			out.println("<button onclick=\"location.href='"+conPath+"/lect1_member/logout.jsp'\">로그아웃</button>");
			out.println("</p>");
		}else{ // 로그인 전
			out.println("<h2>손님 어서오세요</h2>");
			out.println("<p>");
			out.println("<button onclick=\"location.href='"+conPath+"/lect1_member/join.jsp'\">회원가입</button>");
			out.println("<button onclick=\"location.href='"+conPath+"/lect1_member/login.jsp'\">로그인</button>");
			out.println("</p>");
		}
		
	%>
</body>
</html>