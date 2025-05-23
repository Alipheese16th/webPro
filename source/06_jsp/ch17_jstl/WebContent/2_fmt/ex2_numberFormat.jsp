<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="conPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${conPath }/css/style.css" rel="stylesheet">
</head>
<body>
	<c:set var="num" value="1234567.56"/>
	<p>num : ${num }</p>
	<p>세자리 마다 , : <fmt:formatNumber value="${num }" groupingUsed="true"/></p>
	<p>세자리 마다 , : <fmt:formatNumber value="${num }" pattern="#,###.#"/>(소수점 한자리까지 반올림)</p>
	<p>세자리 마다 , : <fmt:formatNumber value="${num }" pattern="#,###.###"/></p>
	<p>세자리 마다 , : <fmt:formatNumber value="${num }" pattern="0,000.000"/></p>
	
	<c:set var="pi" value="3.141592"/>
	<p>pi : <fmt:formatNumber value="${pi }" pattern="#,###.###"/> </p>
	<p>pi : <fmt:formatNumber value="${pi }" pattern="000.00000000"/> </p>
</body>
</html>