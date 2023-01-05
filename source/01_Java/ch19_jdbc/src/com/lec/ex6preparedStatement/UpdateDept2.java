package com.lec.ex6preparedStatement;
//수정할 부서번호를 받아 존재하는 부서번호인지 확인후, 부서명, 근무지 입력받아 업데이트 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateDept2 {
	public static void main(String[] args) {
		String driver 	= "oracle.jdbc.driver.OracleDriver";
		String url		= "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		Scanner sc 		= new Scanner(System.in);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet  rs   = null;
		String selectQuery = "SELECT COUNT(*) FROM DEPT WHERE DEPTNO = ?";
		String updateQuery = "UPDATE DEPT SET DNAME = ?, LOC = ? WHERE DEPTNO = ?";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(selectQuery);
			
			while(true) {
				System.out.print("수정할 부서번호는 ?");
				int deptno = sc.nextInt();
				pstmt.setInt(1, deptno); //sql문 ?채우기
				rs = pstmt.executeQuery();
				rs.next();
				if(rs.getInt(1) != 0) { //만약 수정할 부서번호가 있다면?
					System.out.print("수정할 부서명은 ?");
					String dname = sc.next();
					System.out.print("수정할 위치는 ?");
					String loc = sc.next();
					rs.close();
					pstmt.close();
					pstmt = conn.prepareStatement(updateQuery);
					pstmt.setString(1,dname);
					pstmt.setString(2, loc);
					pstmt.setInt(3, deptno);
					
					int result = pstmt.executeUpdate();
					System.out.println(result>0? deptno+"번 부서정보 수정성공":"해당 부서는 존재하지 않아 수정실패");
					break;
					
				} else { // 수정할 부서번호가 없다면
					System.out.println("부서번호가 존재하지 않습니다.");
				}
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
