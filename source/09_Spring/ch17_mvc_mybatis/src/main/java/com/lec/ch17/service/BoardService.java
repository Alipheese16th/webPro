package com.lec.ch17.service;

import java.util.List;

import com.lec.ch17.model.Board;

public interface BoardService {
	public List<Board> boardList(String pageNum);
	public int totCnt();
	public int boardWrite(Board board);
	public int hitUp(int bid);
	public Board boardDetail(int bid);
	public int boardModify(Board board);
	public int boardDelete(int bid);
	public int boardReplyPreStep(Board board);
	public int boardReply(Board board);
}
