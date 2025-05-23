package com.lec.ch15.controller;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lec.ch15.dao.DeptDao;
import com.lec.ch15.dao.EmpDao;
import com.lec.ch15.service.EmpService;
import com.lec.ch15.util.Paging;

@Controller
public class EmpController {
	@Autowired
	private EmpService empService;
	@RequestMapping(value="empList", method=RequestMethod.GET)
	public String empList(String pageNum, Model model) {
		// empList,do 또는 empList.do?pageNum=2
		model.addAttribute("empList",empService.empList(pageNum));
		model.addAttribute("paging",new Paging(empService.totCnt(),pageNum,10,5));
		return "empList";
	}
	@RequestMapping(value="dummyInsert", method=RequestMethod.GET)
	public String dummyInsert() {
		empService.dummyDataInsert50();
		return "redirect:empList.do";
	}
	@RequestMapping(value="empDeptList", method=RequestMethod.GET)
	public String empDeptList(String pageNum, Model model) {
		model.addAttribute("empDeptList",empService.empDeptList(pageNum));
		model.addAttribute("paging",new Paging(empService.totCnt(),pageNum));
		return "empDeptList";
	}
//	@RequestMapping(value="", method=RequestMethod.)
//	public String () {
//		return "";
//	}
//	@RequestMapping(value="", method=RequestMethod.)
//	public String () {
//		return "";
//	}
//	@RequestMapping(value="", method=RequestMethod.)
//	public String () {
//		return "";
//	}
//	@RequestMapping(value="", method=RequestMethod.)
//	public String () {
//		return "";
//	}
//	@RequestMapping(value="", method=RequestMethod.)
//	public String () {
//		return "";
//	}
//	@RequestMapping(value="", method=RequestMethod.)
//	public String () {
//		return "";
//	}
}
