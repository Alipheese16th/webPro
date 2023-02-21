<%@page import="com.lec.friend.FriendDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<%request.setCharacterEncoding("utf-8"); %>
	<jsp:useBean id="dto" class="com.lec.friend.FriendDto"/>
	<jsp:setProperty property="*" name="dto"/>
	<%
	FriendDao dao = FriendDao.getInstance();
	int result = dao.addFriend(dto.getName(), dto.getTel());
	if(result==FriendDao.SUCCESS){
		response.sendRedirect("friendInputList.jsp");
	}else{
		%>
		<script>
			alert('친구 추가 실패');
			location.href="friendInputList.jsp";
		</script>
		<%
	}
	%>
</body>
</html>