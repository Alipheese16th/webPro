<%@page import="java.util.ArrayList"%>
<%@page import="com.lec.book.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="<%=conPath %>/css/style.css" rel="stylesheet">
</head>
<body>
	<table>
		<tr>
		<%
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";
			int currentPage = Integer.parseInt(pageNum);
			final int PAGESIZE = 6;
			final int BLOCKSIZE = 2;
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
					<a href="detail.jsp?bid=<%=books.get(i).getBid()%>&pageNum=<%=pageNum%>">
						<img src="<%=conPath%>/bookImg/<%=books.get(i).getBimage1()%>"><br>
						<%=books.get(i).getBid() %>. <%=books.get(i).getBtitle() %><br>
					</a>
					<del><%=price %></del><br>
					<b><%=discountPrice %>원 (<%=discount %>% 할인!)</b>
				</td>
				<%
				//if(i%3==2)out.println("</tr><tr>");
			}//for
		%>
		</tr>
	</table>
	<div class ="paging">
	<%
		int bookTotalCnt = dao.getBookTotalCnt(); // 등록된 책 갯수
		int pageCnt = (int)Math.ceil((double)bookTotalCnt/PAGESIZE);
		int startPage = ((currentPage-1)/BLOCKSIZE) * BLOCKSIZE + 1;
		int endPage = startPage + BLOCKSIZE -1;
		if(endPage > pageCnt){
			endPage = pageCnt;
		}
		if(startPage > BLOCKSIZE){
		%>
			[ <a href="ex4_list.jsp?pageNum=<%=startPage-1%>">이전</a> ]
		<%
		}
		for(int i=startPage; i<=endPage; i++){
			if(i==currentPage){
				out.println(" [ <b>"+i+"</b> ] ");
			}else{
				out.println(" [ <a href='ex4_list.jsp?pageNum="+i+"'>"+i+"</a> ] ");
			}
		}
		if(endPage < pageCnt){
		%>
			[ <a href="ex4_list.jsp?pageNum=<%=endPage+1%>">다음</a> ]
		<%
		}
	%>
	</div>
</body>
</html>