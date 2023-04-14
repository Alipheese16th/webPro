package com.lec.ch19.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lec.ch19.service.BookService;

@Controller
@RequestMapping("main")
public class MainController {
	
	@Autowired
	private BookService bservice;
	
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("main",bservice.mainList());
		return "main/main";
	}
	
}
