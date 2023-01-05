package com.lec.ex1person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class person {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url    = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		Statement         stmt  = null;
		ResultSet         rs    = null;
		Scanner           sc    = new Scanner(System.in);
		String fn, sql;
		ArrayList<String> jobs = new ArrayList<String>(); // ������� ����
		// ��������� jobs�� add
		
		try {
			sql = "SELECT JNAME FROM JOB";
			Class.forName(driver); // (1)����̹� �ε�
			conn = DriverManager.getConnection(url, "scott", "tiger");//(2)
			stmt = conn.createStatement();//(3)
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				jobs.add(rs.getString("jname"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
		
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		// ������ ��������
		System.out.println(jobs);
		
		do {
			System.out.print("1:�Է� || 2:��������ȸ || 3:��ü��ȸ || �׿�:����");
			fn = sc.next();
			switch(fn) {
			case "1" : // �̸�, ������(jobs), ��,��, �� �޾� insert
				System.out.print("�̸��� �Է��ϼ���");
				String name = sc.next();
				System.out.print("������ �Է��ϼ��� (���->)"+jobs);
				String job = sc.next();
				System.out.print("���������� �Է��ϼ���");
				int ko = sc.nextInt();
				System.out.print("���������� �Է��ϼ���");
				int en = sc.nextInt();
				System.out.print("���������� �Է��ϼ���");
				int ma = sc.nextInt();
				
				sql = "INSERT INTO PERSON VALUES"
						+ "(PSEQ.NEXTVAL,?,"
						+ "(SELECT JNO FROM JOB WHERE JNAME = ?)"
						+ ",?,?,?)";
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, name);
					pstmt.setString(2, job);
					pstmt.setInt(3, ko);
					pstmt.setInt(4, en);
					pstmt.setInt(5, ma);
					int result = pstmt.executeUpdate();
					System.out.println(result>0?"�Է¼���":"�Է½���");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				} finally {
					try {
						if(pstmt != null) pstmt.close();
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}
				break;
				
			case "2": // ������޾� ���� ���
				System.out.print("��ȸ�� �������� �����ּ���");
				String jname = sc.next();
				
				sql = "SELECT ROWNUM || '��' RN, A.* " + 
						"    FROM (SELECT PNAME || '(' || PNO || '��)' PNAME, JNAME, KOR, ENG, MAT, KOR+ENG+MAT TOT" + 
						"        FROM PERSON P, JOB J WHERE P.JNO = J.JNO  AND P.JNO = (SELECT JNO FROM JOB WHERE JNAME = ?)" + 
						"        ORDER BY TOT DESC) A";
				
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, jname);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						System.out.println("���\t�̸�\t\t����\t����\t����\t����\t����");
						do {
							String rank = rs.getString(1);
							String pname = rs.getString(2);
							int kor = rs.getInt(4);
							int eng = rs.getInt(5);
							int mat = rs.getInt(6);
							int tot = rs.getInt(7);
							
							if(pname.length()<7) {
								System.out.printf("%s\t %s\t\t %s\t %d\t %d\t %d\t %d\n",
										rank,pname,jname,kor,eng,mat,tot);
							} else {
								System.out.printf("%s\t %s\t %s\t %d\t %d\t %d\t %d\n",
										rank,pname,jname,kor,eng,mat,tot);
							}
							
						}while(rs.next());
						
					} else {
						System.out.println("��ȸ�� ����� �����ϴ�");
					}
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				} finally {
					try {
						if(pstmt != null) pstmt.close();
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}
				break;
			case "3":
				System.out.println("��� ����� ����մϴ�");
				sql = "SELECT ROWNUM || '��' RN, A.* " + 
						"    FROM (SELECT PNAME || '(' || PNO || '��)' PNAME, JNAME, KOR, ENG, MAT, KOR+ENG+MAT TOT" + 
						"        FROM PERSON P, JOB J WHERE P.JNO = J.JNO " + 
						"        ORDER BY TOT DESC) A";
				try {
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					System.out.println("���\t�̸�\t\t����\t����\t����\t����\t����");
					while(rs.next()) {
						String rank = rs.getString(1);
						String pname = rs.getString(2);
						String j = rs.getString(3);
						int kor = rs.getInt(4);
						int eng = rs.getInt(5);
						int mat = rs.getInt(6);
						int tot = rs.getInt(7);
						
						if(pname.length()<7) {
							System.out.printf("%s\t %s\t\t %s\t %d\t %d\t %d\t %d\n",
									rank,pname,j,kor,eng,mat,tot);
						} else {
							System.out.printf("%s\t %s\t %s\t %d\t %d\t %d\t %d\n",
									rank,pname,j,kor,eng,mat,tot);
						}
						
					} 
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				} finally {
					try {
						if(pstmt != null) pstmt.close();
						if(conn != null) conn.close();
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
					
				}
				break;
			}//switch
		}while(fn.equals("1") || fn.equals("2") || fn.equals("3"));
		System.out.println("BYE");
		
		sc.close();
	}//main
}


