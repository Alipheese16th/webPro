package com.lec.uitest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lec.uitest.vo.student;

@Controller
public class StudentController {
	
	@ModelAttribute("cnt")
	public int cnt() {
		return 4;
	}
	
	@RequestMapping(value = "input.do", method = RequestMethod.GET)
	public String input() {
		return "student/input";
	}
	
	@RequestMapping(value = "input.do", method = RequestMethod.POST)
	public String result(student student) {
		return "student/result";
	}
}
