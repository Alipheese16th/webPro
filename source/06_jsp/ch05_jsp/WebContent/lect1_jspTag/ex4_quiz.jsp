<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script>
		window.onload = function(){
			document.querySelector("form").onsubmit = function(){
				var num = document.querySelector("input[name='num']");
				var n = Number(num.value.trim());
				if(isNaN(n) || n <= 0 || n%1!=0){
					alert('자연수를 입력하세요');
					num.value = '';
					num.focus();
					return false;
				}
			};
		};
	</script>
</head>
<body>
	<%
		String num = request.getParameter("num");
		if(num!=null){
			num = num.trim();
		}
	%>
	<form>
		<fieldset>
			<legend>숫자 입력</legend>
			<input type="text" name="num" value="<%
																					if(num!=null){
																						out.print(num);
																					}
																				%>">
			<input type="submit" value="누적">
		</fieldset>
	</form>
	<%
		if(num!=null){
			int n = Integer.parseInt(num);
			int total = 0;
			for(int i=1; i<=n; i++){
				total += i;
			}
			out.print("<h1>1부터 "+n+"까지 누적합 : "+total+"</h1>");
		}
	%>
</body>
</html>