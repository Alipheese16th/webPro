<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="conPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="${conPath}/css/style.css" rel="stylesheet">
	<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
  <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
  <script>
  	$(function(){
  		
  		$('input[name="mid"]').keyup(function(){
  			var mid = $(this).val();
  			if(mid.length < 2){
  				$('#midConfirmResult').text('아이디는 2글자 이상');
  			}else{
  				$.ajax({
  					url : '${conPath}/midConfirm.do',
  					type : 'get',
  					data : 'mid='+mid,
  					dataType : 'html',
  					success : function(data){
  						$('#midConfirmResult').html(data);
  					},
  				});
  			}
  		}); // 아이디 중복검사
  		
  		$('#mpw, #mpwChk').keyup(function(){
  			var mpw = $('#mpw').val();
  			var mpwChk = $('#mpwChk').val();
  			if(mpw == mpwChk){
  				$('#mpwChkResult').text('비밀번호 일치');
  			}else{
  				$('#mpwChkResult').text('비밀번호 불일치');
  			}
  		}); // 비밀번호 일치 확인
  		
  		var patternMail = /^\w+@\w+(\.\w+){1,2}$/;
  		$('#memail').keyup(function(){
  			var memail = $(this).val();
  			if(memail.match(patternMail)){
  				$.ajax({
  					url : '${conPath}/memailConfirm.do',
  					type : 'get',
  					data : 'memail='+memail,
  					dataType : 'html',
  					success : function(data){
  						$('#memailConfirmResult').html(data);
  					},
  				});
  			}else{
  				$('#memailConfirmResult').text('올바르지 않은 이메일 형식입니다');
  			}
  		}); // email 유효성검사 + 중복검사
  		
  		// "사용가능한 아이디입니다","비밀번호 일치","사용가능한 이메일입니다"가 출력되었을 경우만 submit 가능
  		$('form').submit(function(){
  			var midConfirmResult = $('#midConfirmResult').text().trim();
  			var mpwChkResult = $('#mpwChkResult').text().trim();
  			var memailConfirmResult = $('#memailConfirmResult').text().trim();
  			if(midConfirmResult != '사용가능한 아이디입니다'){
  				alert('사용 가능한 ID인지 확인하세요');
  				return false; // submit 제한
  			}else if(mpwChkResult != '비밀번호 일치'){
  				alert('비밀번호를 확인하세요');
  				$('#mpw').focus();
  				return false;
  			}else if(memailConfirmResult != '사용가능한 이메일입니다'){
  				alert('이메일이 잘못되었습니다');
  				$('#memail').focus();
  				return false;
  			}
  		});
  		
  	});
  </script>
  <script>
	  $( function() {
	    $( "#datepicker" ).datepicker({
	    	dateFormat: "yy-mm-dd",
	    	dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
	    	monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	    	yearSuffix: '년',
	    	changeMonth: true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
	    	changeYear: true,  // 년을 바꿀수 있는 셀렉트 박스를 표시한다.
	    	showMonthAfterYear: true,
	    	showOtherMonths: true,  // 현재 달이 아닌 달의 날짜도 회색으로 표시
	    	//minDate: '-100y', // 현재날짜로부터 100년이전까지 년을 표시한다.
	    	minDate: new Date(1950, 1 - 1, 1), // 1950년 1월 1일을 최소날짜로 세팅
	    	maxDate: 'y',					// 현재 날짜 이전만 선택
	    	yearRange: 'c-100:c+10', // 년도 선택 셀렉트박스를 현재 년도에서 이전, 이후로 얼마의 범위를 표시할것인가.
	    });
	  } );
  </script>
</head>
<body>
	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
		<form action="join.do" method="post" enctype="multipart/form-data">
			<table>
					<tr>
						<th><label for="mid">아이디</label></th>
						<td>
							<input type="text" name="mid" id="mid" required="required">
							<div id="midConfirmResult"> &nbsp; &nbsp; &nbsp; </div>
						</td>
					</tr>
					<tr>
						<th><label for="mpw">비밀번호</label></th>
						<td>
							<input type="password" name="mpw" id="mpw">
						</td>
					</tr>
					<tr>
						<th><label for="mpwChk">비밀번호확인</label></th>
						<td>
							<input type="password" name="mpwChk" id="mpwChk">
							<div id="mpwChkResult"> &nbsp; &nbsp; &nbsp; </div>
						</td>
					</tr>
					<tr>
						<th><label for="mname">이름</label></th>
						<td>
							<input type="text" name="mname" id="mname">
						</td>
					</tr>
					<tr>
						<th><label for="memail">메일</label></th>
						<td>
							<input type="email" name="memail" id="memail">
							<div id="memailConfirmResult"> &nbsp; &nbsp; &nbsp; </div>
						</td>
					</tr>
					<tr>
						<th><label for="mphoto">사진</label></th>
						<td>
							<input type="file" name="mphoto" id="mphoto">
						</td>
					</tr>
					<tr>
						<th>생년월일</th>
						<td>
							<input type="text" name="mbirth" id="datepicker">
						</td>
					</tr>
					<tr>
						<th><label for="maddress">주소</label></th>
						<td>
							<input type="text" name="maddress" id="maddress">
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="회원가입" class="btn">
							<input type="button" value="로그인" class="btn" onclick="location.href='${conPath}/loginView.do'">
						</td>
					</tr>
				</table>
			</form>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>