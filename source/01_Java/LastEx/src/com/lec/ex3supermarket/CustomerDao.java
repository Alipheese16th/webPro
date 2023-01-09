package com.lec.ex3supermarket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerDao {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url    = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	
	public final static int SUCCESS = 1;
	public final static int FAIL 	= 0;
	private static CustomerDao INSTANCE;
	public static CustomerDao getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new CustomerDao();
		}
		return INSTANCE;
	}
	private CustomerDao() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//1.회원가입 : public int insertCustomer(CustomerDto dto)
	public int insertCustomer(CustomerDto dto) {
		int result 				= FAIL;
		Connection 		   conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO CUSTOMER(CID,CTEL,CNAME) "
				+ "VALUES(CUSTOMER_CID_SEQ.NEXTVAL,?,?)";
		try {
			conn  = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCtel());
			pstmt.setString(2, dto.getCname());
			result = pstmt.executeUpdate();
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
	}
	
	//1-2. 오버로딩 : public int insertCustomer(String ctel, String cname)
	public int insertCustomer(String ctel,String cname) {
		int result 				= FAIL;
		Connection 		   conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO CUSTOMER(CID,CTEL,CNAME) "
				+ "VALUES(CUSTOMER_CID_SEQ.NEXTVAL,?,?)";
		try {
			conn  = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ctel);
			pstmt.setString(2, cname);
			result = pstmt.executeUpdate();
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
	}
	
	//2.폰(4자리or풀번호)검색 : public ArrayList<CustomerDto> ctelGetCustomers(String searchTel)
	//(CID,CTEL,CNAME,CPOINT,CAMOUNT,GRADE,LEVELUP)
	public ArrayList<CustomerDto> ctelGetCustomers(String searchTel){
		ArrayList<CustomerDto> dtos = new ArrayList<CustomerDto>();
		Connection conn = null;
		PreparedStatement pstmt  = null;
		ResultSet rs    = null;
		String sql = "SELECT CID,CTEL,CNAME,CPOINT,CAMOUNT,GRADE,"
					+ "(SELECT HIAMOUNT+1 - CAMOUNT FROM CUSTOMER "
						+ "WHERE GNO!=5 AND CID=C.CID) LEVELUP" + 
				"    FROM CUSTOMER C, GRADE G" + 
				"    WHERE C.GNO = G.GNO" + 
				"    AND CTEL LIKE '%'|| ?";
		try {
			conn = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,searchTel);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CustomerDto dto = new CustomerDto();
				dto.setCid(rs.getInt("cid"));
				dto.setCtel(rs.getString("ctel"));
				dto.setCname(rs.getString("cname"));
				dto.setCpoint(rs.getInt("cpoint"));
				dto.setCamount(rs.getInt("camount"));
				dto.setGrade(rs.getString("grade"));
				dto.setLevelup(rs.getInt("levelup"));
				dtos.add(dto);
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
	}
	
	//3.물품구입 : public int buy(int cid,int price)
	public int buy(int cid, int price) {
		int result 				= FAIL;
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE CUSTOMER SET CAMOUNT = CAMOUNT + ?, " + 
				"                    CPOINT = CPOINT + ?*0.05, " + 
				"                    GNO = (SELECT G.GNO " + 
				"                                FROM CUSTOMER, GRADE G " + 
				"                                WHERE CAMOUNT+? BETWEEN LAMOUNT AND HIAMOUNT AND CID = ?) " + 
				"    WHERE CID = ?";
		try {
			conn = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, price);
			pstmt.setInt(2, price);
			pstmt.setInt(3, price);
			pstmt.setInt(4, cid);
			pstmt.setInt(5, cid);
			result = pstmt.executeUpdate();
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
	}
	
	//3번 물품 구입후 구매자 정보 출력 : public CustomerDto getCustomer(int cid)
	public CustomerDto getCustomer(int cid) {
		CustomerDto dto = null;
		Connection conn = null;
		Statement  stmt = null;
		ResultSet    rs = null;
		String sql = "SELECT CID,CTEL,CNAME,CPOINT,CAMOUNT,GRADE,"
				+ "(SELECT HIAMOUNT+1 - CAMOUNT FROM CUSTOMER WHERE GNO!=5 AND CID=C.CID) LEVELUP" + 
				"    FROM CUSTOMER C, GRADE G" + 
				"    WHERE C.GNO = G.GNO" + 
				"    AND CID = "+cid;
		try {
			conn = DriverManager.getConnection(url, "scott", "tiger");
			stmt = conn.createStatement();
			rs   = stmt.executeQuery(sql);
			if(rs.next()) { // 검색결과가 있을 경우(cid가 있을경우), dto에 CustomerDto객체 생성하여 할당
				String 	ctel 	= rs.getString("ctel");
				String 	cname 	= rs.getString("cname");
				int 	cpoint 	= rs.getInt("cpoint");
				int 	camount = rs.getInt("camount");
				String 	grade 	= rs.getString("grade");
				int 	levelup = rs.getInt("levelup");
				dto = new CustomerDto(cid, ctel, cname, cpoint, camount, grade, levelup);
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
		return dto;
	}
	//4번 전 고객등급명들 추출 public ArrayList<String> getGrades()
	public ArrayList<String> getGrades(){
		ArrayList<String> grades = new ArrayList<String>();
		Connection conn = null;
		Statement stmt  = null;
		ResultSet rs    = null;
		String sql = "SELECT GRADE FROM GRADE";
		try {
			conn = DriverManager.getConnection(url,"scott","tiger");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				grades.add(rs.getString("grade"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs 	 != null) rs.close();
				if(stmt  != null) stmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return grades;
	}
	//4.고객 등급별 출력 : public ArrayList<CustomerDto> gradeGetCustomers(String grade)
	public ArrayList<CustomerDto> gradeGetCustomers(String grade){
		ArrayList<CustomerDto> dtos = new ArrayList<CustomerDto>();
		Connection 			conn = null;
		PreparedStatement pstmt  = null;
		ResultSet 		   rs    = null;
		String sql = "SELECT CID,CTEL,CNAME,CPOINT,CAMOUNT,GRADE,"
				+ "(SELECT HIAMOUNT+1 - CAMOUNT FROM CUSTOMER WHERE GNO!=5 AND CID=C.CID) LEVELUP" + 
				"    FROM CUSTOMER C, GRADE G" + 
				"    WHERE C.GNO = G.GNO" + 
				"    AND GRADE = UPPER(?)" + 
				"    ORDER BY CAMOUNT DESC";
		try {
			conn = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, grade);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CustomerDto dto = new CustomerDto();
				dto.setCid(rs.getInt("cid"));
				dto.setCtel(rs.getString("ctel"));
				dto.setCname(rs.getString("cname"));
				dto.setCpoint(rs.getInt("cpoint"));
				dto.setCamount(rs.getInt("camount"));
				dto.setGrade(rs.getString("grade"));
				dto.setLevelup(rs.getInt("levelup"));
				dtos.add(dto);
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
	}
	//5.고객 전체 출력 : public ArrayList<CustomerDto> getCustomers()
	public ArrayList<CustomerDto> getCustomers(){
		ArrayList<CustomerDto> dtos = new ArrayList<CustomerDto>();
		Connection 			conn = null;
		PreparedStatement  pstmt = null;
		ResultSet 		   	  rs = null;
		String sql = "SELECT CID,CTEL,CNAME,CPOINT,CAMOUNT,GRADE,"
				+ "(SELECT HIAMOUNT+1 - CAMOUNT FROM CUSTOMER WHERE GNO!=5 AND CID=C.CID) LEVELUP" + 
				"    FROM CUSTOMER C, GRADE G" + 
				"    WHERE C.GNO = G.GNO" + 
				"    ORDER BY CAMOUNT DESC";
		try {
			conn = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CustomerDto dto = new CustomerDto();
				dto.setCid(rs.getInt("cid"));
				dto.setCtel(rs.getString("ctel"));
				dto.setCname(rs.getString("cname"));
				dto.setCpoint(rs.getInt("cpoint"));
				dto.setCamount(rs.getInt("camount"));
				dto.setGrade(rs.getString("grade"));
				dto.setLevelup(rs.getInt("levelup"));
				dtos.add(dto);
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
	}
	//6.회원탈퇴  : public int deleteCustomer(String ctel)
	public int deleteCustomer(String ctel) {
		int result 				= FAIL;
		Connection 		   conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM CUSTOMER WHERE CTEL = ?";
		try {
			conn = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ctel);
			result = pstmt.executeUpdate();
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
	}
	
	
	
	
	
	
	
}
