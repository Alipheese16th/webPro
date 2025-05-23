<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="conPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="${conPath}/css/style.css">
</head>
<body>
	<h3>board의 list.jsp입니다</h3>
	<c:if test="${list.size() eq 0}">
		<h4>추가된 member가 없습니다</h4>
	</c:if>
	<c:if test="${list.size() ne 0}">
		<c:forEach items="${list}" var="member">
			<h4>ID : ${member.id} / PW : ${member.pw}</h4>
		</c:forEach>
		<h4>이상 추가된 member는 ${list.size()}명입니다</h4>
	</c:if>
</body>
</html>