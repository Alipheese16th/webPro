package com.kist.portal.common.comm.rest;

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

import com.kist.portal.common.comm.service.CommPopService;
import com.kist.portal.common.email.rest.SendEmailUtil;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/comm/pop")
public class CommPopController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private CommPopService service;
	
	@Autowired
	private SendEmailUtil util;
	
	@GetMapping("/commpop-sbst-list")
	public ResponseEntity<?> selectSbstCommPopList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectSbstCommPopList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/commpop-mtrl-list")
	public ResponseEntity<?> selectMtrlCommPopList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectMtrlCommPopList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/commpop-vndr-list")
	public ResponseEntity<?> selectVndrCommPopList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectVndrCommPopList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/commpop-mtrl-multi-list")
	public ResponseEntity<?> selectMtrlMultiCommPopList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectMtrlMultiCommPopList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/commpop-bsend-list")
	public ResponseEntity<?> selectBsendCommPopList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectBsendCommPopList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/commpop-bsend-user-list")
	public ResponseEntity<?> selectBsendUserCommPopList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectBsendUserCommPopList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("/send-mail")
	public ResponseEntity<?> insertMailSend(@RequestBody Map<String, Object> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		// 데이터 초기화
		List<Map<String, String>> mail = (List<Map<String, String>>)param.get("mail");
		List<Map<String, String>> rcvUser = (List<Map<String, String>>)param.get("rcv");
		String bizCd = mail.get(0).get("biz_cd");
		String sendUser = userInfo.getUsrId();
		String subject = mail.get(0).get("email_ttl");
		String content = mail.get(0).get("email_cntn");
		String[] to = null;
		if (rcvUser != null) {
			to = new String[rcvUser.size()];
			for (int i=0; i<rcvUser.size(); i++) {
				to[i] = rcvUser.get(i).get("recv_usr_id");
			}
		}
		// 메일 송신
		Boolean mailResult = util.sendMail(bizCd, sendUser, subject, content, to);
		
		String result = "fail";
		if (mailResult) {
			result = "success";
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
