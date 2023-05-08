<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="conPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${conPath }/css/style.css" rel="stylesheet">
</head>
<body>
	<p>"현재 총 ${cnt }명"</p>
		<table>
			<caption>결과는 다음과 같습니다.</caption>
			<tr>
				<th>이름 : </th>
				<td>${student.name }</td>
			</tr>
			<tr>
				<th>국어 : </th>
				<td>${student.kor }</td>
			</tr>
			<tr>
				<th>영어 : </th>
				<td>${student.eng }</td>
			</tr>
			<tr>
				<th>수학 : </th>
				<td>${student.mat }</td>
			</tr>
			<tr>
				<th>총점 : </th>
				<td>${student.kor + student.eng + student.mat }</td>
			</tr>
			<tr>
				<th>평균 : </th>
				<td>
					<fmt:formatNumber value="${(student.kor + student.eng + student.mat)/3 }"
						pattern="###.##"/>
				</td>
			</tr>
			<tr>
				<th colspan="2">&nbsp;</th>
			</tr>
			<tr>
				<th colspan="2">
					<input type="button" value="뒤로가기" onclick="history.back()">
					<input type="button" value="뒤로가기" onclick="location.href='${conPath}/input.do'">
				</th>
			</tr>
		</table>
</body>
</html>