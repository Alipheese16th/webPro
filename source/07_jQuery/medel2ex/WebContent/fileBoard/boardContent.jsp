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
	<c:if test="${empty board}">
		<script>
			location.href="${conPath}/boardList.do?pageNum=${param.pageNum}";
		</script>
	</c:if>
	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
		<table>
			<caption>${board.fid}번글 상세보기</caption>
			<tr><th>작성자</th><td>${board.mname }(${board.mid})님</td></tr>
			<tr><th>제목</th><td>${board.ftitle }</td></tr>
			<tr><th>본문</th><td>${board.fcontent }</td></tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<c:if test="${not empty board.ffilename }">
						<a href="${conPath}/fileBoardUp/${board.ffilename}" target="_blank">${board.ffilename}</a>
					</c:if>
					<c:if test="${empty board.ffilename }">
						첨부파일 없음
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<c:if test="${board.mid eq member.mid}"> <!-- 로그인한 접근자가 작성자인경우 -->
						<button onclick="location.href='${conPath}/boardModifyView.do?fid=${board.fid}&pageNum=${param.pageNum}'">수정</button>
					</c:if>
					<c:if test="${board.mid eq member.mid || not empty admin}"> <!-- 작성자거나 관리자인경우 -->
						<button onclick="location.href='${conPath}/boardDelete.do?fid=${board.fid}&pageNum=${param.pageNum}'">삭제</button>
					</c:if>
					<c:if test="${not empty member }"> <!-- 로그인한경우만 답글 가능 -->
						<button onclick="location.href='${conPath}/boardReplyView.do?fid=${board.fid}&pageNum=${param.pageNum}'">답변</button>
					</c:if>
					<button onclick="location.href='${conPath}/boardList.do?pageNum=${param.pageNum}'">목록</button>
				</td>
			</tr>
		</table>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>