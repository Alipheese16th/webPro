package com.kist.portal.sh.sopmgnt.rest;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sh.sopmgnt.service.SopMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/sfty")
public class SopMgntController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private SopMgntService service;
	
	@GetMapping("/sopmgnt")
	public ResponseEntity<?> selectSopMgntList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_DEPT", userInfo.getDeptCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> list = null;
		if("base_wkpl".equals(param.get("type"))) {
			param.put("cmpy_cd", userInfo.getCmpyCd());
			
			list = service.selectSopMgntBaseWkpl(param);
		} else {
			list = service.selectSopMgntList(param);
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/sopmgnt/{id}")
	public ResponseEntity<?> selectSopMgnt(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("sop_id", id);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		Map<String, ?> map = service.selectSopMgnt(param);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("/sopmgnt")
	@Transactional
	public ResponseEntity<?> insertSopMgnt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		int n = service.insertSopMgnt(param);
		
		String result = n > -1? (String) param.get("sop_id") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/sopmgnt")
	@Transactional
	public ResponseEntity<?> updateSopMgnt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		int n = service.updateSopMgnt(param);
		
		String result = n > -1? (String) param.get("sop_id") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/sopmgnt/{id}")
	public ResponseEntity<?> deleteSopMgnt(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("sop_id", id);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = service.deleteSopMgnt(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/sopmgnt/{id}")
	public ResponseEntity<?> delSop(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("sop_id", id);
		param.put("username", userInfo.getUsrId());
		
		int n = 0;
		n = service.delSop(param);
		
		String result;
		result = (n > -1? Constant.SUCCESS : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
