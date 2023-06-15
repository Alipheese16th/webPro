package com.kist.portal.common.chems.rest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.chems.dto.LoginLogDto;
import com.kist.portal.common.chems.service.HisService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common/chems")
public class HisController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HisService service;
	
	@Autowired
	private SessionManager sessionManager;
	
	@GetMapping("/login-his")
	public ResponseEntity<?> selectLoginHisList(LoginLogDto param) {
		UserInfo userInfo = sessionManager.get();
		param.setSESS_LANG(userInfo.getLangCd());
		
		List<LoginLogDto> list = service.selectLoginHisList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/api-his")
	public ResponseEntity<?> selectApiHisList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIMEZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectApiHisList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/bat-his")
	public ResponseEntity<?> selectBatHisList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIMEZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectBatHisList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/bat-list")
	public ResponseEntity<?> selectBatchList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		List<Map<String, ?>> list = service.selectBatchList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/bat-his/{id}")
	public ResponseEntity<?> selectDetailBatHis(@PathVariable String id) {
		
		String result = service.selectDetailBatHis(id);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
