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
	<style>
		p{
			text-align : center;
		}
	</style>
</head>
<body>
	<%!
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String uid = "scott";
		String upw = "tiger";
	%>
	<form action="">
		<p>
			사원명 
			<%
				String enameParam = request.getParameter("ename");
				if(enameParam == null){
					enameParam = "";
				}
			%>
			<input type="text" name="ename" value="<%=enameParam%>"/>
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
			
			Connection 				 conn = null;
			PreparedStatement pstmt = null;
			ResultSet 					 rs = null;
			String sql = "SELECT EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, D.DEPTNO, DNAME "+
				    "FROM EMP E, DEPT D "+
				    "WHERE E.DEPTNO = D.DEPTNO "+
				    "AND ENAME LIKE '%' || UPPER(TRIM(?)) || '%'";
				    
			try{
				Class.forName(driver);
				conn = DriverManager.getConnection(url,uid,upw);
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,enameParam);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					int empno = rs.getInt("empno");
					String ename = rs.getString("ename");
					String job = rs.getString("job");
					int mgr = rs.getInt("mgr");
					Date hiredate = rs.getDate("hiredate");
					int sal = rs.getInt("sal");
					int deptno = rs.getInt("deptno");
					String dname = rs.getString("dname");
					
					out.println("<tr><td>"+empno+"</td>");
					out.println("<td>"+ename+"</td>");
					out.println("<td>"+job+"</td>");
					out.println("<td>"+mgr+"</td>");
					out.println("<td>"+hiredate+"</td>");
					out.println("<td>"+sal+"</td>");
					out.println("<td>"+deptno+"</td>");
					out.println("<td>"+dname+"</td></tr>");
					
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}finally{ 
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			}
		%>
	</table>
</body>
</html>