package com.lec.ch17.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("mvcBoard") // 공통 요청 경로
public class MvcBoardController {
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String list(Model model) {
		
		return "mvcBoard/list";
	}
	
}
