<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String conPath = request.getContextPath();
	if(session.getAttribute("customer")!=null){ // 로그인 상태
		response.sendRedirect(conPath+"/customer/main.jsp");
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="<%=conPath %>/css/join.css" rel="stylesheet">
	<script>
		var patternMail = /^\w+@\w+(\.\w+){1,2}$/;  // 이메일 패턴
		window.onload = function(){
			document.querySelector('form').onsubmit = function(){
				if(!this.cid.value.trim()){
					alert('id를 입력하세요');
					this.cid.focus();
					return false;
				} else if(!this.cpw.value.trim()){
					alert('pw를 입력하세요');
					this.cpw.focus();
					return false;
				} else if(this.cpw.value != this.cpwChk.value){
					alert('비밀번호가 일치하지 않습니다');
					this.cpw.value='';
          this.cpwChk.value='';
          return false;
				} else if(!this.cname.value.trim()){
					alert('이름을 입력하세요');
					this.cname.focus();
					return false;
				}else if(this.cemail.value && !this.cemail.value.match(patternMail)){ 
          alert('메일 형식에 맞춰주세요');
          this.cemail.focus();
          return false;
				}
				return true;

			}; // onsubmit
		}; // onload
	</script>
</head>
<body>
	<jsp:include page="../customer/header.jsp"/>
	<div id="joinForm_wrap">
		<div id="join_title">회원가입</div>
		<form action="joinPro.jsp" method="post">
			<table>
				<tr>
					<th>아이디</th>
					<td><input type="text" name="cid" class="id"></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="cpw" class="pw"></td>
				</tr>
				<tr>
					<th>비밀번호확인</th>
					<td><input type="password" name="cpwChk" class="pwChk"></td>
				</tr>
				<tr>
					<th>이름</th>
					<td><input type="text" name="cname" class="name"></td>
				</tr>
				<tr>
					<th>전화</th>
					<td><input type="text" name="ctel" class="tel"></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><input type="email" name="cemail" class="email"></td>
				</tr>
				<tr>
					<th>주소</th>
					<td><input type="text" name="caddress" class="address"></td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td><input type="date" name="birth" class="birth"></td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<input type="radio" name="cgender" value="m">남자
						<input type="radio" name="cgender" value="f">여자
					</td>
				</tr>
			</table>
			<div>
				<input type="submit" value="가입하기" class="joinBtn_style">
				<input type="reset" value="다시하기" class="joinBtn_style">
				<input type="button" value="로그인" onclick="location.href='<%=conPath %>/customer/login.jsp'" class="joinBtn_style">
			</div>
		</form>
	</div>
<jsp:include page="../customer/footer.jsp"/>
</body>
</html>