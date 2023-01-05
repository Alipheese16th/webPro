package com.lec.ex6preparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

// 입력받은 부서번호 중복체크후 부서명, 근무지를 입력받아 insert
public class Ex2_InsertDept {

	public static void main(String[] args) {
		
		String 			driver 	= "oracle.jdbc.driver.OracleDriver";
		String 			url 	= "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		Scanner 		sc 		= new Scanner(System.in);
		Connection 		conn 	= null;
		PreparedStatement pstmt = null;
		ResultSet  		rs   	= null;
		
		String selectQuery = "SELECT COUNT(*) CNT FROM DEPT WHERE DEPTNO = ?";
		String insertQuery = "INSERT INTO DEPT VALUES (?, ?, ?)";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(selectQuery);
			
			System.out.print("입력할 부서번호는 ?");
			int deptno = sc.nextInt();
			pstmt.setInt(1, deptno);
			rs = pstmt.executeQuery();
			rs.next();
			
			if(rs.getInt("cnt")==1) { //중복이 있는경우
				System.out.println("이미 있는 부서입니다");
			}else { //중복이 없는경우
				System.out.print("입력할 부서명은 ?");
				String dname = sc.next();
				System.out.print("입력할 부서위치는 ?");
				String loc = sc.next();
				
				rs.close(); //rs를 먼저닫아야함 
				pstmt.close();
				pstmt = conn.prepareStatement(insertQuery);
				pstmt.setInt(1, deptno);
				pstmt.setString(2, dname);
				pstmt.setString(3, loc);
				int result = pstmt.executeUpdate(); // insert, update, delete문 전송
				System.out.println(result>0? "입력성공" : "입력실패");
				
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		sc.close();
	}

}
