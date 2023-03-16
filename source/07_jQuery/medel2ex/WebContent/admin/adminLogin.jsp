<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="conPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="${conPath}/css/style.css" rel="stylesheet">
</head>
<body>

	<c:if test="${not empty admin }">
		<script>
			alert('이미 관리자 모드입니다');
			location.href='${conPath }/allView.do';
		</script>
	</c:if>
	
	<c:if test="${not empty member }">
		<script>
			var next = confirm('관리자계정으로 전환하시겠습니까? (현재 사용자는 로그아웃됨)');
			if(next){
				location.href="logout.do?next=adminLoginView.do";
			}else{
				history.back();
			}
		</script>
	</c:if>
	
	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
		<form action="${conPath }/adminLogin.do" method="post">
			<table>
			<caption>관리자 로그인</caption>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="aid" required autofocus></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="apw" required></td>
			</tr>
			<tr>
				<td colspan="2">
						<input type="submit" value="로그인" class="btn">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>