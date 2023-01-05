package com.lec.ex3insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// 입력받은 부서번호가 중복된 부서번호일 경우 계속 입력받는다
public class InsertDept3 {

	public static void main(String[] args) {
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs   = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","tiger");
			stmt = conn.createStatement();
			
			while(true) {
				System.out.print("입력할 부서번호는 ?");
				int deptno = sc.nextInt();
				String sql = String.format("SELECT COUNT(*) CNT FROM DEPT WHERE DEPTNO = %d",
						deptno);
				
				rs   = stmt.executeQuery(sql);
				rs.next();
				
				if(rs.getInt("cnt")!=0) { //중복이 있는경우
					System.out.println("이미 있는 부서입니다");
				}else { //중복이 없는경우
					System.out.print("입력할 부서명은 ?");
					String dname = sc.next();
					System.out.print("입력할 부서위치는 ?");
					String loc = sc.next();
					String sql2 = String.format("INSERT INTO DEPT VALUES (%d, '%s','%s')", 
							deptno,dname,loc);
					
					int result = stmt.executeUpdate(sql2); // insert, update, delete문 전송
					System.out.println(result>0? "입력성공" : "입력실패");
					break;
				}
				
			}
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs   != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		
		sc.close();
	}

}
