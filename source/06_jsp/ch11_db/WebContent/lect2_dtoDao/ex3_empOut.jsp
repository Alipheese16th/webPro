<%@page import="com.lec.dto.EmpDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.lec.dao.EmpDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
<title>Insert title here</title>
<link href="<%=conPath %>/css/style.css" rel="stylesheet">
</head>
<body>
	<table>
		<tr><th>사번</th><th>이름</th><th>직책</th><th>부서번호</th></tr>
		<%
			EmpDao empDao = EmpDao.getInstance();
			int deptno = Integer.parseInt(request.getParameter("deptno"));
			ArrayList<EmpDto> empList = empDao.deptnoEmpList(deptno);
			
			if(empList.isEmpty()){
				out.println("<tr><td colspan='4'>해당 부서번호의 사원이 없습니다</td></tr>");
			}else{
				for(int i=0;i<empList.size();i++){
					
					int empno = empList.get(i).getEmpno();
					String ename = empList.get(i).getEname();
					String job = empList.get(i).getJob();
					deptno = empList.get(i).getDeptno();
					
					out.println("<tr><td>"+empno+"</td>");
					out.println("<td>"+ename+"</td>");
					out.println("<td>"+job+"</td>");
					out.println("<td>"+deptno+"</td></tr>");
				}
			}
			
		%>
	</table>
</body>
</html>