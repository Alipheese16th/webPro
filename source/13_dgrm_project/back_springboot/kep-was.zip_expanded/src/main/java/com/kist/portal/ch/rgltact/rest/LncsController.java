package com.kist.portal.ch.rgltact.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.ch.rgltact.service.LncsService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/ch/rgltact")
public class LncsController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private LncsService service;
	
	@GetMapping("/lncs")
	public ResponseEntity<?> selectLncsList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectLncsList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/lncs")
	@Transactional
	public ResponseEntity<?> saveLncs(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		int n = 0;
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		if("".equals(param.get("bbs_doc_no")) || null==param.get("bbs_doc_no")) {
			String key = service.selectBbskey(param);
			param.put("bbs_doc_no", key);
			n = service.saveLncs(param);
		}else {
			n = service.updateLncs(param);
		}
		
		String result = n > -1? param.get("bbs_doc_no") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/lncs/{id}")
	public ResponseEntity<?> selectLncsDtl(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("bbs_doc_no", id);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		
		Map<String, ?> map = service.selectLncsDtl(param);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/lncs/{id}")
	public ResponseEntity<?> deleteLncs(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("bbs_doc_no", id);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = 0;
		// mro 기본 데이터 데이터 삭제
		n = service.deleteLncs(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);

		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/alrm")
	public ResponseEntity<?> selectRgltActList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> list = service.selectRgltActList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/alrm/{id}")
	public ResponseEntity<?> selectRgltActList(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> param = new HashMap<>();
		param.put("rglt_act_alrm_no", id);

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> list = service.selectRgltActDetail(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
