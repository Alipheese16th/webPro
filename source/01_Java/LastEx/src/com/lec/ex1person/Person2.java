package com.lec.ex1person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Person2 {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url    = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		Statement         stmt  = null;
		ResultSet         rs    = null;
		Scanner           sc    = new Scanner(System.in);
		String fn, sql;
		ArrayList<String> jobs = new ArrayList<String>();
		
		try {
			sql = "SELECT JNAME FROM JOB";
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "scott", "tiger");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				jobs.add(rs.getString("jname"));
			}
			
			do {
				System.out.print("1:�Է� || 2:��������ȸ || 3:��ü��ȸ || �׿�:����");
				fn = sc.next();
				switch(fn) {
				case "1" : // �̸�, ������(jobs), ��,��, �� �޾� insert
					System.out.print("�̸��� �Է��ϼ���");
					String pname = sc.next();
					System.out.print("������ �Է��ϼ��� (���:"+jobs+")");
					String jname = sc.next();
					System.out.print("���������� �Է��ϼ���");
					int kor = sc.nextInt();
					System.out.print("���������� �Է��ϼ���");
					int eng = sc.nextInt();
					System.out.print("���������� �Է��ϼ���");
					int mat = sc.nextInt();
					sql = "INSERT INTO PERSON VALUES"
							+ "(PSEQ.NEXTVAL,?,"
							+ "(SELECT JNO FROM JOB WHERE JNAME = ?)"
							+ ",?,?,?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, pname);
					pstmt.setString(2, jname);
					pstmt.setInt(3, kor);
					pstmt.setInt(4, eng);
					pstmt.setInt(5, mat);
					int result = pstmt.executeUpdate();
					System.out.println(result>0?"�Է¼���":"�Է½���");
					break;
					
				case "2": // ������޾� ���� ���
					System.out.print("��ȸ�� �������� �����ּ���");
					jname = sc.next();
					sql = "SELECT ROWNUM || '��' RN, A.* " + 
							"    FROM (SELECT PNAME || '(' || PNO || '��)' PNAME, JNAME, KOR, ENG, MAT, KOR+ENG+MAT TOT" + 
							"        FROM PERSON P, JOB J WHERE P.JNO = J.JNO  AND P.JNO = (SELECT JNO FROM JOB WHERE JNAME = ?)" + 
							"        ORDER BY TOT DESC) A";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, jname);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						System.out.println("���\t�̸�\t\t����\t����\t����\t����\t����");
						do {
							String rank = rs.getString(1);
								  pname = rs.getString(2);
								    kor = rs.getInt(4);
								    eng = rs.getInt(5);
								    mat = rs.getInt(6);
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
					break;
					
				case "3":
					System.out.println("��� ����� ����մϴ�");
					sql = "SELECT ROWNUM || '��' RN, A.* " + 
							"    FROM (SELECT PNAME || '(' || PNO || '��)' PNAME, JNAME, KOR, ENG, MAT, KOR+ENG+MAT TOT" + 
							"        FROM PERSON P, JOB J WHERE P.JNO = J.JNO " + 
							"        ORDER BY TOT DESC) A";
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					System.out.println("���\t�̸�\t\t����\t����\t����\t����\t����");
					while(rs.next()) {
					String rank = rs.getString(1);
						  pname = rs.getString(2);
						  jname = rs.getString(3);
							kor = rs.getInt(4);
							eng = rs.getInt(5);
							mat = rs.getInt(6);
						int tot = rs.getInt(7);
						if(pname.length()<7) {
							System.out.printf("%s\t %s\t\t %s\t %d\t %d\t %d\t %d\n",
									rank,pname,jname,kor,eng,mat,tot);
						} else {
							System.out.printf("%s\t %s\t %s\t %d\t %d\t %d\t %d\n",
									rank,pname,jname,kor,eng,mat,tot);
						}
					} 
					break;
				}//switch
				
			}while(fn.equals("1") || fn.equals("2") || fn.equals("3"));
			System.out.println("BYE");
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rs 	 != null) rs.close();
				if(stmt  != null) stmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}//��ü ū �ϳ��� try-catch��
		
		sc.close();
	}//main
}


