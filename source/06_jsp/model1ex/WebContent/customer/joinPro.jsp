<%@page import="com.lec.dao.CustomerDao"%>
<%@page import="java.sql.Date"%>
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
	<%request.setCharacterEncoding("utf-8");%>
	<jsp:useBean id="dto" class="com.lec.ex.dto.CustomerDto" scope="page"/>
	<jsp:setProperty property="*" name="dto"/>
	<%
		String birth = request.getParameter("birth");
		if(!birth.equals("")){
			dto.setCbirth(Date.valueOf(birth));
		}
		CustomerDao dao = CustomerDao.getInstance();
		int result = dao.confirmCid(dto.getCid());
		
		if(result == CustomerDao.CUSTOMER_NONEXISTENT){ // 아이디 중복이 없다면
			result = dao.insertCustomer(dto); 							// 회원가입 진행
			if(result==CustomerDao.SUCCESS){							// 회원가입 성공
				session.setAttribute("id", dto.getCid());   // 세션에 id속성추가
				session.setAttribute("pw", dto.getCpw());
				%>
				<script>
					alert('회원가입 감사합니다. 로그인 이후에 서비스를 이용하세요');
					location.href='loginForm.jsp';
				</script>
				<%
			}else{
				// 회원가입 실패
				%>
				<script>
					alert('회원가입이 실패하였습니다. 너무 긴 데이터는 가입 불가합니다. 다시 가입해 주세요');
					location.href='joinForm.jsp';
				</script>
				<%
			}
		}else{
			// 아이디 중복이 존재
			%>
			<script>
				alert('중복된 ID입니다. 다른 아이디를 사용하세요');
				history.back();
			</script>
			<%
		}%>
</body>
</html>
