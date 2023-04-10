package com.lec.ch13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lec.ch13.service.FileUpService;

@Controller
public class FileController {
	@Autowired
	private FileUpService fService;
	
	@RequestMapping(value="fileUp", method=RequestMethod.GET)
	public String fileUp() {
		return "fileUp";
	}
	
	@RequestMapping(value="fileUp", method=RequestMethod.POST)
	public ModelAndView fileUp(MultipartHttpServletRequest mRequest, ModelAndView mav) {
		
		if(fService.fileUp(mRequest, mav)) {
			mav.addObject("fileUpResult", "파일 업로드 성공");
		}else {
			mav.addObject("fileUpResult", "파일 업로드 안 됨");
		}
		
		mav.setViewName("fileResult");
		
		return mav;
	}
	
	
	
}
