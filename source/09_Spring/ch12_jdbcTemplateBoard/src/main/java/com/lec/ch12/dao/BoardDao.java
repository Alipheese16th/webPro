package com.lec.ch12.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.lec.ch12.dto.BoardDto;
import com.lec.ch12.util.Constant;

public class BoardDao {
	
	private JdbcTemplate template;
	
	public BoardDao() {
		template = Constant.template;
	}
	
	// 1. 글목록(startRow ~ endRow까지)
	public ArrayList<BoardDto> list(final int startRow, final int endRow){
		
		String sql = "SELECT * FROM " + 
				"  (SELECT ROWNUM RN, A.* FROM (SELECT * FROM MVC_BOARD ORDER BY BGROUP DESC, BSTEP) A) " + 
				"  WHERE RN BETWEEN ? AND ?";
		return (ArrayList<BoardDto>) template.query(sql, 
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement pstmt) throws SQLException {
						pstmt.setInt(1, startRow);
						pstmt.setInt(2, endRow);
					}
				},
				new BeanPropertyRowMapper<BoardDto>(BoardDto.class));
	}
	
	// 1. 글목록
	public ArrayList<BoardDto> list(){
		
		String sql = "SELECT * FROM " + 
				"  (SELECT ROWNUM RN, A.* FROM (SELECT * FROM MVC_BOARD ORDER BY BGROUP DESC, BSTEP) A)";
		return (ArrayList<BoardDto>) template.query(sql, new BeanPropertyRowMapper<BoardDto>(BoardDto.class));
		
	}
	
	// 2. 전체 글 갯수
	public int getBoardTotCnt() {
		
		String sql = "SELECT COUNT(*) FROM MVC_BOARD";
		return template.queryForObject(sql, Integer.class);
		
	}
	
	// 3. 원글 쓰기
	public int write(final String bname, final String btitle, final String bcontent, final String bip) {
		
//		String sql = "INSERT INTO MVC_BOARD(BID, BNAME, BTITLE, BCONTENT, BGROUP, BSTEP, BINDENT, BIP) " + 
//				"  VALUES(MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, MVC_BOARD_SEQ.CURRVAL, 0, 0, ?)";
//		return template.update(sql, new PreparedStatementSetter() {
//			@Override
//			public void setValues(PreparedStatement pstmt) throws SQLException {
//				pstmt.setString(1, bname);
//				pstmt.setString(1, btitle);
//				pstmt.setString(1, bcontent);
//				pstmt.setString(1, bip);
//			}
//		});
		
		return template.update(new PreparedStatementCreator() {
			String sql = "INSERT INTO MVC_BOARD(BID, BNAME, BTITLE, BCONTENT, BGROUP, BSTEP, BINDENT, BIP) " + 
					"  VALUES(MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, MVC_BOARD_SEQ.CURRVAL, 0, 0, ?)";
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, bname);
				pstmt.setString(2, btitle);
				pstmt.setString(3, bcontent);
				pstmt.setString(4, bip);
				return pstmt;
			}
		});
		
	}
	
	// 3. 원글 쓰기
	public int write(final BoardDto bDto) {
		
		String sql = "INSERT INTO MVC_BOARD(BID, BNAME, BTITLE, BCONTENT, BGROUP, BSTEP, BINDENT, BIP) " + 
				"  VALUES(MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, MVC_BOARD_SEQ.CURRVAL, 0, 0, ?)";
		return template.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bDto.getBname());
				pstmt.setString(2, bDto.getBtitle());
				pstmt.setString(3, bDto.getBcontent());
				pstmt.setString(4, bDto.getBip());
			}
		});
		
	}
	
	// 4. bid로 조회수 1 올리기
	private void hitUp(final int bid) {
		String sql = "UPDATE MVC_BOARD SET BHIT = BHIT + 1 WHERE BID = ?";
		template.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setInt(1, bid);
			}
		});
	}
	
	// 5. BID로 DTO 가져오기 (글상세보기) -- 조회수 올리고 dto 가져오기
	public BoardDto content(int bid) {
		hitUp(bid);
		String sql = "SELECT * FROM MVC_BOARD WHERE BID = " + bid;
		return template.queryForObject(sql, new BeanPropertyRowMapper<BoardDto>(BoardDto.class));
		
	}
	
	// 6. BID로 DTO 가져오기 (글수정VIEW, 답변글VIEW) -- dto 가져오기
	public BoardDto modifyView_replyView(int bid) {
		String sql = "SELECT * FROM MVC_BOARD WHERE BID = " + bid;
		return template.queryForObject(sql, new BeanPropertyRowMapper<BoardDto>(BoardDto.class));
		
	}
	
	// 7. 글 수정  (특정 bid의 작성자, 글제목, 글본문, bip만 수정)
	public int modify(final String bname, final String btitle, final String bcontent, final String bip, final int bid) {
		String sql = "UPDATE MVC_BOARD SET" + 
				"  BNAME = ?, " + 
				"  BTITLE = ?, " + 
				"  BCONTENT = ?, " + 
				"  BIP = ? " + 
				"  WHERE BID = ?";
		return template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bname);
				pstmt.setString(2, btitle);
				pstmt.setString(3, bcontent);
				pstmt.setString(4, bip);
				pstmt.setInt(5, bid);
			}
		});
	}
	
	// 7. 글 수정  (특정 bid의 작성자, 글제목, 글본문, bip만 수정)
	public int modify(final BoardDto bDto) {
		String sql = "UPDATE MVC_BOARD SET" + 
				"  BNAME = ?, " + 
				"  BTITLE = ?, " + 
				"  BCONTENT = ?, " + 
				"  BIP = ? " + 
				"  WHERE BID = ?";
		return template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bDto.getBname());
				pstmt.setString(2, bDto.getBtitle());
				pstmt.setString(3, bDto.getBcontent());
				pstmt.setString(4, bDto.getBip());
				pstmt.setInt(5, bDto.getBid());
				
			}
		});
	}
	
	// 8. 글 삭제
	public int delete(final int bid) {
		
		// 방법1
//		String sql = "DELETE FROM MVC_BOARD WHERE BID = " + bid;
//		return template.update(sql);
		
		// 방법2
//		String sql = "DELETE FROM MVC_BOARD WHERE BID = ?";
//		return template.update(sql, new PreparedStatementSetter() {
//			@Override
//			public void setValues(PreparedStatement pstmt) throws SQLException {
//				pstmt.setInt(1, bid);
//			}
//		});
		
		// 방법3
		return template.update(new PreparedStatementCreator() {
			String sql = "DELETE FROM MVC_BOARD WHERE BID = ?";
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, bid);
				return pstmt;
			}
		});
		
	}
	
	// 9. 답변글 저장전 작업
	private void preReplyStep(final int bgroup, final int bstep) {
		String sql = "UPDATE MVC_BOARD SET BSTEP = BSTEP + 1 " + 
				"  WHERE BGROUP = ? AND BSTEP > ?";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setInt(1, bgroup);
				pstmt.setInt(2, bstep);
			}
		});
	}
	
	// 10. 답변글 쓰기
	// db에서 자동생성 : bid, bdate, bhit
	// 답변글쓴이 : bname, btitle, bcontent
	// 시스템적으로 : bip
	// 원글의 내용 : bgroup, bstep, bindent
	public int reply(final String bname, final String btitle, final String bcontent, 
			final int bgroup, final int bstep, final int bindent, final String bip) {
		preReplyStep(bgroup, bstep);
		String sql = "INSERT INTO MVC_BOARD (BID, BNAME, BTITLE, BCONTENT, BGROUP, BSTEP, BINDENT, BIP) " + 
				"  VALUES (MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
		return template.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bname);
				pstmt.setString(2, btitle);
				pstmt.setString(3, bcontent);
				pstmt.setInt(4, bgroup);
				pstmt.setInt(5, bstep+1);
				pstmt.setInt(6, bindent+1);
				pstmt.setString(7, bip);
			}
		});
	}
	
	public int reply(final BoardDto bDto) {
		preReplyStep(bDto.getBgroup(), bDto.getBstep());
		String sql = "INSERT INTO MVC_BOARD (BID, BNAME, BTITLE, BCONTENT, BGROUP, BSTEP, BINDENT, BIP) " + 
				"  VALUES (MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
		return template.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bDto.getBname());
				pstmt.setString(2, bDto.getBtitle());
				pstmt.setString(3, bDto.getBcontent());
				pstmt.setInt(4, bDto.getBgroup());
				pstmt.setInt(5, bDto.getBstep()+1);
				pstmt.setInt(6, bDto.getBindent()+1);
				pstmt.setString(7, bDto.getBip());
			}
		});
	}
	
}
