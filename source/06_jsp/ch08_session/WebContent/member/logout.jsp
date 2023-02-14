<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<style>
		#mainForm_wrap{
			border: 1px solid #D4A190;
			height:500px;
			line-height:500px;
			font-weight:bold;
			color:#805040;
			text-align:center;
			width:900px;
			margin:10px auto;
		}
	</style>
	<script>
		setTimeout(
			function(){
				location.href="<%=conPath%>/member/main.jsp"
			},2000);
	</script>
</head>
<body>
	<%
		if(session.getAttribute("name")==null){ // 이미 로그아웃 상태인데 온 경우
			response.sendRedirect(conPath+"/member/main.jsp");
		}else{
			session.invalidate();
			%>
			<jsp:include page="../member/header.jsp"/>
			<div id="mainForm_wrap">
				<h1>로그아웃되었습니다. 잠시후 페이지 이동이 있겠습니다.</h1>
			</div>
		<%}%>
	<jsp:include page="../member/footer.jsp"/>
</body>
</html>