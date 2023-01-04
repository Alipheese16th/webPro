package com.lec.ex2selectWhere;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// ����ڿ��� ���ϴ� �μ���ȣ�� �޾� �μ������� �Ѹ���, �ش� �μ��� ��������� ���
	// 1. �ش�μ���ȣ�� ���� ��� : �������� �ʴ� �μ��Դϴ�
	// 2. �ش�μ���ȣ�� �ִ� ��� : �μ������� ���
		// 2-1. �ش�μ� ����� �ִ� ��� : ������� ���
		// 2-2. �ش�μ� ����� ���� ��� : �ش� �μ� ���(���, �̸�, �޿�, ����̸�)�� �����ϴ�
public class Ex2_selectWhereDeptno2 {
	public static void main(String[] args) {
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		Connection conn	= null;
		Statement  stmt	= null;
		ResultSet  rs	= null;
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("���ϴ� �μ���ȣ�� ?");
		int deptno = scanner.nextInt();
		
		String sqldept = "SELECT * FROM DEPT WHERE DEPTNO = " + deptno;
		String sqlemp = "SELECT W.EMPNO, W.ENAME, W.SAL, M.ENAME MANAGER " + 
				"    FROM EMP W, EMP M " + 
				"    WHERE W.MGR = M.EMPNO" + 
				"    AND W.DEPTNO = " + deptno;
		try {
			Class.forName(driver);
			conn	= DriverManager.getConnection(url, "scott", "tiger");
			stmt	= conn.createStatement();
			rs		= stmt.executeQuery(sqldept);
			
			if(rs.next()) { //�μ��� �ִ°��
				String dname = rs.getString("dname");
				String loc   = rs.getString("loc");
				System.out.println("�� �� �� " + deptno + "���� �μ����� �� �� ��");
				System.out.println("�μ��� : " + dname);
				System.out.println("�μ���ġ : " + loc);
				
				rs.close();
				rs = stmt.executeQuery(sqlemp);
				if(rs.next()) { // ����� �ִ°�� 
					System.out.println("�� �� �� " + deptno + "���� ������� �� �� ��");
					System.out.println("���\t����̸�\t�޿�\t����̸�");
					do {
						int 	empno 	= rs.getInt("empno");
						String  ename 	= rs.getString("ename");
						int 	sal 	= rs.getInt("sal");
						String  manager = rs.getString("manager");
						System.out.printf("%d\t %s\t %d\t %s\n",empno,ename,sal,manager);
					}while(rs.next());
					
				} else { // ����� ���°��
					System.out.println("�ش�μ� ����� �������� �ʽ��ϴ�");
				}
			} else { // �μ��� ���°��
				System.out.println("�ش�μ��� �������� �ʽ��ϴ�");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs	!= null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		scanner.close();
	}
}
