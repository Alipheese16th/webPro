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
	<h2>모든요청에서 cnt, list 출력 가능</h2>
	<h3>cnt : ${cnt }</h3>
	<h3>list : ${list }</h3>
	<h2>result4.jsp페이지 개인 정보(member/join4 결과)</h2>
	<h3>이름 : ${member.name }</h3>
	<h3>아이디 : ${member.id }</h3>
	<h3>비밀번호 : ${member.pw }</h3>
	<h3>나이 : ${member.age }</h3>
	<h3>이메일 : ${member.email }</h3>
	<h3>주소 : ${member.address }</h3>
	<button onclick="history.back()">뒤로가기</button>
	<button onclick="location.href='${conPath}/'">다시입력받기</button>
</body>
</html>