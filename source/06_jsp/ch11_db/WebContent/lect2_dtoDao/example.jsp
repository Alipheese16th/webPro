<%@page import="com.lec.dto.EmpDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.lec.dao.EmpDao"%>
<%@page import="java.sql.Date"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String conPath = request.getContextPath();
	String searchName = request.getParameter("searchName");
	if(searchName==null){
		searchName = "";
	}
%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
<title>Insert title here</title>
<link href="<%=conPath %>/css/style.css" rel="stylesheet">
</head>
<body>
	<form action="">
		<p>
			사원명 
			<input type="text" name="searchName" value="<%=searchName.toUpperCase().trim()%>"/>
			<input type="submit" value="검색"/>
		</p>
	</form>
	<table>
		<tr>
			<th>사번</th>
			<th>이름</th>
			<th>직책</th>
			<th>상사사번</th>
			<th>입사일</th>
			<th>급여</th>
			<th>부서번호</th>
			<th>부서명</th>
		</tr>
		<%
			EmpDao empDao = EmpDao.getInstance();
			ArrayList<EmpDto> empList = empDao.searchNameEmpList(searchName);
			if(empList.isEmpty()){
				out.println("<tr><td colspan='8'>해당 이름이 들어간 사원이 없습니다</td></tr>");
			}else{
				for(EmpDto emp: empList){
					out.println("<tr><td>"+emp.getEmpno()+"</td>");
					out.println("<td>"+emp.getEname()+"</td>");
					out.println("<td>"+emp.getJob()+"</td>");
					out.println("<td>"+emp.getMgr()+"</td>");
					out.println("<td>"+emp.getHiredate()+"</td>");
					out.println("<td>"+emp.getSal()+"</td>");
					out.println("<td>"+emp.getDeptno()+"</td>");
					out.println("<td>"+emp.getDname()+"</td></tr>");
				}
			}
		%>
	</table>
</body>
</html>