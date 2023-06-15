package com.kist.portal.common.test.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.test.service.TestService;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common/test")
public class TestController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private TestService service;
	
	@GetMapping("/bbs")
	public ResponseEntity<?> selectBbsList(@RequestParam Map<String, String> param) {
		List<Map<String, ?>> list = service.selectBbsList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/bbs/{id}")
	public ResponseEntity<?> selectBbs(@PathVariable String id) {
		Map<String, ?> map = service.selectBbs(id);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("/bbs")
	public ResponseEntity<?> insertBbs(@RequestBody Map<String, String> param) {
		int n = service.insertBbs(param);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/bbs")
	public ResponseEntity<?> updateBbs(@RequestBody Map<String, String> param) {
		int n = service.updateBbs(param);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/bbs/{id}")
	public ResponseEntity<?> deleteBbs(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("id", id);
		param.put("username", userInfo.getUsrId());
		int n = service.deleteBbs(param);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
