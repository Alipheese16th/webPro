<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="conPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="${conPath}/css/emp.css">
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body>
	<c:if test="${not empty writeResult}">
		<script>
			alert('${writeResult}');
			history.back();
		</script>
	</c:if>
	<form action="${conPath}/write.do" method="post" name="frm">
		<table>
			<tr>
				<th>사번</th>
				<td>
					<input type="number" name="empno" required="required" value="${param.empno}">
					<input type="button" value="중복확인" onclick="confirmChk()"><br>
					<span class="confirmMsg">${confirmMsg}</span>
				</td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name="ename" value="${param.ename}"></td>
			</tr>
			<tr>
				<th>직책</th>
				<td><input type="text" name="job" value="${param.job}"></td>
			</tr>
			<tr>
				<th>상사사번</th>
				<td>
					<select name="mgr">
						<c:forEach var="mgrEmp" items="${managerList}">
							<option value="${mgrEmp.empno}"
								<c:if test="${param.mgr eq mgrEmp.empno}">
									selected="selected"
								</c:if>
							>
								${mgrEmp.empno}-${mgrEmp.ename}
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>입사일</th>
				<td><input type="date" name="hiredate" required="required" value="${param.hiredate}"></td>
			</tr>
			<tr>
				<th>급여</th>
				<td><input type="number" name="sal" value="${param.sal}"></td>
			</tr>
			<tr>
				<th>상여</th>
				<td><input type="number" name="comm" value="${param.comm}"></td>
			</tr>
			<tr>
				<th>부서번호</th>
				<td>
					<select name="deptno">
						<c:forEach var="dept" items="${deptList}">
							<option value="${dept.deptno}"
								<c:if test="${param.deptno eq dept.deptno}">
									selected="selected"
								</c:if>
							>
								${dept.deptno} - ${dept.dname} - ${dept.loc}
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="작성">
					<input type="button" value="목록" onclick="btnList()">
				</td>
			</tr>
		</table>
	</form>
	<script>
		
		function confirmChk(){
			var empno = frm.empno.value;
			if(!empno){
				alert('사번 입력 후 중복확인하세요');
				frm.empno.focus();
			}else{
				var ename = frm.ename.value;
				var job = frm.job.value;
				var mgr = frm.mgr.value;
				var hiredate = frm.hiredate.value;
				var sal = frm.sal.value;
				var comm = frm.comm.value;
				var deptno = frm.deptno.value;
				location.href = 'confirmNo.do?empno='+empno
											+'&ename='+ename
											+'&job='+job
											+'&mgr='+mgr
											+'&hiredate='+hiredate
											+'&sal='+sal
											+'&comm='+comm
											+'&deptno='+deptno;
			}
		}
	
		$(document).ready(function(){
			
			$('form').submit(function(){
				var confirmMsg = $('.confirmMsg').text().trim();
				if(!confirmMsg){
					alert('사번 중복확인 후 등록하세요');
					return false; // submit 제한
				}else if(confirmMsg != '사용가능한 사번입니다'){
					alert('사용가능한 사번으로 등록하세요');
					frm.empno.focus();
					return false;
				}
				
			});
			
		});
		
		const btnList = function(){
			location.href = '${conPath}/empDeptList.do?pageNum=${param.pageNum}';
		};
		
	</script>
</body>
</html>