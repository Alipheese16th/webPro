package com.lec.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.lec.dto.FileboardDto;

public class FileboardDao {
	public static final int SUCCESS = 1;
	public static final int FAIL = 0;
	//싱글톤
	private static FileboardDao instance = new FileboardDao();
	public static FileboardDao getInstance() {
		return instance;
	}
	private FileboardDao() {}
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle11g");
			conn = ds.getConnection();
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	
	
	// 1. 글목록 
	public ArrayList<FileboardDto> listBoard(int startRow,int endRow){
		ArrayList<FileboardDto> dtos = new ArrayList<FileboardDto>();
		Connection 		   conn = null;
		PreparedStatement pstmt = null;
		ResultSet 			 rs = null;
		String sql = "SELECT * " + 
				"  FROM(SELECT ROWNUM RN, A.* " + 
				"    FROM(SELECT F.*, CNAME, CEMAIL " + 
				"            FROM FILEBOARD F, CUSTOMER C " + 
				"            WHERE F.CID = C.CID " + 
				"            ORDER BY FREF DESC, FRE_STEP) A ) " + 
				"  WHERE RN BETWEEN ? AND ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int fnum = rs.getInt("fnum");
				String cid = rs.getString("cid");
				String fsubject = rs.getString("fsubject");
				String fcontent = rs.getString("fcontent");
				String ffilename = rs.getString("ffilename");
				String fpw = rs.getString("fpw");
				int fhit = rs.getInt("fhit");
				int fref = rs.getInt("fref");
				int fre_step = rs.getInt("fre_step");
				int fre_level = rs.getInt("fre_level");
				String fip = rs.getString("fip");
				Date frdate = rs.getDate("frdate");
				String cname = rs.getString("cname");
				String cemail = rs.getString("cemail");
				dtos.add(new FileboardDto(fnum, cid, fsubject,fcontent, ffilename, fpw, fhit,
						fref, fre_step, fre_level, fip, frdate, cname, cemail));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return dtos;
	}
	
	// 2. 등록된 글 수
	public int getFileboardCnt() {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM FILEBOARD";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	
	// 3. 글쓰기(원글)
	public int insertBoard(FileboardDto dto) {
		int result = FAIL;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO FILEBOARD(fNUM, cID, fSUBJECT, fCONTENT, fFILENAME, fPW, fREF, fRE_STEP, fRE_LEVEL, fIP) " + 
				" VALUES(FILEBOARD_SEQ.NEXTVAL, ?, ?, ?, ?, ?, FILEBOARD_SEQ.CURRVAL, 0, 0, ?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,dto.getCid());
			pstmt.setString(2,dto.getFsubject());
			pstmt.setString(3,dto.getFcontent());
			pstmt.setString(4,dto.getFfilename());
			pstmt.setString(5,dto.getFpw());
			pstmt.setString(6,dto.getFip());
			result = pstmt.executeUpdate();
			System.out.println(result==SUCCESS?"원글 쓰기 성공":"원글 쓰기 실패");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("원글쓰다 예외 발생 : "+dto);
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	
	// 4. 답글쓰기전 전처리
	private void preReplyStep(int fref, int fre_step) {
		Connection 		   conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE FILEBOARD SET FRE_STEP = FRE_STEP+1 " + 
							" WHERE FREF = ? AND FRE_STEP > ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fref);
			pstmt.setInt(2, fre_step);
			int result = pstmt.executeUpdate();
			System.out.println("답변글 "+result+"개 조정됨");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	// 5. 답글쓰기
	public int reply(FileboardDto dto) { // dto안의 내용(원글의 fref,fre_step,fre_level / 새글의 정보들)
		preReplyStep(dto.getFref(),dto.getFre_step());
		int result = FAIL;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO FILEBOARD(fNUM, cID, fSUBJECT, fCONTENT, fFILENAME, fPW, fREF, fRE_STEP, fRE_LEVEL, fIP) " + 
					" VALUES(FILEBOARD_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,dto.getCid());
			pstmt.setString(2,dto.getFsubject());
			pstmt.setString(3,dto.getFcontent());
			pstmt.setString(4,dto.getFfilename());
			pstmt.setString(5,dto.getFpw());
			pstmt.setInt(6, dto.getFref());
			pstmt.setInt(7, dto.getFre_step()+1);
			pstmt.setInt(8, dto.getFre_level()+1);
			pstmt.setString(9, dto.getFip());
			result = pstmt.executeUpdate();
			System.out.println(result==SUCCESS?"답변글쓰기 성공":"답변글쓰기 실패");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("답변글 쓰다 예외 발생 : "+dto);
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	
	
	// 6. 조회수올리기
	public void hitUp(int fnum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE FILEBOARD SET FHIT = FHIT + 1 WHERE FNUM = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,fnum);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	// 7. 상세보기
	public FileboardDto getBoard(int fnum) {
		FileboardDto dto = null;
		Connection 		   conn = null;
		PreparedStatement pstmt = null;
		ResultSet 			 rs = null;
		String sql = "SELECT F.*, CNAME, CEMAIL FROM FILEBOARD F, CUSTOMER C WHERE F.CID = C.CID AND FNUM = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fnum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String cid = rs.getString("cid");
				String fsubject = rs.getString("fsubject");
				String fcontent = rs.getString("fcontent");
				String ffilename = rs.getString("ffilename");
				String fpw = rs.getString("fpw");
				int fhit = rs.getInt("fhit");
				int fref = rs.getInt("fref");
				int fre_step = rs.getInt("fre_step");
				int fre_level = rs.getInt("fre_level");
				String fip = rs.getString("fip");
				Date frdate = rs.getDate("frdate");
				String cname = rs.getString("cname");
				String cemail = rs.getString("cemail");
				dto = new FileboardDto(fnum, cid, fsubject,fcontent, ffilename, fpw, fhit,
						fref, fre_step, fre_level, fip, frdate, cname, cemail);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return dto;
	}
	
	// 8. 글 수정하기
	public int updateBoard(FileboardDto dto) {
		int result = FAIL;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE FILEBOARD SET fSUBJECT = ?, " + 
				"                    fCONTENT = ?, " + 
				"                    fFILENAME = ?, " + 
				"                    fPW = ?, " + 
				"                    fIP = ? " + 
				"                WHERE fNUM = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,dto.getFsubject());
			pstmt.setString(2,dto.getFcontent());
			pstmt.setString(3,dto.getFfilename());
			pstmt.setString(4,dto.getFpw());
			pstmt.setString(5,dto.getFip());
			pstmt.setInt(6,dto.getFnum());
			result = pstmt.executeUpdate();
			System.out.println(result==SUCCESS?"글수정 성공":"글수정 실패");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("글수정 예외 발생 : "+dto);
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	
	// 9. 글 삭제하기
	public int deleteBoard(int fnum, String fpw) {
		int result = FAIL;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM FILEBOARD WHERE fNUM = ? AND fPW = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,fnum);
			pstmt.setString(2,fpw);
			result = pstmt.executeUpdate();
			System.out.println(result==SUCCESS?"글삭제 완료":"글삭제 실패(비번확인)");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	
}
