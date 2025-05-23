package com.lec.emp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.lec.emp.model.Emp;
import com.lec.emp.service.EmpService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EmpController {
	
	private EmpService empService;
	
	@Autowired
	public EmpController(EmpService empService) {
		this.empService = empService;
	}
	// Autowired 는 그냥 변수위에 정의해도 되고 세터에 정의해도되고 생성자에 정의해도된다
//	@Autowired
//	public void setEmpService(EmpService empService) {
//		this.empService = empService;
//	}
	
	@GetMapping("/")
	public String index() {
		return "redirect:emp.do";
	}
	
	@GetMapping("emp.do")
	public String emp(@ModelAttribute("searchEmp") Emp searchEmp, Model model) {
		log.info("emp.do실행");
		model.addAttribute("empList", empService.empList(searchEmp));
		model.addAttribute("deptList", empService.deptList());
		return "emp";
	}
	
	
}
