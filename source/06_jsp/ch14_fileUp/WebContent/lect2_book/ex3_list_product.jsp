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
			BookDao dao = BookDao.getInstance();
			ArrayList<BookDto> books = dao.listBook();
			for(int i=0; i<books.size(); i++){
				int price = books.get(i).getBprice();
				int discount = books.get(i).getBdiscount();
				int discountPrice = price * (100-discount)/100;
				if(i!=0&&i%3==0) out.println("</tr><tr>");
				%>
				<td>
					<a href="detail.jsp?bid=<%=books.get(i).getBid()%>">
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
</body>
</html>