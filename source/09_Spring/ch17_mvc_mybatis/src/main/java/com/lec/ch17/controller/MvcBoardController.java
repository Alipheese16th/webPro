package com.lec.ch17.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lec.ch17.model.Board;
import com.lec.ch17.service.BoardService;
import com.lec.ch17.util.Paging;

@Controller
@RequestMapping("mvcBoard") // 공통 요청 경로
public class MvcBoardController {
	
	@Autowired
	private BoardService service;
	
	@RequestMapping(value="list", method= {RequestMethod.GET,RequestMethod.POST})
	public String list(String pageNum, Model model) {
		model.addAttribute("boardList",service.boardList(pageNum));
		model.addAttribute("paging", new Paging(service.totCnt(), pageNum, 10, 10));
		return "mvcBoard/list";
	}
	@RequestMapping(value="write", method=RequestMethod.GET)
	public String write() {
		return "mvcBoard/write";
	}
	@RequestMapping(value="write", method=RequestMethod.POST)
	public String write(Board board, HttpServletRequest request, Model model) {
		// request : service클래스에서 ip추출 용도
		model.addAttribute("writeResult",service.boardWrite(board,request));
		return "forward:list.do";
	}
	@RequestMapping(value="content", method= {RequestMethod.GET,RequestMethod.POST})
	public String content(int bid, Model model) {
		model.addAttribute("contentBoard",service.boardDetail(bid));
		service.hitUp(bid);
		return "mvcBoard/content";
	}
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public String modify(int bid, Model model) {
		model.addAttribute("board",service.boardDetail(bid));
		return "mvcBoard/modify";
	}
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(Board board, HttpServletRequest request, Model model) {
		model.addAttribute("modifyResult",service.boardModify(board, request));
		return "forward:content.do";
	}
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete(int bid, Model model) {
		int result = service.boardDelete(bid);
		String deleteResult = result==1 ? bid+"번글 삭제 성공" : bid+"번글 삭제 실패";
		model.addAttribute("deleteResult",deleteResult);
		return "forward:list.do";
	}
	@RequestMapping(value="reply", method=RequestMethod.GET)
	public String reply(int bid, Model model) {
		model.addAttribute("board",service.boardDetail(bid));
		return "mvcBoard/reply";
	}
	@RequestMapping(value="reply", method=RequestMethod.POST)
	public String reply(Board board, HttpServletRequest request, Model model) {
		service.boardReplyPreStep(board);
		model.addAttribute("replyResult",service.boardReply(board, request));
		return "forward:list.do";
	}
	
}
