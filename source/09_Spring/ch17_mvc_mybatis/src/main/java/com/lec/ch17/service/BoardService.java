package com.lec.ch17.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lec.ch17.model.Board;

public interface BoardService {
	public List<Board> boardList(String pageNum);
	public int totCnt();
	public int boardWrite(Board board, HttpServletRequest request);
	public void hitUp(int bid);
	public Board boardDetail(int bid);
	int boardModify(Board board, HttpServletRequest request);
	public int boardDelete(int bid);
	public void boardReplyPreStep(Board board);
	int boardReply(Board board, HttpServletRequest request);
}
