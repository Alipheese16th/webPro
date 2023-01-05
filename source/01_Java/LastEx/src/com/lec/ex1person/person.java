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
		ArrayList<String> jobs = new ArrayList<String>(); // 직업명들 저장
		// 직업명들을 jobs에 add
		
		try {
			sql = "SELECT JNAME FROM JOB";
			Class.forName(driver); // (1)드라이버 로드
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
		// 직업명 가져오기
		System.out.println(jobs);
		
		do {
			System.out.print("1:입력 || 2:직업별조회 || 3:전체조회 || 그외:종료");
			fn = sc.next();
			switch(fn) {
			case "1" : // 이름, 직업명(jobs), 국,영, 수 받아 insert
				System.out.print("이름을 입력하세요");
				String name = sc.next();
				System.out.print("직업을 입력하세요 (목록->)"+jobs);
				String job = sc.next();
				System.out.print("국어점수를 입력하세요");
				int ko = sc.nextInt();
				System.out.print("영어점수를 입력하세요");
				int en = sc.nextInt();
				System.out.print("수학점수를 입력하세요");
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
					System.out.println(result>0?"입력성공":"입력실패");
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
				
			case "2": // 직업명받아 직업 출력
				System.out.print("조회할 직업명을 적어주세요");
				String jname = sc.next();
				
				sql = "SELECT ROWNUM || '등' RN, A.* " + 
						"    FROM (SELECT PNAME || '(' || PNO || '번)' PNAME, JNAME, KOR, ENG, MAT, KOR+ENG+MAT TOT" + 
						"        FROM PERSON P, JOB J WHERE P.JNO = J.JNO  AND P.JNO = (SELECT JNO FROM JOB WHERE JNAME = ?)" + 
						"        ORDER BY TOT DESC) A";
				
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, jname);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						System.out.println("등수\t이름\t\t직업\t국어\t영어\t수학\t총점");
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
						System.out.println("조회한 결과가 없습니다");
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
				System.out.println("모든 사람을 출력합니다");
				sql = "SELECT ROWNUM || '등' RN, A.* " + 
						"    FROM (SELECT PNAME || '(' || PNO || '번)' PNAME, JNAME, KOR, ENG, MAT, KOR+ENG+MAT TOT" + 
						"        FROM PERSON P, JOB J WHERE P.JNO = J.JNO " + 
						"        ORDER BY TOT DESC) A";
				try {
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					System.out.println("등수\t이름\t\t직업\t국어\t영어\t수학\t총점");
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


