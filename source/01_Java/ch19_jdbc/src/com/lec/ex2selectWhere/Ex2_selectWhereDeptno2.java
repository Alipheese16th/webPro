package com.lec.ex2selectWhere;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// 사용자에게 원하는 부서번호를 받아 부서정보를 뿌리고, 해당 부서의 사원정보도 출력
	// 1. 해당부서번호가 없는 경우 : 존재하지 않는 부서입니다
	// 2. 해당부서번호가 있는 경우 : 부서정보를 출력
		// 2-1. 해당부서 사원이 있는 경우 : 사원정보 출력
		// 2-2. 해당부서 사원이 없는 경우 : 해당 부서 사원(사번, 이름, 급여, 상사이름)이 없습니다
public class Ex2_selectWhereDeptno2 {
	public static void main(String[] args) {
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		Connection conn	= null;
		Statement  stmt	= null;
		ResultSet  rs	= null;
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("원하는 부서번호는 ?");
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
			
			if(rs.next()) { //부서가 있는경우
				String dname = rs.getString("dname");
				String loc   = rs.getString("loc");
				System.out.println("★ ★ ★ " + deptno + "번의 부서정보 ★ ★ ★");
				System.out.println("부서명 : " + dname);
				System.out.println("부서위치 : " + loc);
				
				rs.close();
				rs = stmt.executeQuery(sqlemp);
				if(rs.next()) { // 사원이 있는경우 
					System.out.println("★ ★ ★ " + deptno + "번의 사원정보 ★ ★ ★");
					System.out.println("사번\t사원이름\t급여\t상사이름");
					do {
						int 	empno 	= rs.getInt("empno");
						String  ename 	= rs.getString("ename");
						int 	sal 	= rs.getInt("sal");
						String  manager = rs.getString("manager");
						System.out.printf("%d\t %s\t %d\t %s\n",empno,ename,sal,manager);
					}while(rs.next());
					
				} else { // 사원이 없는경우
					System.out.println("해당부서 사원이 존재하지 않습니다");
				}
			} else { // 부서가 없는경우
				System.out.println("해당부서가 존재하지 않습니다");
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
