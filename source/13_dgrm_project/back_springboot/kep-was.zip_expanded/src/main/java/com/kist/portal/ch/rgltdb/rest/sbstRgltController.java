package com.kist.portal.ch.rgltdb.rest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.ch.rgltdb.service.sbstRgltService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/ch/rgltdb")
public class sbstRgltController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private sbstRgltService service;
	
	@GetMapping("/sbst-rglt")
	public ResponseEntity<?> selectSbstRgltList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		List<Map<String, ?>> list = service.selectsbstRgltList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
}
