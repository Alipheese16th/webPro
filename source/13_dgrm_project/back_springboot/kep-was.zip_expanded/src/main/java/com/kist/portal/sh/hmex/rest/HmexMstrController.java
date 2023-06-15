package com.kist.portal.sh.hmex.rest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sh.hmex.service.HmexMstrService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/hlth")
public class HmexMstrController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private HmexMstrService service;
	
	@GetMapping("/hmexmstr")
	public ResponseEntity<?> selectHmexMstrList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_DEPT", userInfo.getDeptCd());
		param.put("SESS_CMPN", userInfo.getCmpyCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> list = null;
		if("dept".equals(param.get("type"))) {
			list = service.selectDeptList(param);
		} else if("user".equals(param.get("type"))) {
			list = service.selectUserList(param);
		} else if("mtra".equals(param.get("type"))) {
			list = service.selectMtraList(param);
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/hmexmstr")
	@Transactional
	public ResponseEntity<?> insertHmexMstr(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		int n = service.insertHmexMstr(param);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
