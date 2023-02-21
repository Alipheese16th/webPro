<%@page import="com.lec.friend.FriendDao"%>
<%@page import="com.lec.friend.FriendDto"%>
<%@page import="java.util.ArrayList"%>
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
	<br/><br/>
	<form action="friendInputListPro.jsp" method="post">
		<p>
		친구이름
		<input type="text" name="name">
		전화
		<input type="text" name="tel">
		<input type="submit" value="추가">
		</p>
		<table>
			<caption>이곳에 DB에 입력된 친구들 리스트</caption>
			<%
			FriendDao dao = FriendDao.getInstance();
			ArrayList<FriendDto> dtos = dao.getList();
			if(dtos.isEmpty()){
				%>
				<tr><td>출력할 리스트가 없습니다</td></tr>
				<%
			}else{
				%>
				<tr>
					<td>순번</td>
					<td>이름</td>
					<td>전화</td>
				</tr>
				<%
				for(FriendDto dto : dtos){
					%>
					<tr>
						<td><%=dto.getNo() %></td>
						<td><%=dto.getName() %></td>
						<td><%=dto.getTel() %></td>
					</tr>
					<%
				}
			}
			%>
		</table>
	</form>
	<br/><br/>
</body>
</html>