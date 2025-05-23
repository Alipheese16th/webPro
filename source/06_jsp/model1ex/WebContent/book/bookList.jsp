<%@page import="com.lec.dto.BookDto"%>
<%@page import="com.lec.dao.BookDao"%>
<%@page import="java.util.ArrayList"%>
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
			padding-top:80px;
		}
	</style>
</head>
<body>
	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
		<table>
			<tr>
			<%
				String pageNum = request.getParameter("pageNum");
				if(pageNum==null) pageNum = "1";
				int currentPage = Integer.parseInt(pageNum);
				final int PAGESIZE = 3, BLOCKSIZE = 4;
				int startRow = (currentPage -1)*PAGESIZE + 1;
				int endRow = startRow + PAGESIZE - 1;
				BookDao dao = BookDao.getInstance();
				ArrayList<BookDto> books = dao.listBook(startRow, endRow);
				for(int i=0; i<books.size(); i++){
					int price = books.get(i).getBprice();
					int discount = books.get(i).getBdiscount();
					int discountPrice = price * (100-discount)/100;
					if(i!=0&&i%3==0) out.println("</tr><tr>");
					%>
					<td>
						<%
							if(discount!=0){
								%>
									<a href="bookContent.jsp?bid=<%=books.get(i).getBid()%>&pageNum=<%=pageNum%>">
									<img src="<%=conPath%>/bookImg/<%=books.get(i).getBimage1()%>"><br>
									<%=books.get(i).getBid() %>. <%=books.get(i).getBtitle() %><br>
									</a>
									<del><%=price %>원</del><br>
									<b><%=discountPrice %>원 (<%=discount %>% 할인!)</b>
								<%
							}else{
								%>
									<a href="bookContent.jsp?bid=<%=books.get(i).getBid()%>&pageNum=<%=pageNum%>">
									<img src="<%=conPath%>/bookImg/<%=books.get(i).getBimage1()%>"><br>
									<%=books.get(i).getBid() %>. <%=books.get(i).getBtitle() %><br>
									</a>
									<b>
										<%=price %>원<br/>
										<img src="<%=conPath%>/img/hot.gif"> 절찬판매중
									</b>
								<%
							}
						%>
					</td>
					<%
				}//for
			%>
			</tr>
		</table>
		<div class ="paging">
		<%
			int bookTotalCnt = dao.getBookTotCnt(); // 등록된 책 갯수
			int pageCnt = (int)Math.ceil((double)bookTotalCnt/PAGESIZE);  // 전체 페이지 갯수
			int startPage = ((currentPage-1)/BLOCKSIZE) * BLOCKSIZE + 1;
			int endPage = startPage + BLOCKSIZE -1;
			if(endPage > pageCnt){
				endPage = pageCnt;
			}
			if(startPage > BLOCKSIZE){
			%>
				[ <a href="<%=conPath %>/book/bookList.jsp?pageNum=<%=startPage-1%>">이전</a> ]
			<%
			}
			for(int i=startPage; i<=endPage; i++){
				if(i==currentPage){
					out.println(" [ <b>"+i+"</b> ] ");
				}else{
					out.println(" [ <a href='"+conPath+"/book/bookList.jsp?pageNum="+i+"'>"+i+"</a> ] ");
				}
			}
			if(endPage < pageCnt){
			%>
				[ <a href="<%=conPath %>/book/bookList.jsp?pageNum=<%=endPage+1%>">다음</a> ]
			<%
			}
		%>
		</div>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>