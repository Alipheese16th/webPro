package com.kist.portal.common.bbs.rest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.kist.portal.common.bbs.service.BbsService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common/bbs")
public class BbsController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private BbsService service;
	
	@GetMapping("/noti")
	public ResponseEntity<?> selectNotiList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectNotiList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/noti")
	@Transactional
	public ResponseEntity<?> saveNoti(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		int n = 0;
//		System.out.println("usrinfo>>>>>>>>>>>>>>>>>>>>>");
//		System.out.println(userInfo.getUsrId());
//		System.out.println(userInfo.getUsrNm());
//		System.out.println(userInfo.getDeptCd());
//		System.out.println(userInfo.getDeptNm());
//		System.out.println(userInfo.getWkplId());
//		System.out.println(userInfo.getLangCd());
//		System.out.println(userInfo.getTimeZone());
//		System.out.println(userInfo.getRoles());
//		System.out.println(userInfo.getRoleList());
//		System.out.println("usrinfo>>>>>>>>>>>>>>>>>>>>>");

		param.put("SESS_USR_ID", userInfo.getUsrId());
		if("".equals(param.get("bbs_doc_no")) || null==param.get("bbs_doc_no")) {
			String key = service.selectBbskey(param);
			param.put("bbs_doc_no", key);
			n = service.saveNoti(param);
		}else {
			n = service.updateNoti(param);
		}
		
		String result = n > -1? param.get("bbs_doc_no") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/noti/{id}")
	public ResponseEntity<?> selectNotiDtl(@PathVariable String id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> param = new HashMap<>();
		param.put("bbs_doc_no", id);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		Cookie[] cookies = request.getCookies();
		boolean hasCookie = false;
		for(Cookie cookie : cookies) {
//			logger.debug("Cookie Name : " + cookie.getName());
//			logger.debug("Cookie Value : " + cookie.getValue());
			if(userInfo.getUsrId().equals(cookie.getName())) {
				hasCookie = true;
				if(!cookie.getValue().contains(id)) {
					// update cnt
					service.updateNotiCnt(param);
					cookie.setValue(cookie.getValue() + "-" + id);
					setCookie(cookie, response);
				}
			}
		}
		
		if(!hasCookie) {
			// update cnt
			service.updateNotiCnt(param);
			Cookie cookie = new Cookie(userInfo.getUsrId(), id);
			setCookie(cookie, response);
		}

		Map<String, ?> map = service.selectNotiDtl(param);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	private void setCookie(Cookie cookie, HttpServletResponse response) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DATE) + 1, 0, 0, 0);
		int sec = (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / 1000);
		cookie.setPath("/");
		cookie.setMaxAge(sec);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}
	
	@DeleteMapping("/noti/{id}")
	public ResponseEntity<?> deleteNoti(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("bbs_doc_no", id);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = 0;
		// mro 기본 데이터 데이터 삭제
		n = service.deleteNoti(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);

		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/faqs")
	public ResponseEntity<?> selectFaqsList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectFaqsList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/faqs")
	@Transactional
	public ResponseEntity<?> saveFaqs(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		int n = 0;
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		if("".equals(param.get("bbs_doc_no")) || null==param.get("bbs_doc_no")) {
			String key = service.selectBbskey(param);
			param.put("bbs_doc_no", key);
			n = service.saveFaqs(param);
		}else {
			n = service.updateFaqs(param);
		}
		
		String result = n > -1? param.get("bbs_doc_no") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/faqs/{id}")
	public ResponseEntity<?> selectFaqsDtl(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> param = new HashMap<>();
		param.put("bbs_doc_no", id);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		
		Map<String, ?> map = service.selectFaqsDtl(param);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/faqs/{id}")
	public ResponseEntity<?> deleteFaqs(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("bbs_doc_no", id);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = 0;
		// mro 기본 데이터 데이터 삭제
		n = service.deleteNoti(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);

		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/arch")
	public ResponseEntity<?> selectArchList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectArchList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/arch")
	@Transactional
	public ResponseEntity<?> saveArch(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		int n = 0;
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		if("".equals(param.get("bbs_doc_no")) || null==param.get("bbs_doc_no")) {
			String key = service.selectBbskey(param);
			param.put("bbs_doc_no", key);
			n = service.saveArch(param);
		}else {
			n = service.updateArch(param);
		}
		
		String result = n > -1? param.get("bbs_doc_no") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/arch/{id}")
	public ResponseEntity<?> selectArchDtl(@PathVariable String id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("bbs_doc_no", id);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		Cookie[] cookies = request.getCookies();
		boolean hasCookie = false;
		for(Cookie cookie : cookies) {
//			logger.debug("Cookie Name : " + cookie.getName());
//			logger.debug("Cookie Value : " + cookie.getValue());
			if(userInfo.getUsrId().equals(cookie.getName())) {
				hasCookie = true;
				if(!cookie.getValue().contains(id)) {
					// update cnt
					service.updateNotiCnt(param);
					cookie.setValue(cookie.getValue() + "-" + id);
					setCookie(cookie, response);
				}
			}
		}
		
		if(!hasCookie) {
			// update cnt
			service.updateNotiCnt(param);
			Cookie cookie = new Cookie(userInfo.getUsrId(), id);
			setCookie(cookie, response);
		}
		
		Map<String, ?> map = service.selectArchDtl(param);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/arch/{id}")
	public ResponseEntity<?> deleteArch(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("bbs_doc_no", id);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = 0;
		// mro 기본 데이터 데이터 삭제
		n = service.deleteNoti(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);

		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/noti-pop")
	public ResponseEntity<?> selectNotiYList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		List<Map<String, ?>> list = service.selectNotiYList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/manu")
	public ResponseEntity<?> selectManuList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectManuList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/manu")
	@Transactional
	public ResponseEntity<?> saveManu(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		int n = 0;
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		if("".equals(param.get("bbs_doc_no")) || null==param.get("bbs_doc_no")) {
			String key = service.selectBbskey(param);
			param.put("bbs_doc_no", key);
			n = service.saveManu(param);
		}else {
			n = service.updateManu(param);
		}
		
		String result = n > -1? param.get("bbs_doc_no") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/manu/{id}")
	public ResponseEntity<?> selectManuDtl(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("bbs_doc_no", id);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		
		Map<String, ?> map = service.selectManuDtl(param);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/manu/{id}")
	public ResponseEntity<?> deleteManu(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("bbs_doc_no", id);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = 0;
		// mro 기본 데이터 데이터 삭제
		n = service.deleteNoti(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);

		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
