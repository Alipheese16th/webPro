<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<link href="<%=conPath %>/css/main.css" rel="stylesheet">
	<script>
		setTimeout(
			function(){
				location.href="<%=conPath%>/customer/main.jsp"
			},20000);
	</script>
</head>
<body>
	<%
		if(session.getAttribute("customer")==null){ // 이미 로그아웃 상태인데 온 경우
			response.sendRedirect(conPath+"/customer/main.jsp");
		}else{
			session.invalidate();
			%>
			<jsp:include page="../customer/header.jsp"/>
			<div id="mainForm_wrap">
				<p>로그아웃되었습니다. 잠시후 페이지 이동이 있겠습니다.</p>
			</div>
			<jsp:include page="../customer/footer.jsp"/>
	<%}%>
</body>
</html>