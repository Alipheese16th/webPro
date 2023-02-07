<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style>
		table{
			margin:0 auto;
		}
		td{
			text-align:center;
			padding:5px 10px;
		}
		button{
			padding:0 10px;
		}
	</style>
	<script>
		function goBack(){
			history.back();
		}
	</script>
</head>
<body>
	<%
		int su1 = Integer.parseInt(request.getParameter("su1"));
		int su2 = Integer.parseInt(request.getParameter("su2"));
	%>
	<table>
		<tr>
			<%for(int i=su1;i<=su2;i++){%>
				<td><%=i %>단</td>
			<%}%>
		</tr>
		<%for(int j=1;j<10;j++){%>
		<tr>
			<%for(int i=su1;i<=su2;i++){%>
				<td><%=i %> * <%=j %> = <%=i*j %></td>
			<%}%>
		</tr>
		<%}%>
		<tr>
			<td colspan="<%=su2-su1+1 %>">
				<button onclick="goBack()">뒤로가기</button>
			</td>
		</tr>
	</table>
</body>
</html>