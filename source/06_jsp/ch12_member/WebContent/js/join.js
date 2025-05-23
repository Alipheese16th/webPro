/**
 * 
 */

const infoConfirm = function(){
	var patternMail = /^\w+@\w+(\.\w+){1,2}$/;  // 이메일 패턴
	if(join_frm.id.value.length<3){
		alert('아이디는 반드시 3글자 이상');
		join_frm.id.focus();
		return;
	}else if(!join_frm.pw.value){
		alert('비밀번호는 필수입력사항입니다');
		join_frm.pw.focus();
		return;
	}else if(join_frm.pw.value != join_frm.pwChk.value){
		alert('비밀번호를 확인하세요');
		join_frm.pw.value = '';
		join_frm.pwChk.value = '';
		join_frm.pw.focus();
		return;
	}else	if(!join_frm.name.value){
		alert('이름은 필수입력 사항입니다');
		join_frm.name.focus();
		return;
	}else	if(join_frm.email.value && !join_frm.email.value.match(patternMail)){
		alert('메일 형식을 확인하세요');
		join_frm.email.focus();
		return;
	}
	join_frm.submit(); // 강제로 submit 이벤트 발생
};