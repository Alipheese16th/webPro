<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style>
		#mainForm_wrap{
			border: 1px solid #D4A190;
			height:500px;
			color:#805040;
			text-align:center;
			width:800px;
			margin:10px auto;
		}
		.joinBtn_style {
			color: #ffffff;        	background: #A47160;
			border:0;               outline: none;
			width:32%;              padding:5px 0; 
		}
	</style>
</head>
<body>
	<jsp:include page="../member/header.jsp"/>
	<div id="mainForm_wrap">
		<br><br>
		<h2>세션 리스트</h2>
		<%
		Enumeration<String> names = session.getAttributeNames();
		int cnt = 0;
		while(names.hasMoreElements()){
			String name = names.nextElement();
			String value = session.getAttribute(name).toString();
			out.println("<h3> 세션명 : "+name+"  세션값 : "+value+"</h3>");
			cnt++;
		}
		if(cnt==0){
			out.println("<h3>세션 내용이 없습니다</h3>");
		}
		%>
		<br>
		<button onclick="history.back()" class="joinBtn_style">뒤로가기</button>
	</div>
	<jsp:include page="../member/footer.jsp"/>
</body>
</html>