<%@page import="com.lec.ex.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<%
		// midConfirm.jsp?mid=aaa
		String mid = request.getParameter("mid");
		MemberDao mDao = new MemberDao();
		int result = mDao.idConfirm(mid);
		if(result == MemberDao.EXISTENT){
			out.println("중복된 ID입니다");
		}else{
			out.println("사용가능한 ID입니다");
		}
	%>
</body>
</html>