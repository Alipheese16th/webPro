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

	<c:if test="${empty admin}">
		<script>
			location.href='${conPath}/adminLoginView.do';
		</script>
	</c:if>
	
	<c:if test="${not empty adminLoginResult }">
		<script>
			alert('${adminLoginResult}');
		</script>
	</c:if>
	
	<c:if test="${not empty adminLoginErrorMsg }">
		<script>
			alert('${adminLoginErrorMsg}');
			history.back();
		</script>
	</c:if>
	
	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
		<table>
			<caption>전체회원보기</caption>
			<tr>
				<c:forEach var="member" items="${memberList}">
					<td>
						<img src="${conPath}/memberPhotoUp/${member.mphoto}" width="120">
						<br>
						${member.mname}
						<br>
						(${member.mid})
					</td>
				</c:forEach>
			</tr>
		</table>
		<p class="paging">
			<a href="${conPath}/allView.do?pageNum=1">&lt;&lt;</a>
			&nbsp; &nbsp; &nbsp;
			<c:if test="${BLOCKSIZE < startPage}">
				<a href="${conPath }/allView.do?pageNum=${startPage-1}">&lt;</a>
			</c:if>
			<c:if test="${BLOCKSIZE >= startPage }">
				&lt;
			</c:if>
			&nbsp; &nbsp; &nbsp;
			<c:forEach var="i" begin="${startPage }" end="${endPage }">
				<c:if test="${i eq pageNum }">
					[ <b> ${i } </b> ]
				</c:if>
				<c:if test="${i != pageNum }">
					[ <a href="${conPath }/allView.do?pageNum=${i }"> ${i } </a> ]
				</c:if>
			</c:forEach>
			&nbsp; &nbsp; &nbsp;
			<c:if test="${endPage < pageCnt }">
				<a href="${conPath }/allView.do?pageNum=${endPage+1 }">&gt;</a>
			</c:if>
			<c:if test="${endPage == pageCnt }">
				&gt;
			</c:if>
			&nbsp; &nbsp; &nbsp;
			<a href="${conPath }/allView.do?pageNum=${pageCnt }">&gt;&gt;</a>
		</p>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>