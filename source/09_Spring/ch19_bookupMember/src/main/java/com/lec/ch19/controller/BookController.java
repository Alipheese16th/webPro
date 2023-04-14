package com.lec.ch19.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lec.ch19.model.Book;
import com.lec.ch19.service.BookService;
import com.lec.ch19.util.Paging;

@Controller
@RequestMapping("book")
public class BookController {
	
	@Autowired
	private BookService bservice;
	
	@RequestMapping(params="method=detail", method=RequestMethod.GET)
	public String detail(int bnum, Model model) {
		model.addAttribute("book",bservice.getDetailBook(bnum));
		return "book/detail";
	}
	
	@RequestMapping(params="method=list", method=RequestMethod.GET)
	public String list(String pageNum, Model model) {
		model.addAttribute("bookList",bservice.bookList(pageNum));
		model.addAttribute("paging",new Paging(bservice.totCntBook(), pageNum, 3, 3));
		return "book/list";
	}
	
	@RequestMapping(params="method=register", method=RequestMethod.GET)
	public String register(Model model) {
		return "book/register";
	}
	
	@RequestMapping(params="method=register", method=RequestMethod.POST)
	public String register(Book book, Model model, MultipartHttpServletRequest mRequest) {
		model.addAttribute("registerResult", bservice.registerBook(mRequest, book));
		return "book/register";
	}
	

	
	
	
	
	
	
}
