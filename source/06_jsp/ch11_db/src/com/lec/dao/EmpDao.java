package com.lec.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lec.dto.EmpDto;

public class EmpDao {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private String uid = "scott";
	private String upw = "tiger";
	
	private static EmpDao INSTANCE;	
	public static EmpDao getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new EmpDao();
		}
		return INSTANCE;
	}
	private EmpDao() {
		try {
			Class.forName(driver); // 생성자함수에서 드라이버로드
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// deptno로 empList를 반환
	public ArrayList<EmpDto> deptnoEmpList(int deptno){
		ArrayList<EmpDto>  dtos = new ArrayList<EmpDto>();
		Connection 		   conn = null;
		PreparedStatement pstmt = null;
		ResultSet 			 rs = null;
		String query = "SELECT * FROM EMP WHERE DEPTNO LIKE '%' || ?";
		try {
			conn = DriverManager.getConnection(url,uid,upw);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,deptno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int empno	  = rs.getInt("empno");
				String ename  = rs.getString("ename");
				String job	  = rs.getString("job");
				int mgr		  = rs.getInt("mgr");
				Date hiredate = rs.getDate("hiredate");
				int sal		  = rs.getInt("sal");
				int comm	 = rs.getInt("comm");
				deptno 		 = rs.getInt("deptno");
				dtos.add(new EmpDto(empno,ename,job,mgr,hiredate,sal,comm,deptno,null));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return dtos;
	}
	
	// searchName으로 empList를 반환
	public ArrayList<EmpDto> searchNameEmpList(String searchName){
		ArrayList<EmpDto>  dtos = new ArrayList<EmpDto>();
		Connection 		   conn = null;
		PreparedStatement pstmt = null;
		ResultSet 			 rs = null;
		String query = "SELECT E.*, DNAME FROM EMP E, DEPT D " + 
						"    WHERE E.DEPTNO = D.DEPTNO " + 
						"    AND ENAME LIKE '%' || UPPER(TRIM(?)) || '%'";
		try {
			conn = DriverManager.getConnection(url,uid,upw);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,searchName);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int empno	  = rs.getInt("empno");
				String ename  = rs.getString("ename");
				String job	  = rs.getString("job");
				int mgr		  = rs.getInt("mgr");
				Date hiredate = rs.getDate("hiredate");
				int sal		  = rs.getInt("sal");
				int comm	 = rs.getInt("comm");
				int deptno = rs.getInt("deptno");
				String dname = rs.getString("dname");
				dtos.add(new EmpDto(empno,ename,job,mgr,hiredate,sal,comm,deptno,dname));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return dtos;
	}
	
}
