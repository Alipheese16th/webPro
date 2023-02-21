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
	<script>
		const search = function(){
			//var name = document.getElementsByName('name')[0].value;
			var name = frm.name.value;
			var tel = frm.tel.value;
			location.href = 'friendInputList2.jsp?name='+name+'&tel='+tel;
			
		};
	</script>
</head>
<body>
	<br/><br/>
	<form action="friendInputListPro2.jsp" name="frm">
		<p>
		친구이름
		<input type="text" name="name" 
					value='<%
						String name = request.getParameter("name");
						if(name!=null)out.print(name);
					%>'>
		전화
		<input type="text" name="tel"
					value='<%
						String tel = request.getParameter("tel");
						out.print(tel==null?"":tel);
					%>'>
		<input type="button" value="검색" onclick="search()">
		<input type="submit" value="추가">
		</p>
		<table>
			<caption>이곳에 DB에 입력된 친구들 리스트</caption>
			<%
			FriendDao dao = FriendDao.getInstance();
			ArrayList<FriendDto> dtos = dao.searchFriends(name,tel);
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