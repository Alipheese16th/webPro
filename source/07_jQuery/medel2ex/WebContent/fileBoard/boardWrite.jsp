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
	<c:if test="${empty member }">
		<script>
			alert('글쓰기는 회원만 가능합니다');
			location.href = "${conPath}/boardList.do";
		</script>
	</c:if>

	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
		<form action="${conPath}/boardWrite.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="mid" value="${member.mid}">	<!-- 작성자아이디 -->
			<table>
				<caption>글쓰기</caption>
				<tr>
					<th>글제목</th>
					<td>
						<input type="text" name="ftitle" required autofocus>
					</td>
				</tr>
				<tr>
					<th>본문</th>
					<td>
						<textarea rows="5" cols="20" name="fcontent"></textarea>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
						<input type="file" name="ffilename">
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="글쓰기" class="btn">
						<input type="reset" value="취소" class="btn" onclick="history.back()">
						<input type="button" value="목록" class="btn" 
							onclick="location.href='${conPath}/boardList.do?pageNum=${param.pageNum}'">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>