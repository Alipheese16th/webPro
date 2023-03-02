<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	p{text-align:center;}
	p:last-child { color:red; font-weight: bold;}
</style>
</head>
<body>
	<form>
		<p>
			<input type="number" name="n1" value="<%=(int)(Math.random()*9)+1%>" readonly>
			*
			<input type="number" name="n2" value="<%=(int)(Math.random()*9)+1%>" readonly>
			=
			<input type="number" name="n3" autofocus>
		</p>
		<p><input type="submit" value="확인"></p>
	</form>
	<p>
		${param.n1 } * ${param.n2 } = ${param.n3 } 는 
		${param.n1*param.n2 eq param.n3 }
	</p>
</body>
</html>