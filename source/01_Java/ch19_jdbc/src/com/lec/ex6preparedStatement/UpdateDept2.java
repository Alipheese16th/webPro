package com.lec.ex6preparedStatement;
//������ �μ���ȣ�� �޾� �����ϴ� �μ���ȣ���� Ȯ����, �μ���, �ٹ��� �Է¹޾� ������Ʈ 
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
				System.out.print("������ �μ���ȣ�� ?");
				int deptno = sc.nextInt();
				pstmt.setInt(1, deptno); //sql�� ?ä���
				rs = pstmt.executeQuery();
				rs.next();
				if(rs.getInt(1) != 0) { //���� ������ �μ���ȣ�� �ִٸ�?
					System.out.print("������ �μ����� ?");
					String dname = sc.next();
					System.out.print("������ ��ġ�� ?");
					String loc = sc.next();
					rs.close();
					pstmt.close();
					pstmt = conn.prepareStatement(updateQuery);
					pstmt.setString(1,dname);
					pstmt.setString(2, loc);
					pstmt.setInt(3, deptno);
					
					int result = pstmt.executeUpdate();
					System.out.println(result>0? deptno+"�� �μ����� ��������":"�ش� �μ��� �������� �ʾ� ��������");
					break;
					
				} else { // ������ �μ���ȣ�� ���ٸ�
					System.out.println("�μ���ȣ�� �������� �ʽ��ϴ�.");
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
