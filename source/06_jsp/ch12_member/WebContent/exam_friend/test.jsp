<%@page import="com.lec.friend.FriendDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.lec.friend.FriendDao"%>
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
	FriendDao dao = FriendDao.getInstance();
	dao.addFriend("김치전사", "1010101010");
	dao.addFriend("된장전사", "1010101010");
	ArrayList<FriendDto> dtos = dao.getList();
	for(FriendDto dto : dtos){
		out.println(dto+"<br>");
	}
	System.out.print(dtos);
	%>
</body>
</html>