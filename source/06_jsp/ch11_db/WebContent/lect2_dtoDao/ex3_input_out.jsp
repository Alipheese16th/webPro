<%@page import="com.lec.dto.EmpDto"%>
<%@page import="com.lec.dao.EmpDao"%>
<%@page import="com.lec.dto.DeptDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.lec.dao.DeptDao"%>
<%@page import="java.sql.Date"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
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
	<form action="">
		<p>
			부서번호
			<select name="deptno">
			<option value="0"></option>
				<%
					String deptnoStr = request.getParameter("deptno");
					if(deptnoStr ==null){
						deptnoStr = "0";
					}
					int deptnoInput = Integer.parseInt(deptnoStr);
					DeptDao deptDao = DeptDao.getInstance();
					ArrayList<DeptDto> deptList = deptDao.deptList();
					
					for(DeptDto dept:deptList){
						int deptno = dept.getDeptno();
						String dname = dept.getDname();
						String loc = dept.getLoc();
						
						if(dept.getDeptno()==deptnoInput){
							out.println("<option value='"+deptno+"' selected='selected'>"+
									deptno+"-"+dname+"-"+loc+"-"+"</option>");
						}else{
							out.println("<option value='"+deptno+"'>"+
									deptno+"-"+dname+"-"+loc+"-"+"</option>");
						}
					}
				%>
			</select>
			<input type="submit" value="검색"/>
		</p>
	</form>
	<table>
		<tr><th>사번</th><th>이름</th><th>입사일</th><th>부서번호</th></tr>
		<%
			EmpDao empDao = EmpDao.getInstance();
			ArrayList<EmpDto> empList = empDao.deptnoEmpList(deptnoInput);
			
			if(empList.isEmpty()){
				out.println("<tr><td colspan='4'>해당 부서번호의 사원이 없습니다</td></tr>");
			}else{
				for(EmpDto emp: empList){
					
					int empno = emp.getEmpno();
					String ename = emp.getEname();
					String job = emp.getJob();
					int deptno = emp.getDeptno();
					
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