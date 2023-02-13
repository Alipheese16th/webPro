<%@page import="java.sql.Timestamp"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="<%=conPath %>/css/join.css" rel="stylesheet">
</head>
<body>
<%
	request.setCharacterEncoding("utf-8");
	String name = request.getParameter("name");
	String id = request.getParameter("id");
	String pw = request.getParameter("pw").replaceAll(".","*");
	String birth = request.getParameter("birth");
	//Date birthDate = Date.valueOf(birth);
	//Timestamp birthTimestamp = Timestamp.valueOf(birth + " 00:00:00");
	Date birthDate = null;
	Timestamp birthTimestamp = null;
	if(birth!=null && !birth.equals("")){
		birthDate  = Date.valueOf(birth);
		birthTimestamp = Timestamp.valueOf(birth+"00:00:00");
	}
	
	String[] hobby = request.getParameterValues("hobby");
	String gender = request.getParameter("gender");
	String email = request.getParameter("email");
	String[] mailSend = request.getParameterValues("mailSend");
	Date date = new Date(System.currentTimeMillis());
	String ip = request.getRemoteAddr();
%>
<jsp:include page="header.jsp"/>
<div id="joinForm_wrap">
	<div id="join_title">회원가입정보</div>
	<p>name : <%=name %></p>
	<p>id : <%=id %></p>
	<p>pw : <%=pw %></p>
	<p>birth : <%=birthTimestamp!=null? birthTimestamp:""%></p>
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
	<input type="button" value="로그인" onclick="location.href='<%=conPath %>/member/login.jsp'" class="joinBtn_style">
</div>
<jsp:include page="footer.jsp"/>
<%--<%@include file="footer.jsp" %>--%>
</body>
</html>
