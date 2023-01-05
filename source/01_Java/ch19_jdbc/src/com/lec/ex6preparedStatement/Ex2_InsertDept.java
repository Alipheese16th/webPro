package com.lec.ex6preparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

// �Է¹��� �μ���ȣ �ߺ�üũ�� �μ���, �ٹ����� �Է¹޾� insert
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
			
			System.out.print("�Է��� �μ���ȣ�� ?");
			int deptno = sc.nextInt();
			pstmt.setInt(1, deptno);
			rs = pstmt.executeQuery();
			rs.next();
			
			if(rs.getInt("cnt")==1) { //�ߺ��� �ִ°��
				System.out.println("�̹� �ִ� �μ��Դϴ�");
			}else { //�ߺ��� ���°��
				System.out.print("�Է��� �μ����� ?");
				String dname = sc.next();
				System.out.print("�Է��� �μ���ġ�� ?");
				String loc = sc.next();
				
				rs.close(); //rs�� �����ݾƾ��� 
				pstmt.close();
				pstmt = conn.prepareStatement(insertQuery);
				pstmt.setInt(1, deptno);
				pstmt.setString(2, dname);
				pstmt.setString(3, loc);
				int result = pstmt.executeUpdate(); // insert, update, delete�� ����
				System.out.println(result>0? "�Է¼���" : "�Է½���");
				
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
