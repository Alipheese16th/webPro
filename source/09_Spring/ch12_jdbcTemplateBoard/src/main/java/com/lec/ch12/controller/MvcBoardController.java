package com.lec.ch12.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lec.ch12.dto.BoardDto;
import com.lec.ch12.service.BContentService;
import com.lec.ch12.service.BDeleteService;
import com.lec.ch12.service.BListService;
import com.lec.ch12.service.BModifyService;
import com.lec.ch12.service.BReplyService;
import com.lec.ch12.service.BWriteService;
import com.lec.ch12.service.BmodifyReplyViewService;
import com.lec.ch12.service.Service;
import com.lec.ch12.util.Constant;

@Controller
@RequestMapping("mvcBoard") // 공통 요청 경로
public class MvcBoardController {
	
	
	public JdbcTemplate template;
	
	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		Constant.template = template;
	}
	
	
	private Service bservice;
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String list(String pageNum, Model model) {
		// mvcBoard/list.do
		// mvcBoard/list.do?pageNum=10
		model.addAttribute("pageNum",pageNum);
		bservice = new BListService();
		bservice.execute(model);
		return "mvcBoard/list";
	}
	@RequestMapping(value="list", method=RequestMethod.POST)
	public String list_post(String pageNum, Model model) {
		// mvcBoard/list.do
		// mvcBoard/list.do?pageNum=10
		model.addAttribute("pageNum",pageNum);
		bservice = new BListService();
		bservice.execute(model);
		return "mvcBoard/list";
	}
	
	@RequestMapping(value="write", method=RequestMethod.GET)
	public String write() {
		return "mvcBoard/write";
	}
	@RequestMapping(value="write", method=RequestMethod.POST)
	public String write(@ModelAttribute("bDto") BoardDto boardDto, 
						HttpServletRequest request, Model model) {
		// request : service클래스에서 ip추출 용도
		model.addAttribute("request",request);
		bservice = new BWriteService();
		bservice.execute(model);
		return "forward:list.do";
	}
	
	@RequestMapping(value="content", method=RequestMethod.GET)
	public String content(int bid, Model model) {
		model.addAttribute("bid",bid);
		bservice = new BContentService();
		bservice.execute(model);
		return "mvcBoard/content";
	}
	@RequestMapping(value="content", method=RequestMethod.POST)
	public String content_post(int bid, Model model) {
		model.addAttribute("bid",bid);
		bservice = new BContentService();
		bservice.execute(model);
		return "mvcBoard/content";
	}
	
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public String modify(int bid, Model model) {
		model.addAttribute("bid",bid);
		bservice = new BmodifyReplyViewService();
		bservice.execute(model);
		return "mvcBoard/modify";
	}
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(BoardDto boardDto,
						HttpServletRequest request,
						Model model) {
		model.addAttribute("request",request);
		bservice = new BModifyService();
		bservice.execute(model);
		return "forward:content.do";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete(int bid, Model model) {
		model.addAttribute("bid", bid);
		bservice = new BDeleteService();
		bservice.execute(model);
		return "forward:list.do";
	}
	
	@RequestMapping(value="reply", method=RequestMethod.GET)
	public String reply(int bid, Model model) {
		model.addAttribute("bid",bid);
		bservice = new BmodifyReplyViewService();
		bservice.execute(model);
		return "mvcBoard/reply";
	}
	@RequestMapping(value="reply", method=RequestMethod.POST)
	public String reply(BoardDto boardDto, HttpServletRequest request, Model model) {
		model.addAttribute("request",request);
		bservice = new BReplyService();
		bservice.execute(model);
		return "forward:list.do";
	}
	
}
