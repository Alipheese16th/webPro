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
	<c:if test="${empty member}">
		<script>
			alert('글수정은 회원만 가능합니다');
			location.href = "${conPath}/boardList.do?pageNum=${param.pageNum}";
		</script>
	</c:if>

	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
		<form action="${conPath}/boardModify.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="pageNum" value="${param.pageNum}">
			<input type="hidden" name="fid" value="${param.fid}">
			<input type="hidden" name="oldFile" value="${board.ffilename}">
			<table>
				<caption>${board.fid}번글 수정하기</caption>
				<tr>
					<th>글제목</th>
					<td>
						<input type="text" name="ftitle" required value="${board.ftitle}">
					</td>
				</tr>
				<tr>
					<th>본문</th>
					<td>
						<textarea rows="5" cols="20" name="fcontent">${board.fcontent}</textarea>
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
						<input type="submit" value="글수정하기" class="btn">
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