package com.lec.ex2person_dtodao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PersonDao {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url    = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	
	public final static int SUCCESS = 1;
	public final static int FAIL 	= 0;
	private static PersonDao INSTANCE;
	
	public static PersonDao getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new PersonDao();
		}
		return INSTANCE;
	}
	
	private PersonDao() {
		try {
			Class.forName(driver); // 1단계는 생성자에서 한번
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} 
		
	}
	//1번 기능 : dto(이름,직업명,국영수)를 받아 DB에 insert하고 SUCCESS/FAIL return
	public int insertPerson(PersonDto dto) {
		int result 				= FAIL;
		Connection 		   conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO PERSON VALUES(PSEQ.NEXTVAL,?,"
				+ "(SELECT JNO FROM JOB WHERE JNAME = ?),?,?,?)";
		try {
			conn  = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPname());
			pstmt.setString(2, dto.getJname());
			pstmt.setInt(3, dto.getKor());
			pstmt.setInt(4, dto.getEng());
			pstmt.setInt(5, dto.getMat());
			result = pstmt.executeUpdate();
			System.out.println(result==SUCCESS?"입력성공":"입력실패");
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
		return result;
	}//1번메소드
	
	//2번 기능 : 직업명을 받아 DB에 select한 결과를 ArrayList<PersonDto> return
	public ArrayList<PersonDto> selectJname(String jname){
		ArrayList<PersonDto> dtos = new ArrayList<PersonDto>();
		Connection 		  conn 	= null;
		PreparedStatement pstmt = null;
		ResultSet 		  rs 	= null;
		String sql = "SELECT ROWNUM RN, A.* " + 
				"    FROM (SELECT PNAME || '(' || PNO || ')' PNAME, "
				+ "JNAME, KOR, ENG, MAT, KOR+ENG+MAT TOT" + 
				"        FROM PERSON P, JOB J WHERE P.JNO = J.JNO  "
				+ "AND P.JNO = (SELECT JNO FROM JOB WHERE JNAME = ?)" + 
				"        ORDER BY TOT DESC) A";
		try {
			conn = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, jname);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int rank 	 = rs.getInt("rn");
				String pname = rs.getString("pname");
				//String jname = rs.getString("jname");
				int kor 	 = rs.getInt("kor");
				int eng 	 = rs.getInt("eng");
				int mat 	 = rs.getInt("mat");
				int sum  = rs.getInt("tot");
				dtos.add(new PersonDto(rank,pname,jname,kor,eng,mat,sum));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs 	 != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return dtos;
	}//2번메소드
	
	//3번 기능 : DB의 전체 select한 결과를 ArrayList<PersonDto> return
	public ArrayList<PersonDto> selectAll(){
		ArrayList<PersonDto> dtos = new ArrayList<PersonDto>();
		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs   = null;
		String sql = "SELECT ROWNUM RN, A.* " + 
				"    FROM (SELECT PNAME || '(' || PNO || ')' PNAME, "
				+ "JNAME, KOR, ENG, MAT, KOR+ENG+MAT TOT" + 
				"        FROM PERSON P, JOB J WHERE P.JNO = J.JNO" + 
				"        ORDER BY TOT DESC) A";
		try {
			conn = DriverManager.getConnection(url,"scott","tiger");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				PersonDto dto = new PersonDto(); // dto 객체생성은 while문 안에 넣어야한다. 
				dto.setRank(rs.getInt("rn"));	  //당연히 여러 객체를 넣을것이기때문에 각 객체는 달라야한다
				dto.setPname(rs.getString("pname"));
				dto.setJname(rs.getString("jname"));
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
				dto.setSum(rs.getInt("tot"));
				dtos.add(dto);
			}
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
		return dtos;
	}//3번메소드
	
	// jname들을 ArrayList<String>로 return
	public ArrayList<String> jnamelist(){
		ArrayList<String> jnames = new ArrayList<String>();
		Connection 		   conn = null;
		PreparedStatement pstmt = null;
		ResultSet 			 rs = null;
		String sql = "SELECT JNAME FROM JOB";
		try {
			conn = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				jnames.add(rs.getString("jname"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs    != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return jnames;
	}//4번메소드
	
}//class
