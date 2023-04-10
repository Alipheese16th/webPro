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
	<c:forEach var="dept" items="${deptList}">
		${dept}
	</c:forEach>
	<br><br>
	<c:forEach var="emp" items="${empList}">
		${emp}
	</c:forEach>
</body>
</html>