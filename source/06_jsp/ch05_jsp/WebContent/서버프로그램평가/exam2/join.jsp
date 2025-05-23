<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="join.css" rel="stylesheet">
	<script>
		var patternMail = /^\w+@\w+(\.\w+){1,2}$/;  // 이메일 패턴
		
		window.onload = function(){
			document.querySelector('form').onsubmit = function(){
				
				if(!this.name.value.trim()){
					alert('이름을 입력하세요');
					this.name.focus();
					return false;
					
				} else if(!this.id.value.trim()){
					alert('id를 입력하세요');
					this.id.focus();
					return false;
					
				} else if(!this.pw.value.trim()){
					alert('pw를 입력하세요');
					this.pw.focus();
					return false;
					
				} else if(this.pw.value != this.pwChk.value){
					alert('비밀번호가 일치하지 않습니다');
					this.pw.value='';
          this.pwChk.value='';
          return false;
					
				}else if(!this.email.value.match(patternMail)){ 
          alert('메일 형식에 맞춰주세요');
          this.email.focus();
          return false;
					
				}else {
// 					var opt = document.querySelectorAll('option');
// 					var cnt = 0;
// 					for(var i=0; i<opt.length; i++){
// 						if(opt[i].selected){
// 							cnt++;
// 						}
// 					}
// 					if(!cnt){
// 						alert('메일수신 항목을 1개 이상 체크하세요');
// 						return false;
// 					}
				
				} // else if 
				
				return true;
			}; // onsubmit
			
		}; // onload
	</script>
</head>
<body>
	<div id="joinForm_wrap">
		<div id="join_title">회원가입</div>
		
		<form action="joinPro.jsp" method="post">
			<table>
				<tr>
					<th>이름</th>
					<td><input type="text" name="name" class="name"></td>
				</tr>
				<tr>
					<th>아이디</th>
					<td><input type="text" name="id" class="id"></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="pw" class="pw"></td>
				</tr>
				<tr>
					<th>비밀번호확인</th>
					<td><input type="password" name="pwChk" class="pwChk"></td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td><input type="date" name="birth" class="birth"></td>
				</tr>
				<tr>
					<th>취미</th>
					<td>
						<input type="checkbox" name="hobby" value="독서">독서
						<input type="checkbox" name="hobby" value="요리">요리
						<input type="checkbox" name="hobby" value="운동">운동
						<input type="checkbox" name="hobby" value="취침">취침
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<input type="radio" name="gender" value="m" checked="checked">남자
						<input type="radio" name="gender" value="f">여자
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><input type="email" name="email" class="email"></td>
				</tr>
				<tr>
					<th>메일수신</th>
					<td>
						<select name="mailSend" size="3" multiple="multiple" class="mailSend">
              <option>광고</option>
              <option>배송</option>
              <option>공지</option>
              <option>이벤트</option>
              <option>할인</option>
              <option>기프티콘</option>
            </select>
					</td>
				</tr>
			</table>
			<div>
				<input type="submit" value="가입하기" class="joinBtn_style">
				<input type="reset" value="다시하기" class="joinBtn_style">
				<input type="button" value="뒤로가기" onclick="history.back()" class="joinBtn_style">
			</div>
		</form>
	</div>
<%@include file="footer.jsp" %>
</body>
</html>