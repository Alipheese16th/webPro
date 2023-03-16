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
	<style>
		#content_form table tr { height: 10px;}
	</style>
	<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
	<script>
		$(document).ready(function(){
			
			$('.write').css('cursor','pointer').click(function(){
				var member = "<c:out value='${member}'/>";
				if(!member){
					alert('글쓰기는 로그인한 회원만 가능합니다');
				}else{
					location.href ="${conPath}/boardWriteView.do?pageNum=${pageNum}";
				}
			});
			
			$('tr').click(function(){
				var fid = $(this).children().eq(0).text();
				if(!isNaN(fid)){
					location.href = "${conPath}/boardContent.do?fid="+fid+"&pageNum=${pageNum}";
				}
			});
			
		});
	</script>
</head>
<body>
	<c:if test="${empty list }">
		<script>
			location.href = "${conPath}/main.do";
		</script>
	</c:if>
	
	<c:if test="${not empty boardResult}">
		<script>
			alert('${boardResult}');
		</script>
	</c:if>
	
	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
		<table>
			<caption>게시판</caption>
			<tr class="write">
				<td>글쓰기</td>
			</tr>
		</table>
		<table>
			<tr>
				<th>글번호</th><th>작성자</th><th>글제목</th><th>조회수</th>
				<th>날짜</th><th>IP</th>
			</tr>
			<c:if test="${list.size() eq 0 }">
				<tr><td colspan="6">해당 페이지의 글이 없습니다</td></tr>
			</c:if>
			
			<c:if test="${list.size() ne 0 }">
				<c:forEach var="dto" items="${list }">
					<tr>
						<td>${dto.fid }</td>
						<td>${dto.mname }</td>
						
						<td class="left">
							<c:forEach var="i" begin="1" end="${dto.findent }"><!-- 답글 들여쓰기 처리 -->
								<c:if test="${i ne dto.findent }">
									&nbsp; &nbsp; 
								</c:if>
								<c:if test="${i eq dto.findent }">
									└─
								</c:if>
							</c:forEach>
								${dto.ftitle }
							<c:if test="${dto.fhit > 10 }">
								<b> * </b>
							</c:if>
						</td>
						
						<td>${dto.fhit }</td>
						<td>
							<fmt:formatDate value="${dto.frdate }" pattern="yy/MM/dd"/>
						</td>
						<td>${dto.fip }</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		
		<div class="paging">
			<c:if test="${startPage > BLOCKSIZE }">
				<a href="${conPath}/boardList.do?pageNum=${startPage-1}">[ 이전 ]</a>
			</c:if>
			
			<c:forEach var="i" begin="${startPage}" end="${endPage}">
				<c:if test="${i eq pageNum }">
					[ <b>${i}</b> ]
				</c:if>
				<c:if test="${i ne pageNum }">
					<a href="${conPath}/boardList.do?pageNum=${i}">[ ${i} ]</a>
				</c:if>
			</c:forEach>
			
			<c:if test="${endPage < pageCnt }">
				<a href="${conPath}/boardList.do?pageNum=${endPage+1}">[ 다음 ]</a>
			</c:if>
		</div>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>