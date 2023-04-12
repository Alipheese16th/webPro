package com.lec.ch17.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lec.ch17.dao.BoardDao;
import com.lec.ch17.model.Board;
import com.lec.ch17.util.Paging;

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
	public int boardWrite(Board board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hitUp(int bid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Board boardDetail(int bid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int boardModify(Board board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int boardDelete(int bid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int boardReplyPreStep(Board board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int boardReply(Board board) {
		// TODO Auto-generated method stub
		return 0;
	}

}
