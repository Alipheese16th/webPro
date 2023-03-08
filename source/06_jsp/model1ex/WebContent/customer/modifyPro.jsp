<%@page import="com.lec.dao.CustomerDao"%>
<%@page import="com.lec.ex.dto.CustomerDto"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String conPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<%-- modifyPro.jsp에서 전달받은 파라미터 :
		cid, cpw(바꾸고싶은 새비번), cname, ctel, cemail, caddress, cgender (MemberDto)
		birth, oldPw(현비번) (dto에 없는 파라미터변수)
	--%>
	<%
		request.setCharacterEncoding("UTF-8");
	%>
	<jsp:useBean id="dto" class="com.lec.ex.dto.CustomerDto" scope="page"/>
	<jsp:setProperty name="dto" property="*"/>
	<%
		String birth = request.getParameter("birth");
		if(!birth.equals("")){
			dto.setCbirth(Date.valueOf(birth));
		}
		CustomerDto customer = (CustomerDto)session.getAttribute("customer");
		String sessionPw = null;
		if(customer!=null){
			sessionPw = customer.getCpw();
		}
		String oldPw = request.getParameter("oldPw");
		if(sessionPw.equals(oldPw)){
			// 현 비밀번호를 맞게 입력한 경우
			if(dto.getCpw()==null){ // 새비밀번호를 적지 않은경우			
				dto.setCpw(oldPw);   // 비번 변경 안함
			}
			
			CustomerDao dao = CustomerDao.getInstance();
			int result = dao.updateCustomer(dto);  // DB 정보 수정
			if(result == CustomerDao.SUCCESS){
				// 정보 수정 성공
				session.setAttribute("customer", dto); // session속성 업데이트
				%>
				<script>
					alert('정보수정이 완료되었습니다');
					location.href = '<%=conPath%>/main/main.jsp';
				</script>
				<%
			}else{ // 정보 수정 실패
				%>
				<script>
					alert('회원정보 수정이 실패되었습니다.');
					location.href = '<%=conPath%>/customer/modifyForm.jsp';
				</script>
				<%
			}
		}else{
			// 현 비밀번호를 틀리게 입력한 경우
			%>
			<script>
				alert('현 비밀번호가 바르지 않습니다. 확인하세요');
				history.back();
			</script>
			<%
		}
	%>
</body>
</html>