<%@page import="com.lec.dto.BookDto"%>
<%@page import="com.lec.dao.BookDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="<%=conPath %>/css/content.css" rel="stylesheet">
	<style>
		#content_form{
			padding-top:30px;
		}
	</style>
</head>
<body>
	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
	<%
		String bidStr = request.getParameter("bid");
		if(bidStr==null){
			response.sendRedirect(conPath+"/book/bookList.jsp");
		}else{
			int bid = Integer.parseInt(bidStr);
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null){
				pageNum = "1";
			}
			BookDao dao = BookDao.getInstance();
			BookDto book = dao.getBook(bid);
	%>
	<table>
		<tr>
			<td rowspan="4">
				<img src="../bookImg/<%=book.getBimage1() %>" width="100">
			</td>
			<td>
				<%=book.getBid() %>번 도서 상세보기
			</td>
		</tr>
		<tr>
			<td><%=book.getBtitle() %></td>
		</tr>
		<tr>
			<td>
				<%
					if(book.getBdiscount()!=0){
						%>
							<del><%=book.getBprice() %></del><br>
							<b>
								<%=book.getBprice()*(100-book.getBdiscount())/100 %>원<br>
								(<%=book.getBdiscount() %>% 할인)
							</b>
						<%
					}else{
						%>
							<b>
								<%=book.getBprice() %>원<br>
								<img src="<%=conPath%>/img/hot.gif"> <br>
								절찬판매중
							</b>
						<%
					}
				%>
			</td>
		</tr>
		<tr>
			<td>
				<button>구매하기</button>
				<button onclick="location.href='bookList.jsp?pageNum=<%=pageNum%>'">책목록</button>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<img src="<%=conPath%>/bookImg/<%=book.getBimage2()%>"><br>
				<pre><%=book.getBcontent()==null?"":book.getBcontent() %></pre>
			</td>
		</tr>
	</table>
	<%}%>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>