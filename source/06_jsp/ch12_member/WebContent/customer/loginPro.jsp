<%@page import="com.lec.customer.CustomerDao"%>
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
	<%
		request.setCharacterEncoding("utf-8");
		String cid = request.getParameter("cid");
		String cpw = request.getParameter("cpw");
		String method = request.getParameter("method");
		CustomerDao dao = CustomerDao.getInstance();
		int result = dao.loginCheck(cid, cpw);
		
		if(result == CustomerDao.LOGIN_SUCCESS){ // 로그인 성공
			session.setAttribute("customer", dao.getCustomer(cid));
		// 세션에 customer 속성 추가
			
			if(method.equals("null")){
				response.sendRedirect(conPath+"/customer/main.jsp");
			}else{
				response.sendRedirect(conPath+"/customer/modify.jsp");
			}
		}else{ // 로그인 실패
			response.sendRedirect(conPath+"/customer/login.jsp?msg=xx");
		}%>
</body>
</html>