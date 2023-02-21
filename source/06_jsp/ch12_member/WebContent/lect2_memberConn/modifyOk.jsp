<%@page import="com.lec.member.MemberDaoConn"%>
<%@page import="com.lec.member.MemberDto"%>
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
	<%-- modifyOk.jsp에서 전달받은 파라미터 :
		id, pw(바꾸고싶은 새비번), name, phone1, phone2, phone3, gender, email, address (MemberDto)
		tempBirth, oldPw(현비번) (dto에 없는 파라미터변수)
	--%>
	<% request.setCharacterEncoding("UTF-8");	%>
	<jsp:useBean id="dto" class="com.lec.member.MemberDto" scope="page"/>
	<jsp:setProperty name="dto" property="*"/>
	<%
		String tempBirth = request.getParameter("tempBirth");
		if(!tempBirth.equals("")){
			dto.setBirth(Date.valueOf(tempBirth));
		}
		MemberDto member = (MemberDto)session.getAttribute("member"); // 세션의 pw 필요
		String sessionPw = null;
		if(member!=null){
			sessionPw = member.getPw();
		}
		String oldPw = request.getParameter("oldPw");
		if(sessionPw.equals(oldPw)){
			// 현 비밀번호를 맞게 입력한 경우 정보 수정 진행
			if(dto.getPw()==null){
				// 새비밀번호를 현 비밀번호로 (비번 변경 안할거)
				dto.setPw(oldPw);
			}
			MemberDaoConn mDao = new MemberDaoConn();
			int result = mDao.modifyMember(dto);
			if(result == MemberDaoConn.SUCCESS){
				// 정보 수정 성공
				session.setAttribute("member", dto); // 수정된 정보를 session 속성으로 수정
				%>
				<script>
					alert('정보수정이 완료되었습니다');
					location.href = 'main.jsp';
				</script>
				<%
			}else{ // 정보 수정 실패
				%>
				<script>
					alert('회원정보 수정이 실패되었습니다. 정보가 너무 깁니다');
					location.href = 'modify.jsp';
				</script>
				<%
			}
		}else{
			// 현 비밀번호를 틀리게 입력한 경우 정보수정했던 페이지로 보낸다
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