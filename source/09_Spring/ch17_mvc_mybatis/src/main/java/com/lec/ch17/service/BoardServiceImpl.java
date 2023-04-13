package com.lec.ch17.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lec.ch17.dao.BoardDao;
import com.lec.ch17.model.Board;
import com.lec.ch17.util.Paging;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDao boardDao;

	@Override
	public List<Board> boardList(String pageNum) {
		Paging paging = new Paging(boardDao.totCnt(), pageNum, 10, 10);
		Board board = new Board();
		board.setStartRow(paging.getStartRow());
		board.setEndRow(paging.getEndRow());
		return boardDao.boardList(board);
	}
	@Override
	public int totCnt() {
		return boardDao.totCnt();
	}
	@Override
	public int boardWrite(Board board, HttpServletRequest request) {
		board.setBip(request.getRemoteAddr());
		return boardDao.boardWrite(board);
	}
	@Override
	public void hitUp(int bid) {
		boardDao.hitUp(bid);
	}
	@Override
	public Board boardDetail(int bid) {
		return boardDao.boardDetail(bid);
	}
	@Override
	public int boardModify(Board board, HttpServletRequest request) {
		board.setBip(request.getRemoteAddr());
		return boardDao.boardModify(board);
	}
	@Override
	public int boardDelete(int bid) {
		return boardDao.boardDelete(bid);
	}
	@Override
	public void boardReplyPreStep(Board board) {
		boardDao.boardReplyPreStep(board);
	}
	@Override
	public int boardReply(Board board, HttpServletRequest request) {
		board.setBip(request.getRemoteAddr());
		return boardDao.boardReply(board);
	}

}
