package com.lec.ex4update;
//������ �μ���ȣ�� �޾� �����ϴ� �μ���ȣ���� Ȯ����, �μ���, �ٹ��� �Է¹޾� ������Ʈ 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateDept2 {
	public static void main(String[] args) {
		String driver 	= "oracle.jdbc.driver.OracleDriver";
		String url		= "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		Scanner sc 		= new Scanner(System.in);
		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs   = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","tiger");
			stmt = conn.createStatement();
			
			while(true) {
				System.out.print("������ �μ���ȣ�� ?");
				int deptno = sc.nextInt();
				String sql = "SELECT COUNT(*) FROM DEPT WHERE DEPTNO = "+deptno;
				
				rs = stmt.executeQuery(sql);
				rs.next();
				
				if(rs.getInt(1) != 0) { //���� ������ �μ���ȣ�� �ִٸ�?
					
					System.out.print("������ �μ����� ?");
					String dname = sc.next();
					System.out.print("������ ��ġ�� ?");
					String loc = sc.next();
					
					String updateQuery = String.format("UPDATE DEPT SET DNAME = '%s', "
							+ "LOC = '%s' "
							+ "WHERE DEPTNO = %d",dname,loc,deptno);
					
					int result = stmt.executeUpdate(updateQuery);
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
