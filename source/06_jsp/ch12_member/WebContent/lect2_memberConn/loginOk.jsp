<%@page import="com.lec.member.MemberDaoConn"%>
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
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String method = request.getParameter("method"); // "null" 또는 "modify"
		MemberDaoConn mDao = new MemberDaoConn();
		int result = mDao.loginCheck(id, pw);
		
		if(result == MemberDaoConn.LOGIN_SUCCESS){ // 로그인 성공
			session.setAttribute("member", mDao.getMember(id));
			if(method.equals("null")){
				response.sendRedirect(conPath+"/lect1_member/main.jsp");
			}else{
				response.sendRedirect(conPath+"/lect1_member/modify.jsp");
			}
		}else if(result == MemberDaoConn.LOGIN_FAIL_PW){ // pw 오류
		%>
		<script>
			alert('비밀번호를 확인하세요');
			history.back();
		</script>
		<%
		}else if(result == MemberDaoConn.LOGIN_FAIL_ID){ // id 오류
		%>
		<script>
			alert('아이디를 확인하세요');
			history.go(-1);
			//location.href = 'login.jsp';
		</script>
		<%
		}
	%>
</body>
</html>