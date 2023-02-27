<%@page import="com.lec.dto.CustomerDto"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
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
	<%! String cid, cname, ctel, cemail, caddress, cgender;
			Date cbirth;
	%>
	<%
		CustomerDto customer = (CustomerDto)session.getAttribute("customer");
		if(customer==null){ // 로그인상태가 아님
			response.sendRedirect("loginForm.jsp?method=modify"); // method 파라미터 추가후 보내기
		}else{
			cid = customer.getCid();
			cname = customer.getCname();
			ctel = customer.getCtel();
			cemail = customer.getCemail();
			caddress = customer.getCaddress();
			cgender = customer.getCgender();
			cbirth = customer.getCbirth();
		}
	%>
	<jsp:include page="../main/header.jsp"/>
	<div id="joinForm_wrap">
	<form action="modifyPro.jsp" method="post">
		<table>
			<caption id="join_title">정보수정</caption>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="cid" value="<%=cid%>" readonly="readonly" class="id"></td>
			</tr>
			<tr>
				<th>현비밀번호</th>
				<td>
					<input type="password" name="oldPw" required="required" class="pw">
				</td>
			</tr>
			<tr>
				<th>새비밀번호</th>
				<td>
					<input type="password" name="cpw" class="pw" placeholder="미작성시 변경무">
				</td>
			</tr>
			<tr>
				<th>이름</th>
				<td>
					<input type="text" name="cname" value="<%=cname %>" class="name">
				</td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td>
					<input type="text" name="ctel" value='<%=ctel==null?"":ctel%>' class="tel">
				</td>
			</tr>
			<tr>
				<th>메일</th>
				<td>
					<input type="email" name="cemail" value='<%=cemail==null?"":cemail%>' class="email">
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>
					<input type="text" name="caddress" value='<%=caddress!=null?caddress:""%>' class="address">
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>
					<input type="date" name="birth" value='<%=cbirth==null?"":cbirth.toString()%>' class="birth">
				</td>
			</tr>
			<tr>
				<th>성별</th>
				<td>
					<%
						if("m".equals(cgender)){
							%>
							<input type="radio" name="cgender" value="m" checked="checked">남
							<input type="radio" name="cgender" value="f">여
							<%
						}else if("f".equals(cgender)){
							%>
							<input type="radio" name="cgender" value="m">남
							<input type="radio" name="cgender" value="f" checked="checked">여
							<%
						}else{
							%>
							<input type="radio" name="cgender" value="m">남
							<input type="radio" name="cgender" value="f">여
							<%
						}
					%>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="정보수정" class="joinBtn_style">
					<input type="reset" value="취소" onclick="history.back()" class="joinBtn_style">
					<input type="button" value="로그아웃" onclick="location.href='logout.jsp'" class="joinBtn_style">
				</td>
			</tr>
		</table>
	</form>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>