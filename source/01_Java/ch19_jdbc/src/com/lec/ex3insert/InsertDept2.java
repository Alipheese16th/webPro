package com.lec.ex3insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// �Է¹��� �μ���ȣ �ߺ�üũ�� �μ���, �ٹ����� �Է¹޾� insert
public class InsertDept2 {

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
			
			System.out.print("�Է��� �μ���ȣ�� ?");
			int deptno = sc.nextInt();
			String sql = String.format("SELECT COUNT(*) CNT FROM DEPT WHERE DEPTNO = %d",
					deptno);
			
			rs   = stmt.executeQuery(sql);
			rs.next();
			
			if(rs.getInt("cnt")==1) { //�ߺ��� �ִ°��
				System.out.println("�̹� �ִ� �μ��Դϴ�");
			}else { //�ߺ��� ���°��
				System.out.print("�Է��� �μ����� ?");
				String dname = sc.next();
				System.out.print("�Է��� �μ���ġ�� ?");
				String loc = sc.next();
				String sql2 = String.format("INSERT INTO DEPT VALUES (%d, '%s','%s')", 
						deptno,dname,loc);
				
				int result = stmt.executeUpdate(sql2); // insert, update, delete�� ����
				System.out.println(result>0? "�Է¼���" : "�Է½���");
				
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
