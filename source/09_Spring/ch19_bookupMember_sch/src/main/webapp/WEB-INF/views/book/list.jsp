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
	<style>
	#content {width: 800px; height:350px;margin: 50px auto; }
	img{width: 100px;}
	a {text-decoration: none; color:black;}
	b {color:red;}
</style>
	
</head>
<body>
	<c:set var="SUCCESS" value="1"></c:set>
	<c:if test="${param.modifyResult eq SUCCESS }">
		<script>
			alert('책수정 완료');
		</script>
	</c:if>

	<jsp:include page="../main/header.jsp"/>
	<div id="content">
		<div>
			<form action="${conPath}/book.do">
				<input type="hidden" name="method" value="list">
				<select name="schItem">
					<option value=""
						<c:if test="${param.schItem eq '' }">selected="selected"</c:if>
					>검색조건</option>
					<option value="all"
						<c:if test="${param.schItem eq 'all' }">selected="selected"</c:if>
					>책이름 + 저자</option>
					<option value="btitle"
						<c:if test="${param.schItem eq 'btitle' }">selected="selected"</c:if>
					>책이름</option>
					<option value="bwriter"
						<c:if test="${param.schItem eq 'bwrtier' }">selected="selected"</c:if>
					>저자</option>
				</select>
				<input type="text" name="schWord" value="${param.schWord}">
				<input type="submit" value="검색">
			</form>
		</div>
	
		<table>
			<caption>도서 List</caption>
			<tr>
			<c:forEach var="book" items="${bookList}">
				<td>
				   ${book.btitle }<br>
				   <a href="${conPath }/book.do?method=detail&bnum=${book.bnum}&pageNum=${paging.currentPage}">
				   <c:if test="${book.bimg1 ne 'noImg.png'}">
					   <img src="${conPath }/bookImgFileUpload/${book.bimg1}">
				   </c:if>
				   <c:if test="${book.bimg1 eq 'noImg.png'}">
					   <img src="${conPath }/bookImgFileUpload/${book.bimg2}">
				   </c:if>
				   </a>
				   <br>
				   ${book.bwriter } 著 <br>
				   출판일 : ${book.brdate }
				</td>
			</c:forEach>
			</tr>
		</table>
		
		<div class="paging">
		
			<c:if test="${paging.startPage>paging.blockSize}">
				[ <a href="${conPath }/book.do?method=list&pageNum=${paging.startPage-1}&schItem=${param.schItem}&schWord=${param.schWord}">이전</a> ]
			</c:if>	
			<c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}">
				<c:if test="${paging.currentPage==i}"> 
					<b>[ ${i} ]</b> 
				</c:if>
				<c:if test="${paging.currentPage != i}">
					[ <a href="${conPath}/book.do?method=list&pageNum=${i}&schItem=${param.schItem}&schWord=${param.schWord}">${i}</a> ]
				</c:if>
			</c:forEach>
			<c:if test="${paging.endPage<paging.pageCnt}">
				[ <a href="${conPath}/book.do?method=list&pageNum=${paging.endPage+1}&schItem=${param.schItem}&schWord=${param.schWord}">다음</a> ]
			</c:if>
			
		</div>
		
		
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>